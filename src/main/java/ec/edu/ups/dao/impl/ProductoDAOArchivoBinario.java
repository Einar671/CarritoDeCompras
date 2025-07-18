package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de la interfaz ProductoDAO que utiliza un archivo binario
 * de acceso aleatorio para la persistencia de datos de los productos.
 * <p>
 * Cada producto se almacena en un registro de tamaño fijo, lo que permite
 * un acceso y modificación eficientes. La eliminación de productos se gestiona
 * de forma lógica mediante un indicador (booleano) de disponibilidad.
 *
 * @author Einar Kaalhus
 * @version 1.0
 */
public class ProductoDAOArchivoBinario implements ProductoDAO {
    /**
     * La ruta completa del archivo binario donde se almacenan los datos de los productos.
     */
    private final String ruta;


    /**
     * El tamaño fijo en caracteres para el campo de nombre del producto.
     */
    private static final int NOMBRE_SIZE_CHARS = 25;
    /**
     * El tamaño fijo en caracteres para el campo de descripción del producto.
     */
    private static final int DESC_SIZE_CHARS = 50;
    /**
     * El tamaño total en bytes de un único registro de producto en el archivo.
     * Se calcula sumando el tamaño de cada campo:
     * int (4) + nombre(25*2) + descripcion(50*2) + double(8) + int(4) + boolean(1).
     */
    private static final int RECORD_SIZE = 4 + (NOMBRE_SIZE_CHARS * 2) + (DESC_SIZE_CHARS * 2) + 8 + 4 + 1;

    /**
     * Constructor para ProductoDAOArchivoBinario.
     *
     * @param ruta La ruta del archivo binario a utilizar.
     */
    public ProductoDAOArchivoBinario(String ruta) {
        this.ruta = ruta;
        try {
            // Asegura que el archivo exista. Si no, lo crea.
            new RandomAccessFile(ruta, "rw").close();
        } catch (IOException e) {
            System.err.println("Error al inicializar el archivo binario de productos: " + e.getMessage());
        }
    }

    /**
     * Agrega un nuevo producto al final del archivo binario.
     *
     * @param producto El objeto {@link Producto} a ser creado y almacenado.
     */
    @Override
    public void crear(Producto producto) {
        try (RandomAccessFile file = new RandomAccessFile(ruta, "rw")) {
            file.seek(file.length()); // Ir al final para añadir
            file.writeInt(producto.getCodigo());
            writeFixedString(file, producto.getNombre(), NOMBRE_SIZE_CHARS);
            file.writeDouble(producto.getPrecio());
            file.writeBoolean(true); // Marcar como disponible al crear
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca un producto específico en el archivo por su código.
     * Lee el archivo registro por registro hasta encontrar uno que coincida con el código
     * y esté marcado como disponible.
     *
     * @param codigo El código del producto a buscar.
     * @return El objeto {@link Producto} encontrado, o {@code null} si no existe o no está disponible.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        try (RandomAccessFile file = new RandomAccessFile(ruta, "r")) {
            long numRecords = file.length() / RECORD_SIZE;
            for (int i = 0; i < numRecords; i++) {
                file.seek(i * RECORD_SIZE);
                int codigoActual = file.readInt();
                if (codigoActual == codigo) {
                    String nombre = readFixedString(file, NOMBRE_SIZE_CHARS);
                    String descripcion = readFixedString(file, DESC_SIZE_CHARS);
                    double precio = file.readDouble();
                    int cantidad = file.readInt();
                    boolean disponible = file.readBoolean();

                    if (disponible) {
                        return new Producto(codigo, nombre, precio);
                    } else {
                        return null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Busca productos en el archivo cuyo nombre coincida exactamente (ignorando mayúsculas y minúsculas)
     * con el nombre proporcionado.
     *
     * @param nombre El nombre del producto a buscar.
     * @return Una lista de objetos {@link Producto} que coinciden con el criterio de búsqueda.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productos = new ArrayList<>();
        try (RandomAccessFile file = new RandomAccessFile(ruta, "r")) {
            long numRecords = file.length() / RECORD_SIZE;
            for (int i = 0; i < numRecords; i++) {
                file.seek(i * RECORD_SIZE);
                int codigo = file.readInt();
                String nombreActual = readFixedString(file, NOMBRE_SIZE_CHARS);

                if (nombreActual.equalsIgnoreCase(nombre.trim())) {
                    double precio = file.readDouble();
                    boolean disponible = file.readBoolean();

                    if (disponible) {
                        productos.add(new Producto(codigo, nombreActual, precio));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productos;
    }

    /**
     * Actualiza un producto existente en el archivo.
     * Busca el registro por su código y lo sobrescribe completamente con los nuevos datos.
     *
     * @param producto El objeto {@link Producto} con la información actualizada.
     */
    @Override
    public void actualizar(Producto producto) {
        try (RandomAccessFile file = new RandomAccessFile(ruta, "rw")) {
            long numRecords = file.length() / RECORD_SIZE;
            for (int i = 0; i < numRecords; i++) {
                file.seek(i * RECORD_SIZE);
                int codigoActual = file.readInt();
                if (codigoActual == producto.getCodigo()) {
                    file.seek(i * RECORD_SIZE); // Volver al inicio del registro para sobrescribir
                    file.writeInt(producto.getCodigo());
                    writeFixedString(file, producto.getNombre(), NOMBRE_SIZE_CHARS);
                    file.writeDouble(producto.getPrecio());
                    file.writeBoolean(true);
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Realiza una eliminación lógica de un producto en el archivo.
     * Busca el registro por su código y establece su bandera de disponibilidad a {@code false}.
     *
     * @param codigo El código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        try (RandomAccessFile file = new RandomAccessFile(ruta, "rw")) {
            long numRecords = file.length() / RECORD_SIZE;
            for (int i = 0; i < numRecords; i++) {
                file.seek(i * RECORD_SIZE);
                if (file.readInt() == codigo) {
                    long flagPosition = i * RECORD_SIZE + (RECORD_SIZE - 1);
                    file.seek(flagPosition);
                    file.writeBoolean(false);
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lee todos los registros del archivo y devuelve una lista de todos los productos
     * que están marcados como disponibles.
     *
     * @return Una lista de todos los productos disponibles.
     */
    @Override
    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        try (RandomAccessFile file = new RandomAccessFile(ruta, "r")) {
            long numRecords = file.length() / RECORD_SIZE;
            for (int i = 0; i < numRecords; i++) {
                file.seek(i * RECORD_SIZE);
                int codigo = file.readInt();
                String nombre = readFixedString(file, NOMBRE_SIZE_CHARS);
                double precio = file.readDouble();
                boolean disponible = file.readBoolean();

                if (disponible) {
                    productos.add(new Producto(codigo, nombre, precio));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productos;
    }

    /**
     * Escribe una cadena de texto en el archivo con una longitud fija.
     * Si la cadena es más corta que el tamaño especificado, se rellena con caracteres nulos.
     * Si es más larga, se trunca.
     *
     * @param file El archivo de acceso aleatorio donde se escribirá.
     * @param str  La cadena de texto a escribir.
     * @param size El tamaño fijo (en caracteres) que debe ocupar la cadena en el archivo.
     * @throws IOException Si ocurre un error de E/S.
     */
    private void writeFixedString(RandomAccessFile file, String str, int size) throws IOException {
        StringBuilder buffer = new StringBuilder(str != null ? str : "");
        buffer.setLength(size);
        file.writeChars(buffer.toString());
    }

    /**
     * Lee una cadena de texto de longitud fija desde la posición actual del puntero del archivo.
     * Lee el número especificado de caracteres y los convierte en una cadena,
     * deteniéndose en el primer carácter nulo encontrado.
     *
     * @param file El archivo de acceso aleatorio desde donde se leerá.
     * @param size El número de caracteres a leer.
     * @return La cadena de texto leída.
     * @throws IOException Si ocurre un error de E/S.
     */
    private String readFixedString(RandomAccessFile file, int size) throws IOException {
        char[] buffer = new char[size];
        for (int i = 0; i < size; i++) {
            buffer[i] = file.readChar();
        }
        int fin = 0;
        while (fin < size && buffer[fin] != '\0') {
            fin++;
        }
        return new String(buffer, 0, fin);
    }
}