package usuarios;

import excepciones.ElementoNoEncontradoException;
import otros.CTool;

import java.util.Scanner;

public class GestorSesion { //Se encarga de abrir Sesion, y gestionar la Sesion en curso.
    private final Scanner scanner = CTool.scanner;
    private final GestorUsuarios gestorUsuarios;

    //USUARIO de la Sesion en curso. Cualquier referencia a propiedades de la sesion en curso, debería hacer referencia a este Usuario.
    public Usuario usuario;

    public GestorSesion(GestorUsuarios gestorUsuarios) {
        this.gestorUsuarios = gestorUsuarios;
    }

    public void login(){
        System.out.println("Ingrese el Nombre de Usuario:");
        String username = scanner.nextLine();
        Usuario usuarioAux;
        try {
            usuarioAux = gestorUsuarios.getByUsername(username);
        } catch (ElementoNoEncontradoException e) {
            System.err.println("Error al iniciar sesion: "+e.getMessage());
            return;
        }
        System.out.println("Ingrese la contraseña:");
        String password = scanner.nextLine();
        if (!password.equals(usuarioAux.getPassword())) {
            System.err.println("Error al iniciar sesion: Contraseña incorrecta!");
        } else {
            usuario = usuarioAux;
            System.out.println("Bienvenido "+usuario.getUsername());
        }
    }

    public NivelAcceso getNivelAcceso(){//Mismo metodo que en la clase Usuario, pero en caso de que no exista (por cualquier razon) devuelvo el nivel más bajo de acceso.
        if (usuario == null) {
            return NivelAcceso.UNO;
        } else {
            return usuario.getNivelAcceso();
        }
    }
}
