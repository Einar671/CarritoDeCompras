package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.RespuestaSeguridad;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsuarioController {
    private final UsuarioDAO usuarioDAO;
    private final MensajeInternacionalizacionHandler mensajes;

    private final UsuarioCrearView usuarioCrearView;
    private final LogInView logInView;
    private final RegistrarseView registrarseView;
    private final UsuarioModificarView usuarioModificarView;
    private final UsuarioEliminarView usuarioEliminarView;
    private final UsuarioModificarMisView usuarioModificarMisView;
    private final UsuarioListarView usuarioListarView;
    private final PreguntasRegisterView preguntasView;
    private final PreguntasModificarView preguntasModificarView;
    private Usuario usuarioAutentificado;
    private Usuario usuarioTemporal;
    private Usuario usuarioEnRecuperacion;

    public UsuarioController(UsuarioCrearView usuarioCrearView, UsuarioDAO usuarioDAO, LogInView logInView,
                             UsuarioModificarView usuarioModificarView, UsuarioEliminarView usuarioEliminarView,
                             UsuarioModificarMisView usuarioModificarMisView, UsuarioListarView usuarioListarView,
                             MensajeInternacionalizacionHandler mensajes, RegistrarseView registrarseView, PreguntasRegisterView preguntasView, PreguntasModificarView preguntasModificarView) {
        this.usuarioCrearView = usuarioCrearView;
        this.usuarioDAO = usuarioDAO;
        this.logInView = logInView;
        this.usuarioModificarView = usuarioModificarView;
        this.usuarioEliminarView = usuarioEliminarView;
        this.usuarioModificarMisView = usuarioModificarMisView;
        this.usuarioListarView = usuarioListarView;
        this.mensajes = mensajes;
        this.registrarseView = registrarseView;
        this.preguntasView = preguntasView;
        this.preguntasModificarView = preguntasModificarView;

        configurarEventos();
    }

    private void configurarEventos() {
        logInView.getBtnIniciarSesion().addActionListener(e -> autentificar());
        logInView.getBtnRegistrarse().addActionListener(e -> {
            registrarseView.setVisible(true);
            logInView.setVisible(false);
        });
        registrarseView.getBtnRegistrarse().addActionListener(e -> {
            procesarDatosDeRegistro();
        });
        preguntasView.getBtnGuardar().addActionListener(e->{
            guardarUsuarioConPreguntas();
        });
        logInView.getBtnOlvidoContraseña().addActionListener(e -> iniciarRecuperacionContraseña());
        preguntasModificarView.getBtnVerificar().addActionListener(e->verificarRespuestasYCambiarContraseña());
        usuarioCrearView.getBtnCrear().addActionListener(e -> crearUsuario());
        usuarioModificarView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaModificar());
        usuarioModificarView.getBtnModificar().addActionListener(e -> modificarUsuario());
        usuarioEliminarView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaEliminar());
        usuarioEliminarView.getBtnEliminar().addActionListener(e -> eliminarUsuario());
        usuarioListarView.getBtnListar().addActionListener(e -> listarTodosLosUsuarios());
        usuarioListarView.getBtnBuscar().addActionListener(e -> buscarUsuarioPorUsername());

        usuarioModificarMisView.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                cargarDatosUsuarioActual();
            }
        });
        usuarioModificarMisView.getBtnModificar().addActionListener(e -> modificarMisDatos());
    }
    private void verificarRespuestasYCambiarContraseña() {
        if (usuarioEnRecuperacion == null) return;

        Map<Pregunta, String> respuestasIngresadas = preguntasModificarView.getRespuestasIngresadas();
        List<RespuestaSeguridad> respuestasGuardadas = usuarioEnRecuperacion.getRespuestasSeguridad();

        boolean todasCorrectas = true;
        for (RespuestaSeguridad guardada : respuestasGuardadas) {
            String respuestaIngresada = respuestasIngresadas.get(guardada.getPregunta());
            if (!guardada.esRespuestaCorrecta(respuestaIngresada)) {
                todasCorrectas = false;
                break;
            }
        }

        if (todasCorrectas) {
            String nuevaPassword = JOptionPane.showInputDialog(
                    preguntasModificarView,
                    mensajes.get("mensaje.pregunta.recuperar.exito")
            );

            if (nuevaPassword != null && !nuevaPassword.trim().isEmpty()) {
                usuarioEnRecuperacion.setPassword(nuevaPassword.trim());
                usuarioDAO.actualizar(usuarioEnRecuperacion);
                JOptionPane.showMessageDialog(preguntasModificarView, mensajes.get("mensaje.contraseña.actualizada"));

                preguntasModificarView.dispose();
                preguntasModificarView.limpiarCampos();
                this.usuarioEnRecuperacion = null;
            }
        } else {
            JOptionPane.showMessageDialog(
                    preguntasModificarView,
                    mensajes.get("mensaje.pregunta.recuperar.error"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private void iniciarRecuperacionContraseña() {
        String username = JOptionPane.showInputDialog(
                logInView,
                mensajes.get("mensaje.pregunta.recuperar.instrucciones"),
                mensajes.get("pregunta.recuperar.titulo"),
                JOptionPane.QUESTION_MESSAGE
        );

        if (username == null || username.isEmpty()) {
            return;
        }

        this.usuarioEnRecuperacion = usuarioDAO.buscarPorUsuario(username.trim());

        if (usuarioEnRecuperacion == null) {
            logInView.mostrarMensaje(mensajes.get("mensaje.pregunta.recuperar.noUsuario"));
            return;
        }

        List<RespuestaSeguridad> respuestas = usuarioEnRecuperacion.getRespuestasSeguridad();
        if (respuestas == null || respuestas.isEmpty()) {
            logInView.mostrarMensaje(mensajes.get("mensaje.pregunta.recuperar.sinPreguntas"));
            return;
        }

        preguntasModificarView.mostrarPreguntasDelUsuario(respuestas);
        preguntasModificarView.setVisible(true);
    }


    private void autentificar() {
        String username = logInView.getTxtUsername().getText();
        String contraseña = new String(logInView.getPsfContraseña().getPassword());

        usuarioAutentificado = usuarioDAO.autenticar(username, contraseña);
        if (usuarioAutentificado == null) {
            logInView.mostrarMensaje(mensajes.get("mensaje.usuario.login.error"));
        } else {
            logInView.dispose();
        }
    }

    private void guardarUsuarioConPreguntas() {
        List<String> respuestasTexto = preguntasView.getRespuestas();

        long respuestasDadas = respuestasTexto.stream().filter(r -> r != null && !r.trim().isEmpty()).count();
        if (respuestasDadas < 3) {
            JOptionPane.showMessageDialog(preguntasView, mensajes.get("mensaje.pregunta.minimoRequerido"));
            return;
        }

        for (int i = 0; i < respuestasTexto.size(); i++) {
            String respuesta = respuestasTexto.get(i);
            if (respuesta != null && !respuesta.trim().isEmpty()) {
                int preguntaId = i + 1;
                String textoPregunta = mensajes.get("pregunta.seguridad." + preguntaId);
                Pregunta pregunta = new Pregunta(preguntaId, textoPregunta);
                usuarioTemporal.addRespuesta(pregunta, respuesta);
            }
        }

        usuarioDAO.crear(usuarioTemporal);

        JOptionPane.showMessageDialog(preguntasView, mensajes.get("mensaje.usuario.registrado"));
        preguntasView.dispose();
        registrarseView.limpiarCampos();
        logInView.setVisible(true);
        this.usuarioTemporal = null;
    }

    private void procesarDatosDeRegistro() {
        String username = registrarseView.getTxtUsername().getText().trim();
        String contraseña = new String(registrarseView.getTxtContraseña().getPassword());
        String repeContra = new String(registrarseView.getTxtRepContra().getPassword());

        if (username.isEmpty() || contraseña.isEmpty()) {
            registrarseView.mostrarMensaje(mensajes.get("mensaje.usuario.registrar.datos"));
            return;
        }
        if (!contraseña.equals(repeContra)) {
            registrarseView.mostrarMensaje(mensajes.get("mensaje.register.contraseñaRepetida"));
            return;
        }
        if (usuarioDAO.buscarPorUsuario(username) != null) {
            registrarseView.mostrarMensaje(mensajes.get("mensaje.usuario.error.nombreUsado"));
            return;
        }

        this.usuarioTemporal = new Usuario(username, Rol.USUARIO, contraseña);
        preguntasView.setVisible(true);
        registrarseView.setVisible(false);
    }



    private void crearUsuario() {
        String username = usuarioCrearView.getTxtUsuario().getText().trim();
        String contraseña = usuarioCrearView.getTxtContraseña().getText().trim();
        Rol rolSeleccionado = (Rol) usuarioCrearView.getCbxRoles().getSelectedItem();

        if (username.isEmpty() || contraseña.isEmpty()) {
            usuarioCrearView.mostrarMensaje(mensajes.get("mensaje.usuario.error.camposVacios"));
            return;
        }
        if (rolSeleccionado == null) {
            usuarioCrearView.mostrarMensaje(mensajes.get("mensaje.usuario.error.rolNoSeleccionado"));
            return;
        }
        if (usuarioDAO.buscarPorUsuario(username) != null) {
            usuarioCrearView.mostrarMensaje(mensajes.get("mensaje.usuario.error.nombreUsado"));
            return;
        }

        Usuario nuevoUsuario = new Usuario(username, rolSeleccionado, contraseña);
        usuarioDAO.crear(nuevoUsuario);
        usuarioCrearView.mostrarMensaje(mensajes.get("mensaje.usuario.creado"));
        usuarioCrearView.limpiarCampos();
    }

    private void buscarUsuarioParaModificar() {
        String username = usuarioModificarView.getTxtUsuario().getText().trim();
        if (username.isEmpty()) {
            usuarioModificarView.mostrarMensaje(mensajes.get("mensaje.usuario.buscar.vacio"));
            return;
        }
        Usuario usuario = usuarioDAO.buscarPorUsuario(username);
        if (usuario == null) {
            usuarioModificarView.mostrarMensaje(mensajes.get("mensaje.usuario.buscar.noEncontrado"));
            return;
        }
        usuarioModificarView.getTxtContraseña().setText(usuario.getPassword());
        usuarioModificarView.getCbxRoles().setSelectedItem(usuario.getRol());
        usuarioModificarView.getTxtUsuario().setEditable(false);
        usuarioModificarView.getBtnBuscar().setEnabled(false);
        usuarioModificarView.getTxtContraseña().setEnabled(true);
        usuarioModificarView.getCbxRoles().setEnabled(true);
        usuarioModificarView.getBtnModificar().setEnabled(true);
    }

    private void modificarUsuario() {
        String username = usuarioModificarView.getTxtUsuario().getText();
        String nuevaContraseña = usuarioModificarView.getTxtContraseña().getText();
        Rol nuevoRol = (Rol) usuarioModificarView.getCbxRoles().getSelectedItem();

        if (nuevaContraseña.isEmpty()) {
            usuarioModificarView.mostrarMensaje(mensajes.get("mensaje.usuario.modificar.vacio"));
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(usuarioModificarView,
                mensajes.get("yesNo.usuario.modificar"), mensajes.get("yesNo.app.titulo"), JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            Usuario usuario = usuarioDAO.buscarPorUsuario(username);
            if (usuario == null) {
                usuarioModificarView.mostrarMensaje(mensajes.get("mensaje.usuario.modificar.noExiste"));
                usuarioModificarView.limpiarCampos();
                return;
            }
            usuario.setPassword(nuevaContraseña);
            usuario.setRol(nuevoRol);
            usuarioDAO.actualizar(usuario);
            usuarioModificarView.mostrarMensaje(mensajes.get("mensaje.usuario.modificarMis.exito"));
            usuarioModificarView.limpiarCampos();
        }
    }

    private void buscarUsuarioParaEliminar() {
        String username = usuarioEliminarView.getTxtUsuario().getText().trim();
        if (username.isEmpty()) {
            usuarioEliminarView.mostrarMensaje(mensajes.get("mensaje.usuario.buscar.vacio"));
            return;
        }
        Usuario usuario = usuarioDAO.buscarPorUsuario(username);
        if (usuario == null) {
            usuarioEliminarView.mostrarMensaje(mensajes.get("mensaje.usuario.buscar.noEncontrado"));
            return;
        }
        usuarioEliminarView.getTxtContraseña().setText(usuario.getPassword());
        usuarioEliminarView.getTxtRol().setText(usuario.getRol() == Rol.ADMINISTRADOR ? mensajes.get("global.rol.admin") : mensajes.get("global.rol.user"));
        usuarioEliminarView.getTxtUsuario().setEditable(false);
        usuarioEliminarView.getBtnBuscar().setEnabled(false);
        usuarioEliminarView.getBtnEliminar().setEnabled(true);
    }

    private void eliminarUsuario() {
        String username = usuarioEliminarView.getTxtUsuario().getText();
        int respuesta = JOptionPane.showConfirmDialog(usuarioEliminarView,
                mensajes.get("yesNo.usuario.eliminar"), mensajes.get("yesNo.app.titulo"), JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            if (usuarioDAO.buscarPorUsuario(username) == null) {
                usuarioEliminarView.mostrarMensaje(mensajes.get("mensaje.usuario.eliminar.noExiste"));
            } else {
                usuarioDAO.eliminar(username);
                usuarioEliminarView.mostrarMensaje(mensajes.get("mensaje.usuario.eliminar.exito"));
            }
            usuarioEliminarView.limpiarCampos();
        }
    }

    private void listarTodosLosUsuarios() {
        usuarioListarView.mostrarUsuarios(usuarioDAO.listarUsuarios());
    }

    private void buscarUsuarioPorUsername() {
        String username = usuarioListarView.getTxtUsuario().getText().trim();
        if (username.isEmpty()) {
            usuarioListarView.mostrarMensaje(mensajes.get("mensaje.usuario.buscarUsername.vacio"));
            return;
        }
        Usuario usuario = usuarioDAO.buscarPorUsuario(username);
        if (usuario != null) {
            List<Usuario> lista = new ArrayList<>();
            lista.add(usuario);
            usuarioListarView.mostrarUsuarios(lista);
        } else {
            usuarioListarView.mostrarUsuarios(new ArrayList<>());
            usuarioListarView.mostrarMensaje(mensajes.get("mensaje.usuario.buscarUsername.noEncontrado"));
        }
    }



    private void cargarDatosUsuarioActual() {
        if (usuarioAutentificado != null) {
            usuarioModificarMisView.getTxtUsuario().setText(usuarioAutentificado.getUsername());
            usuarioModificarMisView.getTxtUsuario().setEditable(true);
            usuarioModificarMisView.getBtnModificar().setEnabled(true);
            usuarioModificarMisView.getTxtContraseña().setText(usuarioAutentificado.getPassword());
        }
    }

    private void modificarMisDatos() {
        if (usuarioAutentificado == null) {
            return;
        }

        String nuevoUsername = usuarioModificarMisView.getTxtUsuario().getText().trim();
        String nuevaPassword = usuarioModificarMisView.getTxtContraseña().getText().trim();
        String usernameOriginal = usuarioAutentificado.getUsername();

        if (nuevoUsername.isEmpty() || nuevaPassword.isEmpty()) {
            usuarioModificarMisView.mostrarMensaje(mensajes.get("mensaje.usuario.modificarMis.incompleto"));
            return;
        }

        if (!nuevoUsername.equals(usernameOriginal) && usuarioDAO.buscarPorUsuario(nuevoUsername) != null) {
            usuarioModificarMisView.mostrarMensaje(mensajes.get("mensaje.usuario.error.nombreUsado"));
            return;
        }

        List<RespuestaSeguridad> respuestasGuardadas = usuarioAutentificado.getRespuestasSeguridad();
        if (respuestasGuardadas == null || respuestasGuardadas.isEmpty()) {
            usuarioModificarMisView.mostrarMensaje(mensajes.get("mensaje.pregunta.recuperar.sinPreguntas"));
            return;
        }

        JPanel panelPreguntas = new JPanel(new GridLayout(respuestasGuardadas.size() + 1, 2, 10, 5));
        panelPreguntas.add(new JLabel("Para confirmar, responda sus preguntas:"));
        panelPreguntas.add(new JLabel("")); // Espacio en blanco

        List<JTextField> camposDeRespuesta = new ArrayList<>();
        for (RespuestaSeguridad resp : respuestasGuardadas) {
            panelPreguntas.add(new JLabel(resp.getPregunta().getTexto()));
            JTextField campoRespuesta = new JTextField();
            camposDeRespuesta.add(campoRespuesta);
            panelPreguntas.add(campoRespuesta);
        }

        int resultado = JOptionPane.showConfirmDialog(
                usuarioModificarMisView,
                panelPreguntas,
                mensajes.get("yesNo.app.titulo"),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            boolean todasCorrectas = true;
            for (int i = 0; i < respuestasGuardadas.size(); i++) {
                String respuestaIngresada = camposDeRespuesta.get(i).getText();
                if (!respuestasGuardadas.get(i).esRespuestaCorrecta(respuestaIngresada)) {
                    todasCorrectas = false;
                    break;
                }
            }

            if (todasCorrectas) {
                if (!usernameOriginal.equals(nuevoUsername)) {
                    usuarioDAO.eliminar(usernameOriginal);
                }
                usuarioAutentificado.setUsername(nuevoUsername);
                usuarioAutentificado.setPassword(nuevaPassword);
                usuarioDAO.crear(usuarioAutentificado);

                usuarioModificarMisView.mostrarMensaje(mensajes.get("mensaje.usuario.modificarMis.exito"));
                usuarioModificarMisView.dispose();
            } else {
                usuarioModificarMisView.mostrarMensaje(mensajes.get("mensaje.pregunta.recuperar.error"));
            }
        }
    }


    public Usuario getUsuarioAutentificado() {
        return usuarioAutentificado;
    }
}