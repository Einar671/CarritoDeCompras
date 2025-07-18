package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Genero;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.Respuesta;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.CedulaValidatorException;
import ec.edu.ups.util.ContraseñaValidatorException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOArchivoTexto implements UsuarioDAO {
    private final String ruta;
    private static final String SEPARADOR_CAMPOS = "|";
    private static final String SEPARADOR_PREGUNTAS = ";";
    private static final String SEPARADOR_RESPUESTAS = ",";

    public UsuarioDAOArchivoTexto(String ruta) {
        this.ruta = ruta;
        try {
            File archivo = new File(ruta);
            // Si el archivo no existe o está vacío, se crean los usuarios por defecto.
            if (!archivo.exists() || archivo.length() == 0) {
                archivo.createNewFile();
                // Se usan datos que cumplen las reglas de validación de Cédula y Contraseña.
                crear(new Usuario("0302581863", Rol.ADMINISTRADOR, "Admin@123", "Administrador Principal", 30, Genero.OTRO, "0999999999", "admin@example.com"));
                crear(new Usuario("0150363232", Rol.USUARIO, "User_456", "Usuario de Prueba", 25, Genero.MASCULINO, "0888888888", "user@example.com"));
            }
        } catch (IOException | CedulaValidatorException | ContraseñaValidatorException e) {
            System.err.println("Error al inicializar o crear usuarios por defecto en el archivo: " + e.getMessage());
        }
    }

    @Override
    public void crear(Usuario usuario) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, true))) {
            bw.write(usuarioToString(usuario));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al crear el usuario en el archivo: " + e.getMessage());
        }
    }

    @Override
    public Usuario buscarPorUsuario(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith(username + SEPARADOR_CAMPOS)) {
                    return stringToUsuario(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Usuario autenticar(String username, String password) {
        Usuario usuario = buscarPorUsuario(username);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }

    @Override
    public void actualizar(Usuario usuarioActualizado) {
        List<Usuario> usuarios = listarTodos();
        boolean modificado = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuarioActualizado.getUsername())) {
                usuarios.set(i, usuarioActualizado);
                modificado = true;
                break;
            }
        }
        if (modificado) {
            sobrescribirArchivo(usuarios);
        }
    }

    @Override
    public void eliminar(String username) {
        List<Usuario> usuarios = listarTodos();
        // Reemplazo de removeIf con un iterador para ser más explícito.
        Iterator<Usuario> iterador = usuarios.iterator();
        boolean eliminado = false;
        while (iterador.hasNext()) {
            Usuario usuario = iterador.next();
            if (usuario.getUsername().equals(username)) {
                iterador.remove();
                eliminado = true;
                break; // Se asume que los usernames son únicos.
            }
        }

        if (eliminado) {
            sobrescribirArchivo(usuarios);
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Usuario usuario = stringToUsuario(linea);
                if (usuario != null) {
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            // No se imprime error si el archivo no se encuentra, es un caso normal.
            if (!(e instanceof FileNotFoundException)) {
                e.printStackTrace();
            }
        }
        return usuarios;
    }

    @Override
    public List<Usuario> listarRol(Rol rol) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : listarTodos()) {
            if (usuario.getRol() == rol) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }

    @Override
    public List<Usuario> listarAdministradores() {
        return listarRol(Rol.ADMINISTRADOR);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return listarRol(Rol.USUARIO);
    }


    private void sobrescribirArchivo(List<Usuario> usuarios) {
        // Se usa 'false' para sobrescribir completamente el archivo.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, false))) {
            for (Usuario u : usuarios) {
                bw.write(usuarioToString(u));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String usuarioToString(Usuario usuario) {
        StringBuilder sb = new StringBuilder();
        sb.append(usuario.getUsername()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getRol()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getPassword()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getNombreCompleto()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getEdad()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getGenero()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getTelefono()).append(SEPARADOR_CAMPOS);
        sb.append(usuario.getEmail());

        if (usuario.getRespuestasSeguridad() != null && !usuario.getRespuestasSeguridad().isEmpty()) {
            sb.append(SEPARADOR_CAMPOS);

            List<String> respuestasFormateadas = new ArrayList<>();
            for (Respuesta r : usuario.getRespuestasSeguridad()) {
                // Se asume que la clase Respuesta tiene el método getTextoRespuesta()
                // para mantener consistencia con el resto del proyecto.
                respuestasFormateadas.add(r.getPregunta().getId() + SEPARADOR_RESPUESTAS + r.getRespuesta());
            }
            sb.append(String.join(SEPARADOR_PREGUNTAS, respuestasFormateadas));
        }
        return sb.toString();
    }


    private Usuario stringToUsuario(String linea) {
        String[] parts = linea.split("\\" + SEPARADOR_CAMPOS, -1);
        if (parts.length < 8) return null;

        try {
            String username = parts[0];
            Rol rol = Rol.valueOf(parts[1]);
            String password = parts[2];
            String nombreCompleto = parts[3];
            int edad = Integer.parseInt(parts[4]);
            Genero genero = Genero.valueOf(parts[5]);
            String telefono = parts[6];
            String email = parts[7];

            Usuario usuario = new Usuario(username, rol, password, nombreCompleto, edad, genero, telefono, email);

            if (parts.length > 8 && !parts[8].isEmpty()) {
                String[] preguntasRespuestas = parts[8].split(SEPARADOR_PREGUNTAS);
                for (String pr : preguntasRespuestas) {
                    String[] prParts = pr.split(SEPARADOR_RESPUESTAS);
                    if (prParts.length == 2) {
                        int preguntaId = Integer.parseInt(prParts[0]);
                        String textoRespuesta = prParts[1];
                        // El texto de la pregunta no se guarda, se recupera desde los archivos de mensajes.
                        Pregunta pregunta = new Pregunta(preguntaId, "");
                        usuario.addRespuesta(pregunta, textoRespuesta);
                    }
                }
            }
            return usuario;
        } catch (IllegalArgumentException | CedulaValidatorException | ContraseñaValidatorException e) {
            System.err.println("Error al parsear la línea de usuario o datos inválidos: " + linea);
            e.printStackTrace();
            return null;
        }
    }
}