package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private final UsuarioDAO usuarioDAO;
    private final MensajeInternacionalizacionHandler mensajes;

    private final UsuarioCrearView usuarioCrearView;
    private final LogInView logInView;
    private final UsuarioModificarView usuarioModificarView;
    private final UsuarioEliminarView usuarioEliminarView;
    private final UsuarioModificarMisView usuarioModificarMisView;
    private final UsuarioListarView usuarioListarView;

    private Usuario usuarioAutentificado;

    public UsuarioController(UsuarioCrearView usuarioCrearView, UsuarioDAO usuarioDAO, LogInView logInView,
                             UsuarioModificarView usuarioModificarView, UsuarioEliminarView usuarioEliminarView,
                             UsuarioModificarMisView usuarioModificarMisView, UsuarioListarView usuarioListarView,
                             MensajeInternacionalizacionHandler mensajes) {
        this.usuarioCrearView = usuarioCrearView;
        this.usuarioDAO = usuarioDAO;
        this.logInView = logInView;
        this.usuarioModificarView = usuarioModificarView;
        this.usuarioEliminarView = usuarioEliminarView;
        this.usuarioModificarMisView = usuarioModificarMisView;
        this.usuarioListarView = usuarioListarView;
        this.mensajes = mensajes;

        configurarEventos();
    }

    private void configurarEventos() {
        logInView.getBtnIniciarSesion().addActionListener(e -> autentificar());
        logInView.getBtnRegistrarse().addActionListener(e -> registrarUsuario());

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

    private void registrarUsuario() {
        String username = logInView.getTxtUsername().getText().trim();
        String contraseña = new String(logInView.getPsfContraseña().getPassword());

        if (username.isEmpty() || contraseña.isEmpty()) {
            logInView.mostrarMensaje(mensajes.get("mensaje.usuario.registrar.datos"));
            return;
        }

        if (usuarioDAO.buscarPorUsuario(username) != null) {
            logInView.mostrarMensaje(mensajes.get("mensaje.usuario.error.nombreUsado"));
            return;
        }

        Usuario nuevoUsuario = new Usuario(username, Rol.USUARIO, contraseña);
        usuarioDAO.crear(nuevoUsuario);
        logInView.mostrarMensaje(mensajes.get("mensaje.usuario.registrado"));
        logInView.getTxtUsername().setText("");
        logInView.getPsfContraseña().setText("");
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
            usuarioModificarMisView.getTxtContraseña().setText(usuarioAutentificado.getPassword());
        }
    }

    private void modificarMisDatos() {
        String nuevaContraseña = usuarioModificarMisView.getTxtContraseña().getText();
        if (nuevaContraseña.isEmpty()) {
            usuarioModificarMisView.mostrarMensaje(mensajes.get("mensaje.usuario.modificarMis.vacio"));
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(usuarioModificarMisView,
                mensajes.get("yesNo.usuario.modificarMis"), mensajes.get("yesNo.app.titulo"), JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            Usuario usuario = usuarioDAO.buscarPorUsuario(usuarioAutentificado.getUsername());
            if (usuario == null) {
                usuarioModificarMisView.mostrarMensaje(mensajes.get("mensaje.usuario.modificarMis.noExiste"));
                return;
            }
            usuario.setPassword(nuevaContraseña);
            usuarioDAO.actualizar(usuario);
            usuarioModificarMisView.mostrarMensaje(mensajes.get("mensaje.usuario.modificarMis.exito"));
            usuarioModificarMisView.dispose();
        }
    }

    public Usuario getUsuarioAutentificado() {
        return usuarioAutentificado;
    }
}