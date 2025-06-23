package ec.edu.ups.modelo;

public class Usuario {
    private String username;
    private Rol rol;
    private String password;

    public Usuario(String username, Rol rol, String password) {
        this.username = username;
        this.rol = rol;
        this.password = password;
    }

    public Usuario() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", rol=" + rol +
                ", password='" + password + '\'' +
                '}';
    }
}
