package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Genero;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.Respuesta;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioDAOArchivoTexto implements UsuarioDAO {
    private String ruta;

    private static final String SEPARADOR_CAMPOS = "|";
    private static final String SEPARADOR_PREGUNTAS = ";";
    private static final String SEPARADOR_RESPUESTAS = ",";

    public UsuarioDAOArchivoTexto(String ruta) {
        this.ruta = ruta;
        try {
            new FileWriter(ruta, true).close();
        } catch (IOException e) {
            System.err.println("Error al inicializar el archivo de usuarios: " + e.getMessage());
        }
    }

    @Override
    public void crear(Usuario usuario) {
        if (buscarPorUsuario(usuario.getUsername()) != null) {
            System.err.println("Intento de crear un usuario que ya existe: " + usuario.getUsername());
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, true))) {
            bw.append(usuarioToString(usuario));
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
                // Optimización: si la línea empieza con el username, la procesamos.
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
        boolean eliminado = usuarios.removeIf(usuario -> usuario.getUsername().equals(username));
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
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public List<Usuario> listarRol(Rol rol) {
        return listarTodos().stream()
                .filter(usuario -> usuario.getRol() == rol)
                .collect(Collectors.toList());
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
            String respuestasString = usuario.getRespuestasSeguridad().stream()
                    .map(r -> r.getPregunta().getId() + SEPARADOR_RESPUESTAS + r.getRespuesta())
                    .collect(Collectors.joining(SEPARADOR_PREGUNTAS));
            sb.append(respuestasString);
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
                        String respuestaTexto = prParts[1];
                        Pregunta pregunta = new Pregunta(preguntaId, "");
                        usuario.addRespuesta(pregunta, respuestaTexto);
                    }
                }
            }
            return usuario;
        } catch (IllegalArgumentException e) {
            System.err.println("Error al parsear la línea de usuario: " + linea);
            e.printStackTrace();
            return null;
        }
    }
}