package otros;

import facturas.GestorFacturas;
import mesas.GestorMesas;
import personal.GestorPersonal;
import productos.GestorProductos;
import reservas.GestorReservas;
import usuarios.GestorSesion;
import usuarios.GestorUsuarios;
import usuarios.NivelAcceso;
import usuarios.Usuario;

import java.io.FileNotFoundException;

public class Gestor { // Gestor General/Central/Principal
    public GestorProductos Productos = new GestorProductos();
    public GestorPersonal Personal = new GestorPersonal();
    public GestorUsuarios Usuarios = new GestorUsuarios();
    public GestorSesion Sesion = new GestorSesion(Usuarios); //Sin datos que guardar, solo temporales. //'Inyeccion de Dependencias'
    public GestorFacturas Facturas = new GestorFacturas();
    public GestorMesas Mesas = new GestorMesas(Productos,Personal,Facturas); //'Inyeccion de Dependencias'
    public GestorReservas Reservas = new GestorReservas(Mesas);//'Inyeccion de Dependencias'



    // - ARCHIVOS - vvv - ARCHIVOS - vvv - ARCHIVOS - vvv - ARCHIVOS -
    public void secuenciaInicio(){
        System.out.println("... iniciando secuencia inicial ...");
        try { // - - - - - - - - - - - - Productos
            Productos.cargarArchivo();//intento cargar el archivo
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe... Creando uno nuevo!");
            Productos.guardarArchivo();//Guardo el archivo
        } try { // - - - - - - - - - - - - Personal
            Personal.cargarArchivo();
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe... Creando uno nuevo!");
            Personal.guardarArchivo();
        } try { // - - - - - - - - - - - - Usuarios
            Usuarios.cargarArchivo();
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe... Creando uno nuevo!");
            Usuarios.guardarArchivo();
        }
        if (Usuarios.getArraySize() <= 0) { // Si al cargar los datos no hay usuarios accesibles, hardcodeo el usuario admin.
            System.out.println("No hay usuarios guardados. Restableciendo usuario default (user:admin|pass:admin)");
            Usuarios.agregar(new Usuario("admin","admin", NivelAcceso.TRES));
        }
        try { // - - - - - - - - - - - - Facturas
            Facturas.cargarArchivo();
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe... Creando uno nuevo!");
            Facturas.guardarArchivo();
        } try { // - - - - - - - - - - - - Mesas
            Mesas.cargarArchivo();
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe... Creando uno nuevo!");
            Mesas.guardarArchivo();
        } try { // - - - - - - - - - - - - Reservas
            Reservas.cargarArchivo();
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe... Creando uno nuevo!");
            Reservas.guardarArchivo();
        }
        System.out.println("... fin secuencia inicial ...");
        System.out.println("\n");
    }
    public void secuenciaFinal(){
        System.out.println("... iniciando secuencia final ...");
        Productos.guardarArchivo();
        Personal.guardarArchivo();
        Usuarios.guardarArchivo();
        Facturas.guardarArchivo();
        Mesas.guardarArchivo();
        Reservas.guardarArchivo();
        System.out.println("... fin secuencia final ...");
    }


}//arrivederchi;