import excepciones.ElementoNoEncontradoException;
import excepciones.ElementoYaExistenteException;
import mesas.EstadoMesa;
import mesas.Mesa;
import otros.CTool;
import otros.Gestor;
import otros.Menu;
import reservas.Reserva;
import usuarios.NivelAcceso;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Gestor gestor = new Gestor();

    public static void main(String[] args) {
        gestor.secuenciaInicio();// esto llama a la funcion de inicio de gestor
        do { // Inicio de Sesion
            System.out.println("- - Inicio de Sesion - -");
            gestor.Sesion.login();
            CTool.clearScreen();
        } while (gestor.Sesion.usuario == null);
        NivelAcceso nivelAcceso = gestor.Sesion.getNivelAcceso();
        gestor.Reservas.verificarReservasTotal();

        //LOOP - LOOP - LOOP - LOOP - LOOP - LOOP - LOOP
        boolean exit = false;
        int option = 0;
        do {
            System.out.println("- - ingresando al menu - -");
            Menu.principal(nivelAcceso);
            option = CTool.promptOption("Ingrese la opcion deseada: ",0,7);
            CTool.clearScreen();
            switch (option) {
                case 0://Salir del programa
                    exit = true;
                    break; //Exit

                //  Permisos Nivel°1
                case 1:
                    //Salon
                    menuSalon();
                    break;
                case 2:
                    //Mesas
                    menuMesas();
                    break;
                case 3:
                    //Reservas
                    menuReservas();
                    break;
            }
            if (nivelAcceso.compareTo(NivelAcceso.DOS) >= 0) {
                switch (option) {
                //  Permisos Nivel°2
                    case 4:
                        //Camareros
                        menuCamareros();
                        break;
                    case 5:
                        //Productos
                        menuProductos();
                        break;
                }
            }
            if (nivelAcceso.compareTo(NivelAcceso.TRES) >= 0) {
                switch (option) {
                //  Permisos Nivel°3
                    case 6:
                        //Usuarios
                        menuUsuarios();
                        break;
                    case 7:
                        //Ventas(Facturas y Estadisticas)
                        menuVentas();
                        break;
                }
            }
            if(!exit){System.out.print("\n");}
        } while (!exit);
        // Fin LOOP (salir totalmente del programa)
        gestor.secuenciaFinal();
        System.out.println("arrivederchi!");
    }


    private static void menuCamareros(){
        //LOOP - LOOP - LOOP - LOOP - LOOP - LOOP - LOOP
        boolean exit = false;
        int option = 0;
        do {
            System.out.println("\033[35m- - Menu Camareros - -\033[0m");
            Menu.camareros();
            option = CTool.promptOption("Ingrese la opcion deseada: ",0,4);
            CTool.clearScreen();
            switch (option) {
                case 1:
                    //Listar Camareros
                    gestor.Personal.mostrarArray();
                    break;
                case 2:
                    //Agregar un Camarero
                    try {
                        gestor.Personal.agregar(gestor.Personal.instanciar());
                    } catch (ElementoYaExistenteException e) {
                        System.err.println("\nError al intentar agregar un camarero: "+e.getMessage());
                    }
                    break;
                case 3:
                    //Editar un Camarero
                    gestor.Personal.editar();
                    break;
                case 4:
                    //Eliminar un Camarero
                    gestor.Personal.borrar();
                    break;

                case 0:
                    exit = true;
                    break; //Exit
            }

            if(!exit){System.out.print("\n");}
        } while (!exit);
    }

    private static void menuProductos(){
        //LOOP - LOOP - LOOP - LOOP - LOOP - LOOP - LOOP
        boolean exit = false;
        int option = 0;
        do {
            System.out.println("\033[35m- - Menu Productos - -\033[0m");
            Menu.productos();
            option = CTool.promptOption("Ingrese la opcion deseada: ",0,4);
            CTool.clearScreen();
            switch (option) {
                case 1:
                    //Listar Productos
                    gestor.Productos.mostrarArray();
                    break;
                case 2:
                    //Agregar un Producto
                    try {
                        gestor.Productos.agregar(gestor.Productos.instanciar());
                    } catch (ElementoYaExistenteException e) {
                        System.err.println("\nError al intentar agregar un producto: "+e.getMessage());
                    }
                    break;
                case 3:
                    //Editar un Producto
                    gestor.Productos.editar();
                    break;
                case 4:
                    //Eliminar un Producto
                    gestor.Productos.borrar();
                    break;

                case 0:
                    exit = true;
                    break; //Exit
            }
            if(!exit){System.out.print("\n");}
        } while (!exit);
    }

    private static void menuUsuarios(){
        //LOOP - LOOP - LOOP - LOOP - LOOP - LOOP - LOOP
        boolean exit = false;
        int option = 0;
        do {
            System.out.println("\033[35m- - Menu Usuarios - -\033[0m");
            Menu.usuarios();
            option = CTool.promptOption("Ingrese la opcion deseada: ",0,4);
            CTool.clearScreen();
            switch (option) {
                case 1:
                    //Listar Usuarios
                    gestor.Usuarios.mostrarArray();
                    break;
                case 2:
                    //Crear un Usuario
                    try {
                        gestor.Usuarios.agregar(gestor.Usuarios.instanciar());
                    } catch (ElementoYaExistenteException e) {
                        System.err.println("\nError al intentar crear un usuario: "+e.getMessage());
                    }
                    break;
                case 3:
                    //Editar un Usuario
                    gestor.Usuarios.editar();
                    break;
                case 4:
                    //Borrar un Usuario
                    gestor.Usuarios.borrar();
                    break;

                case 0:
                    exit = true;
                    break; //Exit
            }
            if(!exit){System.out.print("\n");}
        } while (!exit);
    }

    private static void menuMesas(){
        //LOOP - LOOP - LOOP - LOOP - LOOP - LOOP - LOOP
        boolean exit = false;
        int option = 0;
        do {
            System.out.println("\033[35m- - Menu Mesas - -\033[0m");
            Menu.mesas();
            option = CTool.promptOption("Ingrese la opcion deseada: ",0,4);
            CTool.clearScreen();
            switch (option) {
                case 1:
                    //Listar Mesas
                    gestor.Mesas.mostrarArray();
                    break;
                case 2:
                    //Crear una Mesa
                    try {
                        gestor.Mesas.agregar(gestor.Mesas.instanciar());
                    } catch (ElementoYaExistenteException e) {
                        System.err.println("\nError al intentar agregar un mesa: "+e.getMessage());
                    }
                    break;
                case 3:
                    //Editar una Mesa
                    gestor.Mesas.editar();
                    break;
                case 4:
                    //Borrar una Mesa
                    gestor.Mesas.borrar();
                    break;

                case 0:
                    exit = true;
                    break; //Exit
            }
            if(!exit){System.out.print("\n");}
        } while (!exit);
    }


    private static void menuSalon(){
        //LOOP - LOOP - LOOP - LOOP - LOOP - LOOP - LOOP
        boolean exit = false;
        int mesaId;
        do {
            gestor.Reservas.verificarReservas();
            System.out.println("- - Salon - -");
            gestor.Mesas.mostrarArray();
            if (gestor.Mesas.getArraySize() > 0) {
                mesaId = CTool.promptOption("Ingrese el numero de mesa al que quiera acceder. Para salir, ingrese '0': ");
            } else {
                mesaId = CTool.promptOption("Para salir, ingrese '0': ",0,0);
            }
            CTool.clearScreen();
            if (mesaId != 0){
                menuDeMesa(mesaId);
            } else {
                exit = true;
            }
            if(!exit){System.out.print("\n");}
        } while (!exit);
    }
    private static void menuDeMesa(int mesaId){
        //LOOP - LOOP - LOOP - LOOP - LOOP - LOOP - LOOP

        /* El objetivo es mostrar un Menu distinto, actualizado según el estado de la mesa.
        Por ejemplo, no tiene sentido mostrar la opcion de "Hacer Cuenta" si la mesa
        aún no ha sido ocupada. */

        Mesa mesa;
        boolean exit = false;
        try {
            mesa = gestor.Mesas.getById(mesaId);
        } catch (ElementoNoEncontradoException e) {
            System.err.println("\nError al intentar seleccionar la mesa: "+e.getMessage());
            return;
        }
        do { // Este será el unico Bucle

            switch (mesa.getEstadoMesa()) {
                case LIBRE, RESERVADO:
                    exit = menuDeMesaLibre(mesa);
                    break;
                case OCUPADA:
                    exit = menuDeMesaOcupada(mesa);
                    break;
                case PAGANDO:
                    exit = menuDeMesaPagando(mesa);
                    break;
                case CERRADA:
                    exit = menuDeMesaCerrada(mesa);
                    break;
            }
            if(!exit){System.out.print("\n");}
        } while (!exit);
    }
    private static boolean menuDeMesaLibre(Mesa mesa){
        boolean exit = false;
        int option = 0;

        boolean reservada = false;
        if (mesa.getEstadoMesa() == EstadoMesa.RESERVADO) {
            reservada = true;
            try {
                Reserva reserva = gestor.Reservas.getReservaByMesa(mesa);
                System.out.println("- MESA RESERVADA -");
                System.out.println("Por: "+reserva.getNombreCliente());
                System.out.println("Para "+reserva.getCantPersona()+" personas");
                System.out.println("- - -");
            } catch (ElementoNoEncontradoException e) {
                System.err.println("La mesa esta reservada, pero no se ha podido encontrar la respectiva reserva: "+e.getMessage());
            }
        }
        System.out.println("Mesa N°"+mesa.getId());
        System.out.println("Capacidad: "+mesa.getCapacidadMax()+" personas");
        System.out.println("- - - - - - - - - - - -");
        /* Print de ejemplo:
                Mesa N°33
                Capacidad: 4
        */

        Menu.deMesaLibre();
        option = CTool.promptOption("Ingrese la opcion deseada: ",0,1);
        CTool.clearScreen();
        switch (option) {
            case 1:
                //Abrir Mesa
                gestor.Mesas.abrirMesa(mesa);
                if (reservada) {
                    gestor.Reservas.eliminarReservaByMesa(mesa);
                }
                break;

            default:
                exit = true;
                break; //Exit
        }
        return exit;
    }
    private static boolean menuDeMesaOcupada(Mesa mesa){
        boolean exit = false;
        int option = 0;

        System.out.println("Mesa N°"+mesa.getId());
        System.out.println("Personas: "+mesa.getNumOcupantes()+" personas");
        System.out.println("Camarero: "+mesa.getCamarero().getNombre()+" "+mesa.getCamarero().getApellido());
        System.out.println("Pedido: "+mesa.getPedido());
        System.out.println("- - - - - - - - - - - -");
        /* Print de ejemplo:
                Mesa N°33
                Personas: 4
                Camarero: Sebastian Kloster
                Pedido: [3 CocaCoala | 2 Milanesa Napolitana | 1 Spaguetti]
        */
        Menu.deMesaOcupada();

        option = CTool.promptOption("Ingrese la opcion deseada: ",0,4);
        CTool.clearScreen();
        switch (option) {
            case 1:
                //Agregar al Pedido
                gestor.Mesas.agregarAlPedido(mesa);
                break;
            case 2:
                //Quitar del Pedido
                gestor.Mesas.quitarDelPedido(mesa);
                break;
            case 3:
                //Hacer Cuenta
                gestor.Mesas.hacerCuentaMesa(mesa);
                break;
            case 4:
                //Cerrar Mesa
                gestor.Mesas.cerrarMesa(mesa);
                break;

            default:
                exit = true;
                break; //Exit
        }
        return exit;
    }
    private static boolean menuDeMesaPagando(Mesa mesa){
        boolean exit = false;
        int option = 0;

        System.out.println("Mesa N°"+mesa.getId());
        System.out.println("Personas: "+mesa.getNumOcupantes()+" personas");
        System.out.println("Camarero: "+mesa.getCamarero().getNombre()+" "+mesa.getCamarero().getApellido());
        System.out.println("Pedido: "+mesa.getPedido());
        System.out.println("Descuento: "+mesa.getPedido().getDescuento()+"%");
        System.out.println("Total a pagar: "+mesa.pedido.hacerCuenta());
        System.out.println("- - - - - - - - - - - -");
        /* Print de ejemplo:
                Mesa N°33
                Personas: 4
                Camarero: Sebastian Kloster
                Pedido: [3 CocaCoala | 2 Milanesa Napolitana | 1 Spaguetti]
                DESCUENTO: 10%
                Total a pagar: 18.070,00
        */
        Menu.deMesaPagando();

        option = CTool.promptOption("Ingrese la opcion deseada: ",0,2);
        CTool.clearScreen();
        switch (option) {
            case 1:
                //Confirmar pago
                gestor.Mesas.confirmarPagoMesa(mesa);
                break;
            case 2:
                //Editar Pedido
                gestor.Mesas.editarPedidoMesa(mesa);
                break;

            default:
                exit = true;
                break; //Exit
        }
        return exit;
    }
    private static boolean menuDeMesaCerrada(Mesa mesa){
        boolean exit = false;
        int option = 0;

        System.out.println("Mesa N°"+mesa.getId());
        System.out.println("Personas: "+mesa.getNumOcupantes()+" personas");
        System.out.println("- - - - - - - - - - - -");
        /* Print de ejemplo:
                Mesa N°33
                Personas: 4
        */
        Menu.deMesaCerrada();

        option = CTool.promptOption("Ingrese la opcion deseada: ",0,1);
        CTool.clearScreen();
        switch (option) {
            case 1:
                //Cerrar Mesa
                gestor.Mesas.cerrarMesa(mesa);
                break;

            default:
                exit = true;
                break; //Exit
        }
        return exit;
    }


    private static void menuReservas(){
        //LOOP - LOOP - LOOP - LOOP - LOOP - LOOP - LOOP
        boolean exit = false;
        int option = 0;
        do {
            System.out.println("\033[35m- - Menu Reservas - -\033[0m");            Menu.reservas();
            option = CTool.promptOption("Ingrese la opcion deseada: ",0,4);
            CTool.clearScreen();
            switch (option) {
                case 1:
                    //Listar Reservas
                    gestor.Reservas.mostrarArray();
                    break;
                case 2:
                    //Crear una Reserva
                    try {
                        gestor.Reservas.agregar(gestor.Reservas.instanciar());
                    } catch (ElementoYaExistenteException e) {
                        System.err.println("\nError al intentar crear la reserva: "+e.getMessage());
                    }
                    break;
                case 3:
                    //Editar una Reserva
                    gestor.Reservas.editar();
                    break;
                case 4:
                    //Borrar una Reserva
                    gestor.Reservas.borrar();
                    break;

                case 0:
                    exit = true;
                    break; //Exit
            }
            if(!exit){System.out.print("\n");}
        } while (!exit);
    }


    private static void menuVentas(){
        //LOOP - LOOP - LOOP - LOOP - LOOP - LOOP - LOOP
        boolean exit = false;
        int option = 0;
        do {
            System.out.println("\033[35m- - Menu Ventas - -\033[0m");
            Menu.ventas();
            option = CTool.promptOption("Ingrese la opcion deseada: ",0,3);
            CTool.clearScreen();
            switch (option) {
                case 1:
                    //Listar todas las facturas
                    gestor.Facturas.mostrarArray();
                    break;
                case 2:
                    //Filtrar y listar facturas
                    gestor.Facturas.mostrarFacturasPorFiltros();
                    break;
                case 3:
                    //Estadísticas
                    menuEstadisticas();
                    break;

                case 0:
                    exit = true;
                    break; //Exit
            }
            if(!exit){System.out.print("\n");}
        } while (!exit);
    }

    private static void menuEstadisticas(){
        //LOOP - LOOP - LOOP - LOOP - LOOP - LOOP - LOOP
        boolean exit = false;
        int option = 0;
        do {
            System.out.println("\033[35m- - Menu Estadisticas - -\033[0m");
            Menu.estadisticas();
            option = CTool.promptOption("Ingrese la opcion deseada: ",0,4);
            CTool.clearScreen();
            switch (option) {
                case 1:
                    //Estadística Semanal
                    gestor.Facturas.estadisticaSemanal();
                    break;
                case 2:
                    //Estadística Mensual
                    gestor.Facturas.estadisticaMensual();
                    break;
                case 3:
                    //Estadística Anual
                    gestor.Facturas.estadisticaAnual();
                    break;
                case 4:
                    //Estadistica por intervalos
                    gestor.Facturas.estadisticaPorIntervalos();
                    break;

                case 0:
                    exit = true;
                    break; //Exit
            }
            if(!exit){System.out.print("\n");}
        } while (!exit);
    }


}//arrivederchi;