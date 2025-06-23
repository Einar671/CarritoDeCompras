package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.LogInView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioController {
    private Usuario usuarioAutentificado;
    private final UsuarioDAO usuarioDAO;
    private final LogInView logInView;

    public UsuarioController(UsuarioDAO usuarioDAO, LogInView logInView) {
        this.usuarioDAO = usuarioDAO;
        this.logInView = logInView;

        configurarEventos();
    }

    private void configurarEventos(){
        logInView.getBtnIniciarSesion().addActionListener(e -> autentificar());

        logInView.getBtnRegistrarse().addActionListener(e -> registrarUsuario());
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