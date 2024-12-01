package reservas;

import com.google.gson.reflect.TypeToken;
import excepciones.ElementoNoEncontradoException;
import excepciones.ElementoYaExistenteException;
import excepciones.NumeroInvalidoException;
import mesas.EstadoMesa;
import mesas.GestorMesas;
import mesas.Mesa;
import otros.CTool;
import otros.GestorGenerico;

import javax.swing.text.Element;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class GestorReservas extends GestorGenerico<Reserva> {
    private final GestorMesas gestorMesas;

    public GestorReservas(GestorMesas gestorMesas) {
        this.gestorMesas = gestorMesas;
    }

    @Override
    public Reserva instanciar() throws ElementoYaExistenteException {
        gestorMesas.mostrarArray();//Muestra las mesas.
        int numMesa = CTool.promptOption("Ingrese el Numero de Mesa para la reserva: ");
        Mesa mesa;
        try {
            mesa = gestorMesas.getById(numMesa);
        } catch (ElementoNoEncontradoException e) {
            throw new ElementoYaExistenteException("Error al intentar seleccionar una mesa: "+e.getMessage());
        }

        System.out.println("Ingrese el nombre del cliente:");
        String nombreCliente=scanner.nextLine();

        int numtelefono = CTool.promptOption("Ingrese el numero de telefono del cliente: ");

        System.out.println("Ingrese la fecha para la reserva:");
        LocalDate fecha= CTool.promptFecha();//se encarga de pedir las fechas

        TurnosReserva turnoReserva = null;
        System.out.println("- - turnos - -");
        System.out.println("""
                1-MedioDia
                2-Noche
                """);
        int turno = CTool.promptOption("Ingrese el turno deseado: ",1,2);
        if (turno == 1) {
            turnoReserva = TurnosReserva.MEDIODIA;
        } else if (turno == 2) {
            turnoReserva = TurnosReserva.NOCHE;
        }

        int cantPersonas = CTool.promptOption("Ingrese la cantidad de personas: ");
        try {
            if (cantPersonas>mesa.getCapacidadMax()) {
                throw new NumeroInvalidoException("La cantidad de personas ingresadas supera la capacidad de la mesa seleccionada");
            }
        } catch (NumeroInvalidoException e) {
            throw new ElementoYaExistenteException(e.getMessage());
        }

        if (array.contains(new Reserva(mesa,null,0,fecha,turnoReserva,0))) {
            //(si la mesa ya está reservada para esa fecha y turno...)
            throw new ElementoYaExistenteException("Ya existe otra reserva para la mesa en esa fecha y turno");
        }

        return new Reserva(mesa,nombreCliente,numtelefono,fecha,turnoReserva,cantPersonas);
    }


    public Reserva getReservaByMesa(Mesa mesa) throws ElementoNoEncontradoException {
        for (Reserva reserva : array) {
            if (reserva.getMesa().equals(mesa) && reserva.getFecha().isEqual(LocalDate.now())) {
                return reserva;
            }
        }
        throw new ElementoNoEncontradoException("la mesa no se ha encontrado entre las reservas.");
    }
    public void eliminarReservaByMesa(Mesa mesa) {
        LocalDate fechaHoy = LocalDate.now();
        TurnosReserva turnoActual = getTurnoActual();
        array.remove(new Reserva(mesa,null,0,fechaHoy,turnoActual,0));
    }
    public void verificarReservas() {// Verifica las reservas de hoy
        LocalDate fechaHoy = LocalDate.now();
        TurnosReserva turnoActual = getTurnoActual();

        for (Reserva reserva : array) {
            if (reserva.getFecha().isEqual(fechaHoy) && reserva.getTurno() == turnoActual) {
                if (reserva.getMesa().getEstadoMesa() == EstadoMesa.LIBRE) {
                    reserva.getMesa().setEstadoMesa(EstadoMesa.RESERVADO);
                    reserva.getMesa().setNumOcupantes(reserva.getCantPersona());
                }
            }
        }
    }

    public void verificarReservasTotal() {// Verifica las Reservas de forma completa
        LocalDate fechaHoy = LocalDate.now();
        TurnosReserva turnoActual = getTurnoActual();

        Reserva reserva;
        for (int i = array.size() - 1; i >= 0; i--) { // 'Iteracion inversa' para poder eliminar elementos de ser necesario.
            reserva = array.get(i);

            // Elimina las reservas de fechas antiguas.
            if (reserva.getFecha().isBefore(fechaHoy)) {
                array.remove(i);
                System.out.println("Reserva de "+reserva.getNombreCliente()+" para el dia "+reserva.getFecha()+" desestimada. La fecha es anterior a la actual");

            // Establece las mesas reservadas para el turno y fecha actual.
            } else if (reserva.getFecha().isEqual(fechaHoy) && reserva.getTurno() == turnoActual) {
                try {
                    reserva.setMesa(gestorMesas.getById(reserva.getMesa().getId())); // Actualiza la referencia de la mesa (se pierden al cargar de archivos)
                    if (reserva.getMesa().getEstadoMesa() == EstadoMesa.LIBRE || reserva.getMesa().getEstadoMesa() == EstadoMesa.RESERVADO) {
                        reserva.getMesa().setEstadoMesa(EstadoMesa.RESERVADO);
                        reserva.getMesa().setNumOcupantes(reserva.getCantPersona());
                    }
                    System.out.println("Reserva de "+reserva.getNombreCliente()+" para el dia de hoy ("+reserva.getFecha()+") pendiente!!!");
                } catch (ElementoNoEncontradoException e) {
                    array.remove(i);
                    System.out.println("Reserva de "+reserva.getNombreCliente()+" para hoy ("+reserva.getFecha()+") desestimada. La mesa establecida se encuentra desactualizada.");
                }
            }
        }
    }

    public TurnosReserva getTurnoActual() {
        if (LocalTime.now().isAfter(LocalTime.of(6,0)) && LocalTime.now().isBefore(LocalTime.of(18,0))) {
            //Si son despues de las 6h y antes de las 18h (turno Mediodia)
            return TurnosReserva.MEDIODIA;
        } else {
            return TurnosReserva.NOCHE;
        }
    }

    @Override
    public int buscarPos() throws ElementoNoEncontradoException {
        super.mostrarArray();

        int id = CTool.promptOption("Ingrese el id: ");//ingresa el id de la reserva
        Reserva reserva=getById(id);// obtengo la reserva
        int pos = array.indexOf(reserva);// obtengo la posicion de la reserva

        System.out.println("*-> "+getByPos(pos));//Print objeto seleccionado
        return pos;// retorno la posicion
    }

    @Override
    public void guardarArchivo() {
        CTool.guardarJSON("Reservas.json",array);
    }
    @Override
    public void cargarArchivo() throws FileNotFoundException {
        array = new ArrayList<>(CTool.cargarJSON("Reservas.json", new TypeToken<ArrayList<Reserva>>() {}.getType()));
    }
}