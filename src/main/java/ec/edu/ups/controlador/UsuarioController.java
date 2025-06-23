package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.LogInView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioController {
    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final LogInView logInView;

    public UsuarioController(UsuarioDAO usuarioDAO, LogInView logInView) {
        this.usuarioDAO = usuarioDAO;
        this.logInView = logInView;
        this.usuario = new Usuario();
        configurarEventos();
    }

    private void configurarEventos(){
        logInView.getBtnIniciarSesion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autentificar();
            }
        });
    }

    private void autentificar() {
        String username = logInView.getTxtUsername().getText();
        String contraseña = logInView.getPsfContraseña().getText();
        usuario = usuarioDAO.autenticar(username,contraseña);
        if(usuario == null){
            logInView.mostrarMensaje("usuario o contraseña incorrecto");
        }else{
            logInView.dispose();
        }
    }
    public Usuario getUsuarioAutentificado(){
        return usuario;
    }
}
