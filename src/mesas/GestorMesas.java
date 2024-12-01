package mesas;

import com.google.gson.reflect.TypeToken;
import excepciones.ElementoNoEncontradoException;
import excepciones.ElementoYaExistenteException;
import excepciones.NumeroInvalidoException;
import facturas.GestorFacturas;
import facturas.MetodoDePago;
import otros.CTool;
import otros.GestorGenerico;
import personal.GestorPersonal;
import productos.GestorProductos;
import productos.Producto;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GestorMesas extends GestorGenerico<Mesa> {
    private final GestorProductos gestorProductos;
    private final GestorPersonal gestorCamareros;
    private final GestorFacturas gestorFacturas;

    public GestorMesas(GestorProductos gestorProductos, GestorPersonal gestorCamareros, GestorFacturas gestorFacturas) {
        this.gestorProductos = gestorProductos;
        this.gestorCamareros = gestorCamareros;
        this.gestorFacturas = gestorFacturas;
    }

    @Override
    public Mesa instanciar() throws ElementoYaExistenteException {
        try{
            int id = CTool.promptOption("Ingrese el número identificador de la mesa: ");
            if (id==0) {
                throw new NumeroInvalidoException("El numero de la Mesa no puede ser 0");
            } else if (array.contains(new Mesa(id, 0))) {
                throw new ElementoYaExistenteException("El Numero Identificador de la mesa ya está en uso");
            }
            int capacidadMax = CTool.promptOption("Ingrese la capacidad máxima de ocupantes: ");
            if (capacidadMax<=0) {
                throw new NumeroInvalidoException("La capacidad máxima de la mesa debe de ser mayor a 0");
            }
            return new Mesa(id,capacidadMax);

        }catch (NumeroInvalidoException e){
            System.err.println("Error al cargar una nueva mesa: "+e.getMessage());
            System.out.println("Vuelve a intentarlo!");
            return instanciar();
        }
    }

    @Override
    public int buscarPos() throws ElementoNoEncontradoException {
        super.mostrarArray();
        //Print Ej: Mesa N°3 - estado:LIBRE
        int id = CTool.promptOption("Ingrese el Número de la mesa: ");
        int pos = array.indexOf(new Mesa(id, 0));//Busca en el array un elemento con mismo ID y devuelve su índice.
        if (pos == -1) {
            throw new ElementoNoEncontradoException("La Mesa N° '"+id+"' no existe");
        }
        System.out.println("*-> " + getByPos(pos));//Print objeto seleccionado
        return pos;
    }

    @Override
    public void editar() {
        try{
            int pos = buscarPos(); //obtener indice del objeto a editar.
            int id = CTool.promptOption("Ingrese el número identificador de la mesa: ");
            if (id != array.get(pos).getId()) {//Si se cambia a un ID distinto al anterior...
                if (id==0) {
                    throw new NumeroInvalidoException("El numero de la Mesa no puede ser 0");
                } else if (array.contains(new Mesa(id, 0))) {
                    throw new ElementoYaExistenteException("El Numero Identificador de la mesa ya está en uso");
                }
            }
            int capacidadMax = CTool.promptOption("Ingrese la capacidad máxima de ocupantes: ");
            if (capacidadMax<1){
                throw new NumeroInvalidoException ("La capacidad máxima debe de ser mayor a 0");
            }
            array.get(pos).setId(id);
            array.get(pos).setCapacidadMax(capacidadMax);

            System.out.println("ACTUALIZADO-> "+getByPos(pos));
        } catch (NumeroInvalidoException | ElementoNoEncontradoException | ElementoYaExistenteException e){
            System.err.println("Error al modificar la mesa: "+e.getMessage());
        }
    }

    public void mostrarMesasLibres() {
        for (Mesa mesa:array) {
            if (mesa.getEstadoMesa()==EstadoMesa.LIBRE) {
                System.out.println(mesa);
            }
        }
    }


    //Mesa LIBRE - Mesa LIBRE - Mesa LIBRE - Mesa LIBRE - Mesa LIBRE
    public void abrirMesa(Mesa mesa){
        if (mesa.getEstadoMesa() != EstadoMesa.RESERVADO || mesa.getNumOcupantes() > mesa.getCapacidadMax() || mesa.getNumOcupantes() <= 0) {
            int numOcupantes = CTool.promptOption("Cantidad de personas: ");
            if (numOcupantes < 1) {
                System.err.println("La cantidad de ocupantes no puede ser menor a 1");
                return;
            } else if (numOcupantes > mesa.getCapacidadMax()) {
                System.err.println("La cantidad de ocupantes no puede ser mayor a la capacidad maxima de la mesa ("+mesa.getCapacidadMax()+")");
                return;
            }
            mesa.setNumOcupantes(numOcupantes);
        }

        while (mesa.getCamarero() == null) {
            System.out.println("- - Camareros Disponibles - -");
            if (gestorCamareros.getArraySize() < 1){ //Si no hay camareros creados
                System.err.println("\nAún no hay Camareros asignados! Primero cree algun camarero.");
                return;
            }
            try {
                mesa.setCamarero(gestorCamareros.getByPos(gestorCamareros.buscarPos())); //mesa.camarero = getByPos(BuscarPos())
            } catch (ElementoNoEncontradoException e) {
                System.err.println("Error! No se ha podido encontrar al camarero: "+e.getMessage());
                System.out.println("Intente nuevamente!");
            }
        }

        mesa.abrirPedido();// Crea un nuevo objeto 'Pedido' para la mesa.
        mesa.setEstadoMesa(EstadoMesa.OCUPADA);

        System.out.println("Mesa abierta con exito!");
        System.out.println("*-> "+mesa.getNumOcupantes()+" ocupantes, Camarero:"+mesa.getCamarero().getNombre()+" "+mesa.getCamarero().getApellido());
    }
    public void cerrarMesa(Mesa mesa) {
        mesa.setPedido(null);
        mesa.setCamarero(null);
        mesa.setNumOcupantes(0);
        mesa.setEstadoMesa(EstadoMesa.LIBRE);
        System.out.println("Mesa N°"+mesa.getId()+" ha sido cerrada!");
    }

    //Mesa OCUPADA - Mesa OCUPADA - Mesa OCUPADA - Mesa OCUPADA - Mesa OCUPADA
    public void agregarAlPedido(Mesa mesa) {
        try {
            System.out.println("- - Productos Disponibles - -");
            if (gestorProductos.getArraySize() < 1){ //Si no hay camareros creados
                System.err.println("\nAún no hay Productos asignados! Primero cree algun producto.");
                return;
            }
            Producto producto = gestorProductos.getByPos(gestorProductos.buscarPos());
            int cantidad = CTool.promptOption("Ingrese la cantidad: ",0,99);

            mesa.pedido.agregar(producto,cantidad);
            System.out.println("Producto agregado!");
        } catch (ElementoNoEncontradoException e) {
            System.err.println("Error! No se ha podido encontrar al producto: "+e.getMessage());
        }
    }
    public void quitarDelPedido(Mesa mesa) {
        try {
            System.out.println("- - Pedido - -");
            mesa.pedido.mostrarPedido();
            Producto producto = gestorProductos.getByPos(gestorProductos.buscarPos());
            int cantidad = CTool.promptOption("Ingrese la nueva cantidad (0 para eliminar totalmente): ",0,99);

            mesa.pedido.setCantidad(producto,cantidad);
        } catch (ElementoNoEncontradoException e) {
            System.err.println("Error al intentar quitar el produtcto del pedido: "+e.getMessage());
        }
    }
    public void hacerCuentaMesa(Mesa mesa) {
        System.out.println("SUBTOTAL: "+mesa.pedido.hacerCuenta());
        mesa.pedido.setDescuento(CTool.promptOption("Ingrese el Descuento (0% sin desc. | 100% gratis): %",0,100));

        mesa.setEstadoMesa(EstadoMesa.PAGANDO);
        System.out.println("TOTAL *-> "+mesa.pedido.hacerCuenta());
    }


    //Mesa PAGANDO - Mesa PAGANDO - Mesa PAGANDO - Mesa PAGANDO - Mesa PAGANDO
    public void confirmarPagoMesa(Mesa mesa) {
        System.out.println(
                """
                - - Metodo de Pago - -
                1-Efectivo
                2-Debito
                3-Credito
                
                """);
        int option = CTool.promptOption("Ingrese el metodo de pago: ",1,3);
        MetodoDePago metodoDePago = switch (option) {
            case 3 -> MetodoDePago.CREDITO;
            case 2 -> MetodoDePago.DEBITO;
            default -> MetodoDePago.EFECTIVO;
        };

        gestorFacturas.agregar(gestorFacturas.instanciar(mesa,metodoDePago)); //Crear la Factura
        mesa.setEstadoMesa(EstadoMesa.CERRADA);
    }
    public void editarPedidoMesa(Mesa mesa) {
        mesa.setEstadoMesa(EstadoMesa.OCUPADA);//Vuelve al menu de mesa ocupada.
    }


    @Override
    public void guardarArchivo() {
        CTool.guardarJSON("Mesas.json",array);
    }
    @Override
    public void cargarArchivo() throws FileNotFoundException {
        array = new ArrayList<>(CTool.cargarJSON("Mesas.json", new TypeToken<ArrayList<Mesa>>() {}.getType()));
    }
}