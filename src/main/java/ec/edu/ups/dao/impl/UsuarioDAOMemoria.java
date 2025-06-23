package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;

    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<Usuario>();
        crear(new Usuario("admin",Rol.ADMINISTRADOR,"12345"));
        crear(new Usuario("usuario",Rol.USUARIO,"12345"));
    }

    @Override
    public Usuario autenticar(String username, String password) {
        for(Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getPassword().equals(password)) {
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
    public Usuario bucarPorUsuario(String username) {
        for(Usuario usuario : usuarios) {
            if(usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while(iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if(usuario.getUsername().equals(username)) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        for(int i = 0; i < usuarios.size(); i++) {
            if(usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    @Override
    public List<Usuario> listarAdministradores() {
        return List.of();
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    @Override
    public List<Usuario> listarRol(Rol rol) {
        List<Usuario> usuariosRol = new ArrayList<>();
        for(Usuario usuario : usuarios) {
            if(usuario.getRol() == rol) {
                usuariosRol.add(usuario);
            }
        }
        return List.of();
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarios;
    }
}
