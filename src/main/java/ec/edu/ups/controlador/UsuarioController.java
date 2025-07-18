/**
 * Controlador principal para la gestión de usuarios en el sistema.
 * Maneja autenticación, registro, recuperación de contraseña y operaciones CRUD de usuarios.
 * Coordina la interacción entre las vistas, los DAOs y los modelos de usuario.
 *
 * @author Einar Kaalhus
 * @version 1.0
 * @since 2023-05-15
 */
package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.Respuesta;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.CedulaValidatorException;
import ec.edu.ups.util.ContraseñaValidatorException;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.util.Sonido;
import ec.edu.ups.vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsuarioController {
    // DAOs para acceso a datos
    private final UsuarioDAO usuarioDAO;
    private final PreguntaDAO preguntaDAO;

    // Handler para mensajes internacionalizados
    private final MensajeInternacionalizacionHandler mensajes;

    // Vistas asociadas al controlador
    private final UsuarioCrearView usuarioCrearView;
    private final LogInView logInView;
    private final RegistrarseView registrarseView;
    private final UsuarioModificarView usuarioModificarView;
    private final UsuarioEliminarView usuarioEliminarView;
    private final UsuarioModificarMisView usuarioModificarMisView;
    private final UsuarioListarView usuarioListarView;
    private final PreguntasRegisterView preguntasView;
    private final PreguntasModificarView preguntasModificarView;

    // Estado del controlador
    private Usuario usuarioAutentificado;
    private Usuario usuarioTemporal;
    private Usuario usuarioEnRecuperacion;

    /**
     * Constructor principal del controlador de usuarios.
     *
     * @param usuarioCrearView Vista para creación de usuarios (admin)
     * @param usuarioDAO DAO para operaciones con usuarios
     * @param preguntaDAO DAO para operaciones con preguntas de seguridad
     * @param logInView Vista de inicio de sesión
     * @param usuarioModificarView Vista para modificación de usuarios (admin)
     * @param usuarioEliminarView Vista para eliminación de usuarios (admin)
     * @param usuarioModificarMisView Vista para que usuarios modifiquen sus datos
     * @param usuarioListarView Vista para listar usuarios (admin)
     * @param mensajes Handler para mensajes internacionalizados
     * @param registrarseView Vista de registro de nuevos usuarios
     * @param preguntasView Vista para gestión de preguntas de seguridad
     * @param preguntasModificarView Vista para modificar preguntas de seguridad
     */
    public UsuarioController(UsuarioCrearView usuarioCrearView, UsuarioDAO usuarioDAO, PreguntaDAO preguntaDAO, LogInView logInView,
                             UsuarioModificarView usuarioModificarView, UsuarioEliminarView usuarioEliminarView,
                             UsuarioModificarMisView usuarioModificarMisView, UsuarioListarView usuarioListarView,
                             MensajeInternacionalizacionHandler mensajes, RegistrarseView registrarseView,
                             PreguntasRegisterView preguntasView, PreguntasModificarView preguntasModificarView) {
        this.usuarioCrearView = usuarioCrearView;
        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
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

    /**
     * Configura los listeners de eventos para todas las vistas asociadas.
     */
    private void configurarEventos() {
        // Eventos para vista de login
        logInView.getBtnIniciarSesion().addActionListener(e -> autentificar());
        logInView.getBtnRegistrarse().addActionListener(e -> {
            registrarseView.setVisible(true);
            logInView.setVisible(false);
        });
        logInView.getBtnOlvidoContraseña().addActionListener(e -> iniciarRecuperacionContraseña());

        // Eventos para vista de registro
        registrarseView.getBtnRegistrarse().addActionListener(e -> procesarDatosDeRegistro());
        registrarseView.getBtnAtras().addActionListener(e -> {
            logInView.limpiarCampos();
            registrarseView.setVisible(false);
            logInView.setVisible(true);
        });

        // Eventos para vista de preguntas de seguridad
        preguntasView.getBtnGuardar().addActionListener(e -> guardarUsuarioConPreguntas());
        preguntasModificarView.getBtnVerificar().addActionListener(e -> verificarRespuestasYCambiarContraseña());

        // Eventos para vista de creación de usuarios (admin)
        usuarioCrearView.getBtnCrear().addActionListener(e -> crearUsuario());

        // Eventos para vista de modificación de usuarios (admin)
        usuarioModificarView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaModificar());
        usuarioModificarView.getBtnModificar().addActionListener(e -> modificarUsuario());

        // Eventos para vista de eliminación de usuarios (admin)
        usuarioEliminarView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaEliminar());
        usuarioEliminarView.getBtnEliminar().addActionListener(e -> eliminarUsuario());

        // Eventos para vista de listado de usuarios
        usuarioListarView.getBtnListar().addActionListener(e -> listarTodosLosUsuarios());
        usuarioListarView.getBtnBuscar().addActionListener(e -> buscarUsuarioPorUsername());

        // Eventos para vista de modificación de datos personales
        usuarioModificarMisView.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                cargarDatosUsuarioActual();
            }
        });
        usuarioModificarMisView.getBtnModificar().addActionListener(e -> modificarMisDatos());
    }

    /**
     * Inicia el proceso de recuperación de contraseña solicitando el nombre de usuario
     * y mostrando una pregunta de seguridad aleatoria asociada.
     */
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

        this.usuarioEnRecuperacion = usuarioDAO.buscarPorUsuario(username);

        if (usuarioEnRecuperacion == null) {
            logInView.mostrarMensaje(mensajes.get("mensaje.pregunta.recuperar.noUsuario"));
            return;
        }

        List<Respuesta> respuestas = usuarioEnRecuperacion.getRespuestasSeguridad();
        if (respuestas == null || respuestas.isEmpty()) {
            usuarioTemporal = usuarioDAO.buscarPorUsuario(username);
            logInView.mostrarMensaje(mensajes.get("mensaje.pregunta.recuperar.sinPreguntas"));
            preguntasView.setVisible(true);
            preguntasView.toFront();
            return;
        }

        // Seleccionar pregunta de seguridad aleatoria
        Random random = new Random();
        int indiceAleatorio = random.nextInt(respuestas.size());
        Respuesta preguntaAleatoria = respuestas.get(indiceAleatorio);

        List<Respuesta> listaConUnaPregunta = new ArrayList<>();
        listaConUnaPregunta.add(preguntaAleatoria);

        preguntasModificarView.mostrarPreguntasDelUsuario(listaConUnaPregunta);
        preguntasModificarView.setVisible(true);
    }

    /**
     * Verifica las respuestas de seguridad ingresadas y permite cambiar la contraseña
     * si la respuesta es correcta.
     */
    private void verificarRespuestasYCambiarContraseña() {
        if (usuarioEnRecuperacion == null) return;

        Map<Pregunta, String> respuestasIngresadas = preguntasModificarView.getRespuestasIngresadas();

        if (respuestasIngresadas.isEmpty()) {
            return;
        }

        // Obtener la única pregunta/respuesta mostrada
        Map.Entry<Pregunta, String> unicaRespuestaEntry = respuestasIngresadas.entrySet().iterator().next();
        Pregunta preguntaMostrada = unicaRespuestaEntry.getKey();
        String respuestaIngresada = unicaRespuestaEntry.getValue();

        // Buscar la respuesta original almacenada
        Respuesta respuestaOriginal = null;
        for (Respuesta guardada : usuarioEnRecuperacion.getRespuestasSeguridad()) {
            if (guardada.getPregunta().equals(preguntaMostrada)) {
                respuestaOriginal = guardada;
                break;
            }
        }

        if (respuestaOriginal != null && respuestaOriginal.esRespuestaCorrecta(respuestaIngresada)) {
            // Solicitar nueva contraseña
            String nuevaPassword = JOptionPane.showInputDialog(
                    preguntasModificarView,
                    mensajes.get("mensaje.pregunta.recuperar.exito")
            );

            if (nuevaPassword != null && !nuevaPassword.isEmpty()) {
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

            // Mostrar nueva pregunta aleatoria
            List<Respuesta> respuestas = usuarioEnRecuperacion.getRespuestasSeguridad();
            Random random = new Random();
            int indiceAleatorio = random.nextInt(respuestas.size());
            Respuesta preguntaAleatoria = respuestas.get(indiceAleatorio);

            List<Respuesta> listaConUnaPregunta = new ArrayList<>();
            listaConUnaPregunta.add(preguntaAleatoria);

            preguntasModificarView.mostrarPreguntasDelUsuario(listaConUnaPregunta);
            preguntasModificarView.setVisible(true);
        }
    }

    /**
     * Modifica los datos personales del usuario autenticado, previa verificación
     * mediante una pregunta de seguridad aleatoria.
     */
    private void modificarMisDatos() {
        if (usuarioAutentificado == null) {
            return;
        }

        // Obtener datos de la vista
        String nuevoUsername = usuarioModificarMisView.getTxtNuevoUser().getText().trim();
        String nuevaPassword = usuarioModificarMisView.getTxtContraseña().getText().trim();
        String nombreCompleto = usuarioModificarMisView.getTxtNombreCom().getText().trim();
        String email = usuarioModificarMisView.getTxtEmail().getText().trim();
        String telefono = usuarioModificarMisView.getTxtTelefono().getText().trim();
        int edad = (int) usuarioModificarMisView.getSprEdad().getValue();
        ec.edu.ups.modelo.Genero genero = (ec.edu.ups.modelo.Genero) usuarioModificarMisView.getCbxGenero().getSelectedItem();
        String usernameOriginal = usuarioAutentificado.getUsername();

        // Validaciones
        if (nuevoUsername.isEmpty() || nuevaPassword.isEmpty() || nombreCompleto.isEmpty() ||
                email.isEmpty() || telefono.isEmpty()) {
            usuarioModificarMisView.mostrarMensaje(mensajes.get("mensaje.usuario.modificarMis.incompleto"));
            return;
        }
        if (genero == null) {
            usuarioModificarMisView.mostrarMensaje(mensajes.get("mensaje.genero"));
            return;
        }
        if (!nuevoUsername.equalsIgnoreCase(usernameOriginal) && usuarioDAO.buscarPorUsuario(nuevoUsername) != null) {
            usuarioModificarMisView.mostrarMensaje(mensajes.get("mensaje.usuario.error.nombreUsado"));
            return;
        }

        List<Respuesta> respuestasGuardadas = usuarioAutentificado.getRespuestasSeguridad();
        if (respuestasGuardadas == null || respuestasGuardadas.isEmpty()) {
            usuarioModificarMisView.mostrarMensaje(mensajes.get("mensaje.pregunta.recuperar.sinPreguntas"));
            usuarioTemporal = usuarioAutentificado;
            preguntasView.setVisible(true);
            preguntasView.toFront();
            return;
        }

        // Verificación mediante pregunta de seguridad aleatoria
        Random random = new Random();
        while (true) {
            int indiceAleatorio = random.nextInt(respuestasGuardadas.size());
            Respuesta preguntaAleatoria = respuestasGuardadas.get(indiceAleatorio);

            JPanel panelPregunta = new JPanel(new GridLayout(2, 1, 10, 5));
            panelPregunta.add(new JLabel(preguntaAleatoria.getPregunta().getTexto()));
            JTextField campoRespuesta = new JTextField();
            panelPregunta.add(campoRespuesta);

            int resultado = JOptionPane.showConfirmDialog(
                    usuarioModificarMisView,
                    panelPregunta,
                    mensajes.get("yesNo.app.titulo"),
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (resultado == JOptionPane.OK_OPTION) {
                String respuestaIngresada = campoRespuesta.getText();
                if (preguntaAleatoria.esRespuestaCorrecta(respuestaIngresada)) {
                    // Actualizar datos del usuario
                    usuarioAutentificado.setUsername(nuevoUsername);
                    usuarioAutentificado.setPassword(nuevaPassword);
                    usuarioAutentificado.setNombreCompleto(nombreCompleto);
                    usuarioAutentificado.setEdad(edad);
                    usuarioAutentificado.setGenero(genero);
                    usuarioAutentificado.setTelefono(telefono);
                    usuarioAutentificado.setEmail(email);

                    // Actualizar en DAO
                    if (!usernameOriginal.equalsIgnoreCase(nuevoUsername)) {
                        usuarioDAO.eliminar(usernameOriginal);
                        usuarioDAO.crear(usuarioAutentificado);
                    } else {
                        usuarioDAO.actualizar(usuarioAutentificado);
                    }

                    usuarioModificarMisView.mostrarMensaje(mensajes.get("mensaje.usuario.modificarMis.exito"));
                    usuarioModificarMisView.dispose();
                    break;

                } else {
                    usuarioModificarMisView.mostrarMensaje(mensajes.get("mensaje.pregunta.recuperar.error"));
                }
            } else {
                break;
            }
        }
    }

    /**
     * Autentica un usuario en el sistema.
     */
    private void autentificar() {
        String username = logInView.getTxtUsername().getText();
        String contraseña = new String(logInView.getPsfContraseña().getPassword());

        usuarioAutentificado = usuarioDAO.autenticar(username, contraseña);
        if (usuarioAutentificado == null) {
            logInView.mostrarMensaje(mensajes.get("mensaje.usuario.login.error"));
        } else {
            logInView.dispose();
            // Reproducir sonido de inicio de sesión exitoso
            Sonido sonido = new Sonido();
            sonido.cargarSonido("/sonidoInicio.wav");
            sonido.reproducir();
        }
    }

    /**
     * Guarda un nuevo usuario con sus preguntas de seguridad en la base de datos.
     */
    private void guardarUsuarioConPreguntas() {
        Map<Pregunta, String> respuestasIngresadas = preguntasView.getRespuestasIngresadas();

        // Validar mínimo de respuestas
        long respuestasDadas = respuestasIngresadas.values().stream()
                .filter(r -> r != null && !r.trim().isEmpty()).count();
        if (respuestasDadas < 3) {
            JOptionPane.showMessageDialog(preguntasView, mensajes.get("mensaje.pregunta.minimoRequerido"));
            return;
        }

        // Asociar respuestas al usuario
        respuestasIngresadas.forEach((pregunta, respuestaTexto) -> {
            if (respuestaTexto != null && !respuestaTexto.trim().isEmpty()) {
                pregunta.setTexto(mensajes.get("pregunta.seguridad." + pregunta.getId()));
                usuarioTemporal.addRespuesta(pregunta, respuestaTexto);
            }
        });

        usuarioDAO.crear(usuarioTemporal);

        JOptionPane.showMessageDialog(preguntasView, mensajes.get("mensaje.usuario.registrado"));
        preguntasView.dispose();
        registrarseView.limpiarCampos();
        if (usuarioTemporal != usuarioAutentificado) {
            logInView.setVisible(true);
            this.usuarioTemporal = null;
        }
    }

    /**
     * Procesa los datos de registro de un nuevo usuario y muestra la vista de
     * preguntas de seguridad si los datos son válidos.
     */
    private void procesarDatosDeRegistro() {
        try {
            // Obtener datos del formulario
            String username = registrarseView.getTxtUsername().getText().trim();
            String contraseña = new String(registrarseView.getTxtContraseña().getPassword());
            String repeContra = new String(registrarseView.getTxtRepContra().getPassword());
            String nombreCompleto = registrarseView.getTxtNombreCom().getText().trim();
            String email = registrarseView.getTxtEmail().getText().trim();
            String telefono = registrarseView.getTxtTelefono().getText().trim();
            int edad = (int) registrarseView.getSpnEdad().getValue();
            ec.edu.ups.modelo.Genero genero = (ec.edu.ups.modelo.Genero) registrarseView.getCbxGenero().getSelectedItem();

            // Validaciones
            if (username.isEmpty() || contraseña.isEmpty() || nombreCompleto.isEmpty() ||
                    email.isEmpty() || telefono.isEmpty()) {
                registrarseView.mostrarMensaje(mensajes.get("mensaje.usuario.modificarMis.incompleto"));
                return;
            }
            if (genero == null) {
                registrarseView.mostrarMensaje("Por favor, seleccione un género.");
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

            // Crear usuario temporal
            this.usuarioTemporal = new Usuario(Rol.USUARIO, nombreCompleto, edad, genero, telefono, email);
            usuarioTemporal.setUsername(username);
            usuarioTemporal.setPassword(contraseña);

            // Cargar preguntas de seguridad
            List<Pregunta> preguntas = preguntaDAO.obtenerTodasLasPreguntas();
            preguntasView.mostrarPreguntas(preguntas);
            preguntasView.setVisible(true);
            registrarseView.setVisible(false);

        } catch (CedulaValidatorException e) {
            registrarseView.mostrarMensaje(mensajes.get("excepcion.usuario.cedula"));
        } catch (ContraseñaValidatorException e) {
            registrarseView.mostrarMensaje(mensajes.get("excepcion.usuario.contraseña"));
        }
    }

    /**
     * Crea un nuevo usuario desde la vista de administración.
     */
    private void crearUsuario() {
        String username = usuarioCrearView.getTxtUsuario().getText().trim();
        String contraseña = usuarioCrearView.getTxtContraseña().getText().trim();
        Rol rolSeleccionado = (Rol) usuarioCrearView.getCbxRoles().getSelectedItem();

        // Validaciones
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

        // Crear y guardar usuario
        Usuario nuevoUsuario = new Usuario(username, rolSeleccionado, contraseña, null, 0, null, null, null);
        usuarioDAO.crear(nuevoUsuario);
        usuarioCrearView.mostrarMensaje(mensajes.get("mensaje.usuario.creado"));
        usuarioCrearView.limpiarCampos();
    }

    /**
     * Busca un usuario para modificar desde la vista de administración.
     */
    private void buscarUsuarioParaModificar() {
        String username = usuarioModificarView.getTxtUsuario().getText().trim();

        // Validaciones
        if (username.isEmpty()) {
            usuarioModificarView.mostrarMensaje(mensajes.get("mensaje.usuario.buscar.vacio"));
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsuario(username);
        if (usuario == null) {
            usuarioModificarView.mostrarMensaje(mensajes.get("mensaje.usuario.buscar.noEncontrado"));
            return;
        }

        // Mostrar datos del usuario encontrado
        usuarioModificarView.getTxtContraseña().setText(usuario.getPassword());
        usuarioModificarView.getCbxRoles().setSelectedItem(usuario.getRol());
        usuarioModificarView.getTxtUsuario().setEditable(false);
        usuarioModificarView.getBtnBuscar().setEnabled(false);
        usuarioModificarView.getTxtContraseña().setEnabled(true);
        usuarioModificarView.getCbxRoles().setEnabled(true);
        usuarioModificarView.getBtnModificar().setEnabled(true);
    }

    /**
     * Modifica un usuario existente desde la vista de administración.
     */
    private void modificarUsuario() {
        String username = usuarioModificarView.getTxtUsuario().getText();
        String nuevaContraseña = usuarioModificarView.getTxtContraseña().getText();
        Rol nuevoRol = (Rol) usuarioModificarView.getCbxRoles().getSelectedItem();

        // Validaciones
        if (nuevaContraseña.isEmpty()) {
            usuarioModificarView.mostrarMensaje(mensajes.get("mensaje.usuario.modificar.vacio"));
            return;
        }

        // Confirmación
        int respuesta = JOptionPane.showConfirmDialog(usuarioModificarView,
                mensajes.get("yesNo.usuario.modificar"),
                mensajes.get("yesNo.app.titulo"),
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            Usuario usuario = usuarioDAO.buscarPorUsuario(username);
            if (usuario == null) {
                usuarioModificarView.mostrarMensaje(mensajes.get("mensaje.usuario.modificar.noExiste"));
                usuarioModificarView.limpiarCampos();
                return;
            }

            // Actualizar y guardar cambios
            usuario.setPassword(nuevaContraseña);
            usuario.setRol(nuevoRol);
            usuarioDAO.actualizar(usuario);
            usuarioModificarView.mostrarMensaje(mensajes.get("mensaje.usuario.modificarMis.exito"));
            usuarioModificarView.limpiarCampos();
        }
    }

    /**
     * Busca un usuario para eliminar desde la vista de administración.
     */
    private void buscarUsuarioParaEliminar() {
        String username = usuarioEliminarView.getTxtUsuario().getText().trim();

        // Validaciones
        if (username.isEmpty()) {
            usuarioEliminarView.mostrarMensaje(mensajes.get("mensaje.usuario.buscar.vacio"));
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsuario(username);
        if (usuario == null) {
            usuarioEliminarView.mostrarMensaje(mensajes.get("mensaje.usuario.buscar.noEncontrado"));
            return;
        }

        // Mostrar datos del usuario encontrado
        usuarioEliminarView.getTxtContraseña().setText(usuario.getPassword());
        usuarioEliminarView.getTxtRol().setText(
                usuario.getRol() == Rol.ADMINISTRADOR ?
                        mensajes.get("global.rol.admin") :
                        mensajes.get("global.rol.user"));
        usuarioEliminarView.getTxtUsuario().setEditable(false);
        usuarioEliminarView.getBtnBuscar().setEnabled(false);
        usuarioEliminarView.getBtnEliminar().setEnabled(true);
    }

    /**
     * Elimina un usuario existente desde la vista de administración.
     */
    private void eliminarUsuario() {
        String username = usuarioEliminarView.getTxtUsuario().getText();

        // Confirmación
        int respuesta = JOptionPane.showConfirmDialog(usuarioEliminarView,
                mensajes.get("yesNo.usuario.eliminar"),
                mensajes.get("yesNo.app.titulo"),
                JOptionPane.YES_NO_OPTION);

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

    /**
     * Lista todos los usuarios existentes en el sistema.
     */
    private void listarTodosLosUsuarios() {
        usuarioListarView.mostrarUsuarios(usuarioDAO.listarUsuarios());
    }

    /**
     * Busca un usuario por su nombre de usuario y lo muestra en la vista de listado.
     */
    private void buscarUsuarioPorUsername() {
        String username = usuarioListarView.getTxtUsuario().getText().trim();

        // Validaciones
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

    /**
     * Carga los datos del usuario autenticado en la vista de modificación personal.
     */
    private void cargarDatosUsuarioActual() {
        if (usuarioAutentificado != null) {
            usuarioModificarMisView.getTxtUsuario().setText(usuarioAutentificado.getUsername());
            usuarioModificarMisView.getTxtUsuario().setEditable(true);
            usuarioModificarMisView.getBtnModificar().setEnabled(true);
            usuarioModificarMisView.getTxtContraseña().setText(usuarioAutentificado.getPassword());
            usuarioModificarMisView.getSprEdad().setModel(
                    new SpinnerNumberModel(usuarioAutentificado.getEdad(), 18, 120, 1));
            usuarioModificarMisView.getCbxGenero().setSelectedItem(usuarioAutentificado.getGenero());
            usuarioModificarMisView.getTxtTelefono().setText(usuarioAutentificado.getTelefono());
            usuarioModificarMisView.getTxtEmail().setText(usuarioAutentificado.getEmail());
            usuarioModificarMisView.getTxtNombreCom().setText(usuarioAutentificado.getNombreCompleto());
        }
    }

    /**
     * Obtiene el usuario actualmente autenticado en el sistema.
     *
     * @return Usuario autenticado o null si no hay ninguno
     */
    public Usuario getUsuarioAutentificado() {
        return usuarioAutentificado;
    }
}