package reservas;

import mesas.Mesa;
import otros.Gestorable;

import java.time.LocalDate;
import java.util.Objects;

public class Reserva implements Gestorable {
    private static int id=0;
    private int idReserva;
    private Mesa mesa;
    private String nombreCliente;
    private int numeroTelefono;
    private LocalDate fecha;
    private TurnosReserva turno;
    private int cantPersona;

    public Reserva(Mesa mesa, String nombreCliente, int numeroTelefono, LocalDate fecha, TurnosReserva turno, int cantPersonas) {
        this.idReserva=id++;
        this.mesa = mesa;
        this.nombreCliente = nombreCliente;
        this.numeroTelefono = numeroTelefono;
        this.fecha = fecha;
        this.turno = turno;
        this.cantPersona = cantPersonas;
    }


    public Mesa getMesa() {
        return mesa;
    }
    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }
    public String getNombreCliente() {
        return nombreCliente;
    }
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    public int getNumeroTelefono() {
        return numeroTelefono;
    }
    public void setNumeroTelefono(int numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public TurnosReserva getTurno() {
        return turno;
    }
    public void setTurno(TurnosReserva turno) {
        this.turno = turno;
    }
    public int getCantPersona() {
        return cantPersona;
    }
    public void setCantPersona(int cantPersona) {
        this.cantPersona = cantPersona;
    }

    @Override
    public String toString() {
        return "-------------Reserva-----------------\n" +
                "IdReserva      = " + idReserva +"\n"+
                "Mesa           = " + mesa +"\n"+
                "NombreCliente  = " + nombreCliente + "\n" +
                "NumeroTelefono = " + numeroTelefono +"\n"+
                "Fecha          = " + fecha +"\n"+
                "Turno          = " + turno +"\n"+
                "CantPersona    = " + cantPersona +"\n"+
                "_____________________________________\n";
    }

    @Override
    public final boolean equals(Object o) { //Dos reservas serán iguales si, y solo si: ambas comparten la misma Mesa, la misma Fecha, y el mismo Turno. Ya que de esta forma, es imposible hacerlas coexistir al mismo tiempo.
        if (this == o) return true;
        if (!(o instanceof Reserva reserva)) return false;

        return Objects.equals(mesa, reserva.mesa) && Objects.equals(fecha, reserva.fecha) && turno == reserva.turno;
    }

    @Override
    public int getId() {
        return idReserva;
    }

    @Override
    public void setId(int idReserva) {
        this.idReserva=idReserva;

    }
}
