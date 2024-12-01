package otros;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;
import otros.adaptadoresGSON.LocalDateTimeAdapter;
import otros.adaptadoresGSON.LocalDateAdapter;
import otros.adaptadoresGSON.PedidoAdapter;
import otros.adaptadoresGSON.ProductoAdapter;
import productos.Pedido;
import productos.Producto;

public abstract class CTool { //Custom Tools
    /* El proposito de esta clase, es ofrecer a distintas clases inconexas
    'herramientas' (metodos) de forma independiente, sin necesidad de heredar
    o implementar nada, por medio de metodos Estaticos. */

    private static final Gson gson = new GsonBuilder()// Gson para la gestion de archivos.
            //Adaptar tipos de datos que por default Gson no comprende (es medio tontito, pero le queremos igual).
            .registerTypeAdapter(Producto.class, new ProductoAdapter()) // Adaptador para Producto
            .registerTypeAdapter(Pedido.class, new PedidoAdapter()) // Adaptador para Pedido
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Adaptador para LocalDate
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Adaptador para LocalDateTime
            .create();

    public static final Scanner scanner = new Scanner(System.in); //Scanner para uso publico de las clases.

    public static LocalDate promptFecha(){// Permite al usuario ingresar una fecha por teclado.
        while (true) {
            try {
                System.out.print("-año:");
                int anio = scanner.nextInt();

                System.out.print("-mes:");
                int mes = scanner.nextInt();

                System.out.print("-dia:");
                int dia = scanner.nextInt();
                scanner.nextLine();

                try {
                    return LocalDate.of(anio,mes,dia);
                } catch (DateTimeException e) {
                    System.err.println("No se ha podido establecer la fecha, error: "+e.getMessage());
                    System.out.println("Vuelva a intentar!");
                }
            } catch (InputMismatchException e) {
                System.err.println("El tipo de valor ingresado no concuerda con lo requerido: "+e.getMessage());
                System.out.println("Vuelva a intentar!");
            }
        }
    }

    public static int promptOption(String mensaje) {
        boolean errFlag;
        int option = 0;
        do {
            errFlag = false;
            try {
                System.out.print(mensaje);
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                errFlag = true;
                System.err.println("El tipo de valor ingresado no concuerda con lo requerido: "+e.getMessage());
                System.out.println("Intente nuevamente!");
            } finally {
                scanner.nextLine();//ComeSL
            }
        } while (errFlag);
        return option;
    }
    public static int promptOption(String mensaje, int limiteInferior, int limiteSuperior) {
        boolean errFlag;
        int option = 0;
        do {
            errFlag = false;
            try {
                System.out.print(mensaje);
                option = scanner.nextInt();
                if (option < limiteInferior || option > limiteSuperior) {
                    errFlag = true;
                    System.out.println("El valor ingresado está fuera del rango!");
                    System.out.println("Intente nuevamente!");
                }
            } catch (InputMismatchException e) {
                errFlag = true;
                System.err.println("\nEl tipo de valor ingresado no concuerda con lo requerido: "+e.getMessage());
                System.out.println("Intente nuevamente!");
            } finally {
                scanner.nextLine();//ComeSL
            }
        } while (errFlag);
        return option;
    }


    public static void clearScreen(){ //Genera 'margen' visual en la consola.
        System.out.println("\033[38;5;24m══════════════════════════════════════════\033[0m");
    }



    // - ARCHIVOS - vvv - ARCHIVOS - vvv - ARCHIVOS - vvv - ARCHIVOS -
    //GUARDAR - GUARDAR - GUARDAR
    public static void guardarJSON(String filePath, Object dato) {
        try (FileWriter file = new FileWriter(filePath)) {
            String json = gson.toJson(dato); // Convierto a JSON
            file.write(json); // Guardo el JSON
            file.flush();
            System.out.println(filePath+" Guardado con éxito!");

        } catch (IOException e) {
            System.err.println("ERROR al guardar el archivo: "+filePath);
            throw new RuntimeException(e);
        }
    }
    //CARGAR - CARGAR - CARGAR
    public static <T> T cargarJSON(String filePath, Type tipo) throws FileNotFoundException {
        try (FileReader file = new FileReader(filePath)) {
            return gson.fromJson(file, tipo); // Carga el JSON usando el tipo especificado

        } catch (FileNotFoundException e) {
            System.out.println("ERROR al cargar el archivo: "+filePath);
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            System.err.println("ERROR al cargar el archivo: "+filePath);
            throw new RuntimeException(e);
        }
    }

}
