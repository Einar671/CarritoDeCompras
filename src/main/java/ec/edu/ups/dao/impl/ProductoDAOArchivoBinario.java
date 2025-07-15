package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOArchivoBinario implements ProductoDAO {
    private final String ruta;


    private static final int NOMBRE_SIZE_CHARS = 25;
    private static final int DESC_SIZE_CHARS = 50;
    private static final int RECORD_SIZE = 4 + (NOMBRE_SIZE_CHARS * 2) + (DESC_SIZE_CHARS * 2) + 8 + 4 + 1;

    public ProductoDAOArchivoBinario(String ruta) {
        this.ruta = ruta;
        try {
            // Asegura que el archivo exista. Si no, lo crea.
            new RandomAccessFile(ruta, "rw").close();
        } catch (IOException e) {
            System.err.println("Error al inicializar el archivo binario de productos: " + e.getMessage());
        }
    }

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

    private void writeFixedString(RandomAccessFile file, String str, int size) throws IOException {
        StringBuilder buffer = new StringBuilder(str != null ? str : "");
        buffer.setLength(size);
        file.writeChars(buffer.toString());
    }

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
