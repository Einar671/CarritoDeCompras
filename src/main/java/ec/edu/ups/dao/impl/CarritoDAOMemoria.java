package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz CarritoDAO que utiliza una lista en memoria
 * para la persistencia de los carritos de compra.
 * <p>
 * Los datos almacenados en esta implementación se pierden cuando la aplicación se cierra.
 * Es ideal para pruebas o para escenarios donde la persistencia a largo plazo no es requerida.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class CarritoDAOMemoria implements CarritoDAO {

    /**
     * La lista en memoria que almacena todos los objetos Carrito.
     */
    private final List<Carrito> listaCarritos;
    /**
     * Contador para asignar un código único y autoincremental a cada nuevo carrito.
     */
    private int proximoCodigo = 1;

    /**
     * Constructor para CarritoDAOMemoria.
     * Inicializa la lista vacía que contendrá los carritos.
     */
    public CarritoDAOMemoria() {
        this.listaCarritos = new ArrayList<>();
    }

    /**
     * Crea un nuevo carrito de compras, asignándole un código único y autoincremental
     * antes de añadirlo a la lista en memoria.
     *
     * @param carrito El objeto {@link Carrito} a ser creado. Se le asignará un nuevo código.
     */
    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(proximoCodigo++);
        listaCarritos.add(carrito);
    }

    /**
     * Actualiza un carrito existente en la lista.
     * Busca el carrito por su código y lo reemplaza con la nueva instancia proporcionada.
     *
     * @param carrito El objeto {@link Carrito} con los datos actualizados.
     */
    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < listaCarritos.size(); i++) {
            if (listaCarritos.get(i).getCodigo() == carrito.getCodigo()) {
                listaCarritos.set(i, carrito);
                break;
            }
        }
    }

    /**
     * Elimina un carrito de la lista basándose en su código.
     *
     * @param codigo El código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        listaCarritos.removeIf(carrito -> carrito.getCodigo() == codigo);
    }


    /**
     * Busca un carrito específico en la lista por su código.
     *
     * @param codigo El código del carrito a buscar.
     * @return El objeto {@link Carrito} encontrado, o {@code null} si no existe un carrito con ese código.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : listaCarritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    /**
     * Devuelve una copia de la lista de todos los carritos almacenados en memoria.
     *
     * @return Una nueva {@link List} conteniendo todos los carritos.
     */
    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(listaCarritos);
    }

    /**
     * Filtra la lista de todos los carritos para devolver solo aquellos que pertenecen a un usuario específico.
     *
     * @param usuario El objeto {@link Usuario} cuyos carritos se desean buscar.
     * @return Una lista de todos los carritos que pertenecen al usuario especificado.
     */
    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        return listaCarritos.stream()
                .filter(carrito -> carrito.getUsuario().equals(usuario))
                .collect(Collectors.toList());
    }


    /**
     * Busca un carrito por su código, pero solo lo devuelve si también pertenece al usuario especificado.
     *
     * @param codigo  El código del carrito a buscar.
     * @param usuario El usuario que debe ser el propietario del carrito.
     * @return El objeto {@link Carrito} si se encuentra y pertenece al usuario, de lo contrario {@code null}.
     */
    @Override
    public Carrito buscarPorCodigoYUsuario(int codigo, Usuario usuario) {
        Carrito carritoEncontrado = this.buscarPorCodigo(codigo);

        if (carritoEncontrado != null && carritoEncontrado.getUsuario().equals(usuario)) {
            return carritoEncontrado;
        }

        return null;
    }
}