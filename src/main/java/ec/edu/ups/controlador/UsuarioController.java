package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.*;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private Usuario usuarioAutentificado;
    private final UsuarioCrearView usuarioCrearView;
    private final UsuarioDAO usuarioDAO;
    private final LogInView logInView;
    private final UsuarioModificarView usuarioModificarView;
    private final UsuarioEliminarView usuarioEliminarView;
    private final UsuarioModificarMisView usuarioModificarMisView;
    private final UsuarioListarView usuarioListarView;

    public UsuarioController(UsuarioCrearView usuarioCrearView, UsuarioDAO usuarioDAO, LogInView logInView, UsuarioModificarView usuarioModificarView, UsuarioEliminarView usuarioEliminarView, UsuarioModificarMisView usuarioModificarMisView, UsuarioListarView usuarioListarView) {
        this.usuarioCrearView = usuarioCrearView;
        this.usuarioDAO = usuarioDAO;
        this.logInView = logInView;
        this.usuarioModificarView = usuarioModificarView;
        this.usuarioEliminarView = usuarioEliminarView;
        this.usuarioModificarMisView = usuarioModificarMisView;
        this.usuarioListarView = usuarioListarView;

        configurarEventos();
    }

    private void configurarEventos(){
        logInView.getBtnIniciarSesion().addActionListener(e -> autentificar());
        logInView.getBtnRegistrarse().addActionListener(e -> registrarUsuario());
        usuarioCrearView.getBtnCrear().addActionListener(e -> crearUsuario());
        usuarioModificarView.getBtnBuscar().addActionListener(e-> buscarUsuarioModificar());
        usuarioModificarView.getBtnModificar().addActionListener(e-> modificarUsuario());
        usuarioEliminarView.getBtnBuscar().addActionListener(e-> buscarUsuarioEliminar());
        usuarioEliminarView.getBtnEliminar().addActionListener(e-> eliminarUsuario());
        usuarioListarView.getBtnListar().addActionListener(e-> listarUsuarios());
        usuarioListarView.getBtnBuscar().addActionListener(e -> buscarUsuarioPorUsername());
        usuarioModificarMisView.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                super.internalFrameActivated(e);
                añadirDatos();
            }
        });
        usuarioModificarMisView.getBtnModificar().addActionListener(e -> modificarUsuarioMis());

    }
    private void modificarUsuarioMis() {
        String username = usuarioAutentificado.getUsername();

        if (username.isEmpty() || !usuarioModificarMisView.getBtnModificar().isEnabled()) {
            usuarioModificarMisView.mostrarMensaje("Porfavor llenar todos los campos");
            return;
        }
        String nuevoNombre = usuarioModificarMisView.getTxtUsuario().getText();
        String nuevaContraseña = usuarioModificarMisView.getTxtContraseña().getText();

        if (nuevaContraseña.isEmpty()) {
            usuarioModificarMisView.mostrarMensaje("La contraseña no puede estar vacía.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                usuarioModificarMisView,
                "¿Está seguro de que desea guardar los cambios para el usuario '" + username + "'?",
                "Confirmar Modificación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            Usuario usuarioAModificar = usuarioDAO.buscarPorUsuario(username);

            if (usuarioAModificar == null) {
                usuarioModificarMisView.mostrarMensaje("Error: El usuario ya no existe en la base de datos.");
                usuarioModificarMisView.limpiarCampos();
                return;
            }
            usuarioAModificar.setUsername(nuevoNombre);
            usuarioAModificar.setPassword(nuevaContraseña);

            usuarioDAO.actualizar(usuarioAModificar);

            usuarioModificarMisView.mostrarMensaje("Usuario modificado con éxito.");
            usuarioModificarMisView.limpiarCampos();
            usuarioModificarMisView.dispose();
        }
    }

    private void añadirDatos() {
        usuarioModificarMisView.getTxtUsuario().setText(usuarioAutentificado.getUsername());
        usuarioModificarMisView.getTxtContraseña().setText(usuarioAutentificado.getPassword());
        usuarioModificarMisView.getBtnModificar().setEnabled(true);
    }

    private void buscarUsuarioPorUsername() {
        String username = usuarioListarView.getTxtUsuario().getText().trim();

        if (username.isEmpty()) {
            usuarioListarView.mostrarMensaje("Por favor, ingrese un nombre de usuario para buscar.");
            return;
        }

        Usuario usuarioEncontrado = usuarioDAO.buscarPorUsuario(username);

        if (usuarioEncontrado != null) {
            List<Usuario> listaDeUnUsuario = new ArrayList<>();
            listaDeUnUsuario.add(usuarioEncontrado);
            usuarioListarView.mostrarUsuarios(listaDeUnUsuario);
        } else {
            usuarioListarView.mostrarUsuarios(new ArrayList<>());
            usuarioListarView.mostrarMensaje("No se encontró ningún usuario con el nombre: " + username);
        }
    }

    private void listarUsuarios() {
        usuarioListarView.mostrarUsuarios(usuarioDAO.listarUsuarios());
    }

    private void eliminarUsuario() {
        String usernameEncontrado = usuarioEliminarView.getTxtUsuario().getText();
        if (usernameEncontrado.isEmpty() || !usuarioEliminarView.getBtnEliminar().isEnabled()) {
            usuarioEliminarView.mostrarMensaje("Primero debe buscar un usuario para poder modificarlo.");
            return;
        }


        int respuesta = JOptionPane.showConfirmDialog(
                usuarioModificarView,
                "¿Está seguro de que desea guardar los cambios para el usuario '" + usernameEncontrado + "'?",
                "Confirmar Modificación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            Usuario usuarioAModificar = usuarioDAO.buscarPorUsuario(usernameEncontrado);

            if (usuarioAModificar == null) {
                usuarioModificarView.mostrarMensaje("Error: El usuario ya no existe en la base de datos.");
                usuarioModificarView.limpiarCampos();
                return;
            }
            usuarioDAO.eliminar(usernameEncontrado);
            usuarioModificarView.mostrarMensaje("Usuario eliminado con éxito.");
        }


    }

    private void buscarUsuarioEliminar() {
        String usernameABuscar = usuarioEliminarView.getTxtUsuario().getText().trim();

        if (usernameABuscar.isEmpty()) {
            usuarioEliminarView.mostrarMensaje("Ingrese un nombre de usuario para buscar.");
            return;
        }

        Usuario usuarioEncontrado = usuarioDAO.buscarPorUsuario(usernameABuscar);

        if (usuarioEncontrado == null) {
            usuarioEliminarView.mostrarMensaje("Usuario no encontrado: " + usernameABuscar);
            return;
        }

        usuarioEliminarView.getTxtContraseña().setText(usuarioEncontrado.getPassword());
        usuarioEliminarView.getTxtRol().setText(String.valueOf(usuarioEncontrado.getRol()));

        usuarioEliminarView.getTxtUsuario().setEnabled(false);
        usuarioEliminarView.getBtnBuscar().setEnabled(false);
        usuarioEliminarView.getBtnEliminar().setEnabled(true);
    }

    private void modificarUsuario() {
        String username = usuarioModificarView.getTxtUsuario().getText();

        if (username.isEmpty() || !usuarioModificarView.getBtnModificar().isEnabled()) {
            usuarioModificarView.mostrarMensaje("Primero debe buscar un usuario para poder modificarlo.");
            return;
        }

        String nuevaContraseña = usuarioModificarView.getTxtContraseña().getText();
        Rol nuevoRol = (Rol) usuarioModificarView.getCbxRoles().getSelectedItem();

        if (nuevaContraseña.isEmpty()) {
            usuarioModificarView.mostrarMensaje("La contraseña no puede estar vacía.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                usuarioModificarView,
                "¿Está seguro de que desea guardar los cambios para el usuario '" + username + "'?",
                "Confirmar Modificación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {
        Usuario usuarioAModificar = usuarioDAO.buscarPorUsuario(username);

        if (usuarioAModificar == null) {
            usuarioModificarView.mostrarMensaje("Error: El usuario ya no existe en la base de datos.");
            usuarioModificarView.limpiarCampos();
            return;
        }

        usuarioAModificar.setPassword(nuevaContraseña);
        usuarioAModificar.setRol(nuevoRol);

        usuarioDAO.actualizar(usuarioAModificar);

        usuarioModificarView.mostrarMensaje("Usuario modificado con éxito.");
        usuarioModificarView.limpiarCampos();
    }
    }

    private void buscarUsuarioModificar() {
        String usernameABuscar = usuarioModificarView.getTxtUsuario().getText().trim();

        if (usernameABuscar.isEmpty()) {
            usuarioModificarView.mostrarMensaje("Ingrese un nombre de usuario para buscar.");
            return;
        }

        Usuario usuarioEncontrado = usuarioDAO.buscarPorUsuario(usernameABuscar);

        if (usuarioEncontrado == null) {
            usuarioModificarView.mostrarMensaje("Usuario no encontrado: " + usernameABuscar);
            return;
        }

        usuarioModificarView.getTxtContraseña().setText(usuarioEncontrado.getPassword());
        usuarioModificarView.getCbxRoles().setSelectedItem(usuarioEncontrado.getRol());

        usuarioModificarView.getTxtUsuario().setEditable(false);
        usuarioModificarView.getBtnBuscar().setEnabled(false);
        usuarioModificarView.getBtnModificar().setEnabled(true);
        usuarioModificarView.getTxtContraseña().setEnabled(true);
        usuarioModificarView.getCbxRoles().setEnabled(true);
    }


    private void crearUsuario() {
        String username = usuarioCrearView.getTxtUsuario().getText().trim();
        String contraseña = usuarioCrearView.getTxtContraseña().getText().trim();
        Rol rolSeleccionado = (Rol) usuarioCrearView.getCbxRoles().getSelectedItem();

        if (username.isEmpty() || contraseña.isEmpty()) {
            usuarioCrearView.mostrarMensaje("El nombre de usuario y la contraseña no pueden estar vacíos.");
            return;
        }

        if (rolSeleccionado == null) {
            usuarioCrearView.mostrarMensaje("Debe seleccionar un rol para el nuevo usuario.");
            return;
        }

        if (usuarioDAO.buscarPorUsuario(username) != null) {
            usuarioCrearView.mostrarMensaje("El nombre de usuario '" + username + "' ya está en uso.");
            return;
        }

        Usuario nuevoUsuario = new Usuario(username, rolSeleccionado, contraseña);

        usuarioDAO.crear(nuevoUsuario);

        usuarioCrearView.mostrarMensaje("Usuario '" + username + "' creado con éxito.");
        usuarioCrearView.limpiarCampos();
    }

    private void autentificar() {
        String username = logInView.getTxtUsername().getText();
        String contraseña = new String(logInView.getPsfContraseña().getPassword());

        usuarioAutentificado = usuarioDAO.autenticar(username,contraseña);
        if(usuarioAutentificado == null){
            logInView.mostrarMensaje("Usuario o contraseña incorrecto");
        }else{
            logInView.mostrarMensaje("¡Bienvenido " + usuarioAutentificado.getUsername() + "!");
            logInView.dispose();
        }
    }


    private void registrarUsuario() {
        String username = logInView.getTxtUsername().getText().trim();
        String contraseña = new String(logInView.getPsfContraseña().getPassword());

        if (username.isEmpty() || contraseña.isEmpty()) {
            logInView.mostrarMensaje("Para registrarse, por favor ingrese un nombre de usuario y una contraseña.");
            return;
        }

        if (usuarioDAO.buscarPorUsuario(username) != null) {
            logInView.mostrarMensaje("El nombre de usuario '" + username + "' ya está en uso. Por favor, elija otro.");
            return;
        }

        Usuario nuevoUsuario = new Usuario(username, Rol.USUARIO, contraseña);

        usuarioDAO.crear(nuevoUsuario);

        logInView.mostrarMensaje("¡Usuario '" + username + "' registrado con éxito!\nAhora puede iniciar sesión.");

        logInView.getTxtUsername().setText("");
        logInView.getPsfContraseña().setText("");
    }

    public Usuario getUsuarioAutentificado(){
        return usuarioAutentificado;
    }
}