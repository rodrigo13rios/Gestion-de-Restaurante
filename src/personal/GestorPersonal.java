package personal;

import com.google.gson.reflect.TypeToken;
import excepciones.*;
import otros.CTool;
import otros.GestorGenerico;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

public class GestorPersonal extends GestorGenerico<Personal> {

    @Override
    public Personal instanciar() throws ElementoYaExistenteException {
        System.out.println("Ingrese el nombre:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese el apellido:");
        String apellido = scanner.nextLine();
        int dni = CTool.promptOption("Ingrese el número de DNI: ",0,99999999);

        if (array.contains(new Personal(null, null, dni))) {
            throw new ElementoYaExistenteException("El DNI ya está en uso");
        }

        //Editar fecha de Alta
        int fechaAltaEdit = CTool.promptOption("Si desea establecer fechas de Alta y Baja, ingrese 1: ");
        if (fechaAltaEdit==1){
            //Si SI se desea editar las fechas...
            return instanciarConFechas(nombre,apellido,dni);
        } else {
            //Si NO se desea editar las fechas...
            return new Personal(nombre,apellido,dni);
        }
    }
    public Personal instanciarConFechas(String nombre, String apellido, int dni){
        while (true) {
            try{
                System.out.println("Ingrese la fecha de Alta (Ej: 2024-5-29)");

                LocalDate fechaAlta = CTool.promptFecha();

                //Editar fecha de Baja (Opcional)
                int fechaBajaEdit = CTool.promptOption("Si desea incluir una fecha de Baja, ingrese 1: ");
                LocalDate fechaBaja = null;
                if (fechaBajaEdit==1){
                    System.out.println("Ingrese la fecha de Baja (Ej: 2024-2-28)");
                    fechaBaja = CTool.promptFecha();
                    if (fechaAlta.isBefore(fechaBaja)){
                        throw new FechasAltaBajaException("Las fechas de alta y baja no concuerdan");
                    }
                }
                return new Personal(nombre,apellido,dni,fechaAlta,fechaBaja);

            } catch (FechasAltaBajaException e){
                System.err.println("Error al cargar las fechas correspondientes"+e.getMessage());
                System.out.println("Intente Nuevamente!");
            }
        }
    }


    @Override
    public int buscarPos() throws ElementoNoEncontradoException {
        super.mostrarArray();
        //Print Ej: Sebastian Kloster DNI:19096400

        int dni = CTool.promptOption("Ingrese el número de DNI: ",0,99999999);

        int pos = array.indexOf(new Personal(null,null,dni));//Busca en el array un elemento con mismo DNI y devuelve su índice.
        if (pos == -1){
            throw new ElementoNoEncontradoException("Numero de DNI inexistente");
        }
        System.out.println("*-> "+getByPos(pos));//Print objeto seleccionado
        return pos;
    }


    @Override
    public void guardarArchivo() {
        CTool.guardarJSON("Personal.json",array);
    }
    @Override
    public void cargarArchivo() throws FileNotFoundException {
        array = new ArrayList<>(CTool.cargarJSON("Personal.json", new TypeToken<ArrayList<Personal>>() {}.getType()));
    }
}
