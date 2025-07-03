package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarritoDAOMemoria implements CarritoDAO {

    private final List<Carrito> listaCarritos;
    private int proximoCodigo = 1;
    public CarritoDAOMemoria() {
        this.listaCarritos = new ArrayList<>();
    }

    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(proximoCodigo++);
        listaCarritos.add(carrito);
    }

    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < listaCarritos.size(); i++) {
            if (listaCarritos.get(i).getCodigo() == carrito.getCodigo()) {
                listaCarritos.set(i, carrito);
                break;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        listaCarritos.removeIf(carrito -> carrito.getCodigo() == codigo);
    }


    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : listaCarritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(listaCarritos);
    }

    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        return listaCarritos.stream()
                .filter(carrito -> carrito.getUsuario().equals(usuario))
                .collect(Collectors.toList());
    }


    @Override
    public Carrito buscarPorCodigoYUsuario(int codigo, Usuario usuario) {
        Carrito carritoEncontrado = this.buscarPorCodigo(codigo);

        if (carritoEncontrado != null && carritoEncontrado.getUsuario().equals(usuario)) {
            return carritoEncontrado;
        }

        return null;
    }
}