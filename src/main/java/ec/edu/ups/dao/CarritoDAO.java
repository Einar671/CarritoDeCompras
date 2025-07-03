package ec.edu.ups.dao;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

public interface CarritoDAO {
    void crear(Carrito carrito);
    void actualizar(Carrito carrito);
    void eliminar(int codigo);
    Carrito buscarPorCodigo(int codigo);
    List<Carrito> listarTodos();
    List<Carrito> buscarPorUsuario(Usuario usuario);
    Carrito buscarPorCodigoYUsuario(int codigo, Usuario usuario);
}
