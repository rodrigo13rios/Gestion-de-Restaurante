package otros;

import usuarios.NivelAcceso;

public abstract class Menu { //Clase donde almacenar los print-Menus
    public static void principal(NivelAcceso nivelAcceso){
        switch (nivelAcceso) {
            case NivelAcceso.UNO:
                System.out.println(
                        // Menu N°Acceso UNO
                        """
                        1-Salon
                
                        2-Mesas
                        3-Reservas
                        
                        0-Salir
                        """);
                break;
            case NivelAcceso.DOS:
                System.out.println(
                        // Menu N°Acceso DOS
                        """
                        1-Salon
                
                        2-Mesas
                        3-Reservas
        
                        4-Camareros
                        5-Productos
                        
                        0-Salir
                        """);
                break;
            case NivelAcceso.TRES:
                System.out.println(
                        // Menu N°Acceso TRES
                        """
                        1-Salon
                        
                        2-Mesas
                        3-Reservas
        
                        4-Camareros
                        5-Productos
        
                        6-Usuarios
                        7-Ventas y Estadistica
        
                        0-Salir
                        """
                );
                break;
        }
    }

    public static void usuarios(){
        System.out.println(
                """
                1-Listar Usuarios
                2-Crear un Usuario
                3-Editar un Usuario
                4-Borrar un Usuario

                0-Volver
                """
        );
    }
    public static void camareros(){
        System.out.println(
                """
                1-Listar Camareros
                2-Agregar un Camarero
                3-Editar un Camarero
                4-Eliminar un Camarero

                0-Volver
                """
        );
    }

    public static void productos(){
        System.out.println(
                """
                1-Listar Productos
                2-Agregar un Producto
                3-Editar un Producto
                4-Eliminar un Producto

                0-Volver
                """
        );
    }

    public static void mesas(){
        System.out.println(
                """
                1-Listar Mesas
                2-Agregar una Mesa
                3-Editar una Mesa
                4-Eliminar una Mesa

                0-Volver
                """
        );
    }


    // Salon - Salon - Salon - Salon
    public static void deMesaLibre() {
//                Mesa N°33
//                Capacidad: 4
        System.out.println(
                """     
                1-Abrir Mesa

                0-Volver
                """
        );
    }
    public static void deMesaOcupada() {
//                Mesa N°33
//                Ocupantes: 3
//                Pedido: [3 CocaCoala | 2 Milanesa Napolitana | 1 Spaguetti]
        System.out.println(
                """
                1-Agregar al Pedido
                2-Quitar del Pedido
                
                3-Hacer Cuenta
                4-Cerrar Mesa

                0-Volver
                """
        );
    }
    public static void deMesaPagando() {
//                Mesa N°33
//                Ocupantes: 3
//                Pedido: [3 CocaCoala | 2 Milanesa Napolitana | 1 Spaguetti]
//                Total a Pagar: 23.200
        System.out.println(
                """
                1-Confirmar Pago
                2-Editar Pedido

                0-Volver
                """
        );
    }
    public static void deMesaCerrada() {
//                Mesa N°33
//                Ocupantes: 3
        System.out.println(
                """
                1-Cerrar Mesa

                0-Volver
                """
        );
    }

    public static void reservas()
    {
        System.out.println(
           """
          1-Listar Reservas
          2-Agregar una Reserva
          3-Editar una Reserva
          4-Eliminar una Reserva

          0-Volver
          """
        );
    }

    public static void ventas()
    {
        System.out.println(
                """
               1-Listar todas las facturas
               2-Filtrar y listar facturas
               
               3-Estadísticas
     
               0-Volver
               """
        );
    }
    public static void estadisticas()
    {
        System.out.println(
                """
               1-Estadística Semanal
               2-Estadística Mensual
               3-Estadística Anual
               
               4-Estadistica por intervalos de fechas
     
               0-Volver
               """
        );
    }


}
