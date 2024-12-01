package facturas;

import com.google.gson.reflect.TypeToken;
import excepciones.ElementoNoEncontradoException;
import excepciones.ElementoYaExistenteException;
import excepciones.NumeroInvalidoException;
import mesas.Mesa;
import otros.CTool;
import otros.GestorGenerico;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

public class GestorFacturas extends GestorGenerico<Factura> {


    public Factura instanciar(Mesa mesa, MetodoDePago metodoDePago) {
        return new Factura(mesa.getPedido(),metodoDePago);
    }

    @Override
    public Factura instanciar() throws ElementoYaExistenteException {
        return null;//Funcion Desactivada
    }
    @Override
    public int buscarPos() throws ElementoNoEncontradoException {
        super.mostrarArray();
        int id = CTool.promptOption("Ingrese el número de factura: ");
        Factura factura = getById(id);// obtengo la reserva
        int pos = array.indexOf(factura);// obtengo la posicion de la reserva

        System.out.println("*-> " + getByPos(pos));//Print objeto seleccionado
        return pos;// retorno la posicion
    }

    public ArrayList<Factura> getFacturasPorFiltros(MetodoDePago metodoDePago, LocalDate fechaMasVieja, LocalDate fechaMasNueva) {
        ArrayList<Factura> lista = new ArrayList<>();
        for (Factura factura : array) {
            if (metodoDePago == null || factura.getMetodoDePago() == metodoDePago) {
                if (fechaMasVieja == null || factura.getFechaEmicion().isAfter(fechaMasVieja.atStartOfDay())) {
                    if (fechaMasNueva == null || factura.getFechaEmicion().isBefore(fechaMasNueva.atStartOfDay())) {
                        lista.add(factura);
                    }
                }
            }
        }
        return lista;
    }


    public void mostrarFacturasPorFiltros() {
        MetodoDePago metodoDePago = null;
        LocalDate fechaMasVieja = null;
        LocalDate fechaMasNueva = null;

        int flagMetodoPago = CTool.promptOption("Si desea filtrar por un metodo de pago especifico, ingrese 1: ");
        if (flagMetodoPago == 1) {
            System.out.println("- - metodos de pago - -");
            System.out.println("""
                    1-Efectivo
                    2-Debito
                    3-Credito
                    """);
            int opMetodoPago = CTool.promptOption("Ingrese la opcion deseada: ",1,3);
            metodoDePago = switch (opMetodoPago) {
                case 1 -> MetodoDePago.EFECTIVO;
                case 2 -> MetodoDePago.DEBITO;
                case 3 -> MetodoDePago.CREDITO;
                default -> metodoDePago;
            };
        }

        int flagFechaVieja = CTool.promptOption("Si desea filtrar facturas Posteriores a una fecha, ingrese 1: ");
        if (flagFechaVieja == 1) {
            System.out.println("Ingrese la fecha limite:");
            fechaMasVieja = CTool.promptFecha();
        }

        int flagFechaNueva = CTool.promptOption("Si desea filtrar facturas Anteriores a una fecha, ingrese 1: ");
        if (flagFechaNueva == 1) {
            System.out.println("Ingrese la fecha limite:");
            fechaMasNueva = CTool.promptFecha();
        }

        ArrayList<Factura> lista = getFacturasPorFiltros(metodoDePago,fechaMasVieja,fechaMasNueva);
        if (lista.isEmpty()) {
            System.out.println();//SL
            System.out.println("No hay facturas...");
            System.out.println();//SL
        } else {
            System.out.println(lista);
            System.out.println();//Salto de linea
        }
    }

    public void estadisticaSemanal(){
        ArrayList<Factura> lista = getFacturasPorFiltros(null,LocalDate.now().minusWeeks(1),null);
        if (lista.isEmpty()){
            System.out.println();//SL
            System.out.println("No hay facturas...");
            System.out.println();//SL
        } else {
            double total = 0;
            int cant = 0;
            for (Factura factura : lista) {
                total += factura.getTotal();
                cant++;
            }
            System.out.println("- - ESTADISTICAS - -");
            System.out.println("ingresos: "+total);
            System.out.println("mesas: "+cant);
            System.out.println("promedio de ingresos por mesa: "+(total/cant));
        }
    }
    public void estadisticaMensual(){
        ArrayList<Factura> lista = getFacturasPorFiltros(null,LocalDate.now().minusMonths(1),null);
        if (lista.isEmpty()){
            System.out.println();//SL
            System.out.println("No hay facturas...");
            System.out.println();//SL
        } else {
            double total = 0;
            int cant = 0;
            for (Factura factura : lista) {
                total += factura.getTotal();
                cant++;
            }
            System.out.println("- - ESTADISTICAS - -");
            System.out.println("ingresos: "+total);
            System.out.println("mesas: "+cant);
            System.out.println("promedio de ingresos por mesa: "+(total/cant));
        }
    }

    public void estadisticaAnual(){
        ArrayList<Factura> lista = getFacturasPorFiltros(null,LocalDate.now().minusYears(1),null);
        if (lista.isEmpty()){
            System.out.println();//SL
            System.out.println("No hay facturas...");
            System.out.println();//SL
        } else {
            double total = 0;
            int cant = 0;
            for (Factura factura : lista) {
                total += factura.getTotal();
                cant++;
            }
            System.out.println("- - ESTADISTICAS - -");
            System.out.println("ingresos: "+total);
            System.out.println("mesas: "+cant);
            System.out.println("promedio de ingresos por mesa: "+(total/cant));
        }
    }

    public void estadisticaPorIntervalos(){
        System.out.println("Ingrese la fecha más vieja:");
        LocalDate fechaMasVieja = CTool.promptFecha();

        System.out.println("Ingrese la fecha más reciente:");
        LocalDate fechaMasNueva = CTool.promptFecha();


        ArrayList<Factura> lista = getFacturasPorFiltros(null,fechaMasVieja,fechaMasNueva);
        if (lista.isEmpty()){
            System.out.println();//SL
            System.out.println("No hay facturas...");
            System.out.println();//SL
        } else {
            double total = 0;
            int cant = 0;
            for (Factura factura : lista) {
                total += factura.getTotal();
                cant++;
            }
            System.out.println("- - ESTADISTICAS - -");
            System.out.println("ingresos: "+total);
            System.out.println("mesas: "+cant);
            System.out.println("promedio de ingresos por mesa: "+(total/cant));
        }
    }







    @Override
    public void guardarArchivo() {
        CTool.guardarJSON("Facturas.json",array);
    }
    @Override
    public void cargarArchivo() throws FileNotFoundException {
        array = new ArrayList<>(CTool.cargarJSON("Facturas.json", new TypeToken<ArrayList<Factura>>() {}.getType()));
    }
}
