package usuarios;

import com.google.gson.reflect.TypeToken;
import excepciones.ElementoNoEncontradoException;
import excepciones.ElementoYaExistenteException;
import otros.CTool;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/* No heredamos de GestorGenerico porque este es el único
gestor que implementa un HashSet en lugar de ArrayList */


public class GestorUsuarios {
    protected HashMap<String,Usuario> array = new HashMap<>();
    private final Scanner scanner = CTool.scanner;


    public Usuario instanciar() throws ElementoYaExistenteException {
        System.out.println("Ingrese el Nombre de Usuario:");
        String username = scanner.nextLine();

        if (array.containsKey(username)) {
            throw new ElementoYaExistenteException("El nombre de usuario ingresado ya existe");
        }

        System.out.println("Ingrese la Contraseña:");
        String password = scanner.nextLine();

        int nAcceso = CTool.promptOption("Ingrese el nivel de acceso (1, 2 o 3): ",1,3);
        NivelAcceso nivelAcceso = switch (nAcceso) {
            case 3 -> NivelAcceso.TRES;
            case 2 -> NivelAcceso.DOS;
            default -> NivelAcceso.UNO; //Por si las dudas, dejo el nivel UNO como default
        };
        return new Usuario(username,password,nivelAcceso);
    }
    public Usuario instanciarAdmin() { //Instancia un usuario con N°Acceso 3 (ADMIN) Pensado para cuando no haya ningun usuario cargado, y haya que registrarse por primera vez.
        System.out.println("Ingrese el Nombre de Usuario:");
        String username = scanner.nextLine();

        System.out.println("Ingrese la Contraseña:");
        String password = scanner.nextLine();
        return new Usuario(username,password,NivelAcceso.TRES);
    }

    public void agregar(Usuario usuario) {
        array.put(usuario.getUsername(),usuario);
        System.out.println("Agregado con éxito!");
    }
    public void editar() {
        try {
            System.out.println("Ingrse el nombre de Usuario a editar");
            String username = scanner.nextLine();
            if (!array.containsKey(username)){
                System.err.println("El Nombre de Usuario ingresado no existe");
                return;
            }
            Usuario selectedUser = array.get(username);
            array.remove(selectedUser.getUsername());
            Usuario newUser = instanciar();
            array.put(newUser.getUsername(),newUser);
            System.out.println("ACTUALIZADO-> "+newUser);//Print objeto actualizado
        } catch (ElementoYaExistenteException e) {
            System.err.println("Error al intentar editar. No se ha podido crear el usuario: "+e.getMessage());
        }
    }
    public void borrar() {
        System.out.println("Ingrse el nombre del Usuario a borrar");
        String username = scanner.nextLine();
        if (!array.containsKey(username)){
            System.err.println("El Nombre de Usuario ingresado no existe");
            return;
        }
        array.remove(username);
        System.out.println("Eliminado con exito!");
    }

    public Usuario getByUsername(String username) throws ElementoNoEncontradoException {
        if (array.containsKey(username)) {
            return array.get(username);
        } else {
            throw new ElementoNoEncontradoException("El nombre de usuario no existe");
        }
    }

    public void mostrarArray(){
        System.out.println("- - Usuarios - -");
        if (array.isEmpty()){
            System.out.println("no hay usuarios registrados");
        } else {
            for (String username : array.keySet()){
                System.out.println(username+" - n_acceso:"+array.get(username).getNivelAcceso());
            }
        }
        System.out.println();//Salto de Linea
    }
    public int getArraySize(){
        return array.size();
    }

    public void guardarArchivo() {
        CTool.guardarJSON("Usuarios.json",array);
    }
    public void cargarArchivo() throws FileNotFoundException {
        array = new HashMap<>(CTool.cargarJSON("Usuarios.json", new TypeToken<HashMap<String,Usuario>>() {}.getType()));
    }

}
