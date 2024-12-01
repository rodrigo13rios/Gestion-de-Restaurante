package usuarios;

import otros.Gestorable;

import java.util.Objects;

public class    Usuario {
    private String username;
    private String password;
    private NivelAcceso nivelAcceso;

    public Usuario(String username, String password, NivelAcceso nivelAcceso) {
        this.username = username;
        this.password = password;
        this.nivelAcceso = nivelAcceso;
    }

    //vvv Getters y Setters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public NivelAcceso getNivelAcceso() {
        return nivelAcceso;
    }
    public void setNivelAcceso(NivelAcceso nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }
    //^^^ Getters y Setters


    @Override
    public String toString() {
        return "User:"+username+" / Pass:"+password+" / Nivel de acceso-"+nivelAcceso;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(username, usuario.username);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }
}
