package mesas;

import otros.Gestorable;
import personal.Personal;
import productos.Pedido;

public class Mesa implements Gestorable {
    private int id;
    private int capacidadMax;
    private int numOcupantes;
    private Personal camarero;
    public Pedido pedido;
    private EstadoMesa estadoMesa;

    public Mesa(int id, int capacidadMax) {
        this.id = id;
        this.capacidadMax = capacidadMax;
        this.numOcupantes = 0;
        this.camarero = null;
        this.pedido = null;
        this.estadoMesa = EstadoMesa.LIBRE;
    }

    //vvv Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCapacidadMax() {
        return capacidadMax;
    }
    public void setCapacidadMax(int capacidadMax) {
        this.capacidadMax = capacidadMax;
    }
    public int getNumOcupantes() {
        return numOcupantes;
    }
    public void setNumOcupantes(int numOcupantes) {
        this.numOcupantes = numOcupantes;
    }
    public Personal getCamarero() {
        return camarero;
    }
    public void setCamarero(Personal camarero) {
        this.camarero = camarero;
    }
    public Pedido getPedido() {
        return pedido;
    }
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    public EstadoMesa getEstadoMesa() {
        return estadoMesa;
    }
    public void setEstadoMesa(EstadoMesa estadoMesa) {
        this.estadoMesa = estadoMesa;
    }
    public void abrirPedido(){
        this.pedido = new Pedido();
    }
    //^^^ Getters y Setters


    @Override
    public String toString() {
        return "Mesa N°"+id+" - capacidad:"+capacidadMax+" - estado:"+estadoMesa;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mesa mesa)) return false;

        return id == mesa.id;
    }
}
