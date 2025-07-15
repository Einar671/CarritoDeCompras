package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Genero;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.CedulaValidatorException;
import ec.edu.ups.util.ContraseñaValidatorException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private final List<Usuario> usuarios;

    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<>();
        try {

            crear(new Usuario("0105994392", Rol.ADMINISTRADOR, "Admin@123", "Administrador Principal", 30, Genero.OTRO, "0999999999", "admin@example.com"));
            crear(new Usuario("0106033398", Rol.USUARIO, "User_456", "Usuario de Prueba", 25, Genero.MASCULINO, "0888888888", "user@example.com"));
        } catch (CedulaValidatorException | ContraseñaValidatorException e) {
            System.err.println("Error crítico al crear usuarios por defecto: " + e.getMessage());
        }
    }

    @Override
    public Usuario autenticar(String username, String password) {
        for (Usuario usuario : usuarios) {
            if (username.equals(usuario.getUsername()) && password.equals(usuario.getPassword())) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario buscarPorUsuario(String username) {
        for (Usuario usuario : usuarios) {
            // Comprobación segura para evitar el error original
            if (usuario.getUsername() != null && usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUsername() != null && usuario.getUsername().equals(username)) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public void actualizar(Usuario usuarioActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuarioExistente = usuarios.get(i);
            if (usuarioExistente.getUsername() != null && usuarioExistente.getUsername().equals(usuarioActualizado.getUsername())) {
                usuarios.set(i, usuarioActualizado);
                break;
            }
        }
    }

    @Override
    public List<Usuario> listarAdministradores() {
        List<Usuario> admins = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol() == Rol.ADMINISTRADOR) {
                admins.add(usuario);
            }
        }
        return admins;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        List<Usuario> users = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol() == Rol.USUARIO) {
                users.add(usuario);
            }
        }
        return users;
    }

    @Override
    public List<Usuario> listarRol(Rol rol) {
        List<Usuario> usuariosRol = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol() == rol) {
                usuariosRol.add(usuario);
            }
        }
        return usuariosRol;
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }
}