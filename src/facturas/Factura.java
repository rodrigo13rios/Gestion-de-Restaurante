package facturas;

import otros.Gestorable;
import productos.Pedido;

import java.time.LocalDateTime;

public class Factura implements Gestorable {
    private static int idCounter = 0;
    private int numeroFactura;
    private final LocalDateTime fechaEmicion;
    private final Pedido pedido;
    private final MetodoDePago metodoDePago;
    private final double total;

    public Factura(Pedido pedido, MetodoDePago metodoDePago) {
        this.numeroFactura = idCounter++;
        this.fechaEmicion = LocalDateTime.now();
        this.pedido = new Pedido(pedido);//'Copia Profunda', crea una copia inmutable.
        this.metodoDePago = metodoDePago;
        this.total = pedido.hacerCuenta();
    }

    // vvvvv SETERS y GETERS vvvvv
    public static int getIdCounter() {
        return idCounter;
    }
    public int getNumeroFactura() {
        return numeroFactura;
    }
    public LocalDateTime getFechaEmicion() {
        return fechaEmicion;
    }
    public Pedido getPedido() {
        return pedido;
    }
    public MetodoDePago getMetodoDePago() {
        return metodoDePago;
    }
    public double getTotal() {
        return total;
    }
    // ^^^^^ SETERS y GETERS ^^^^^

    @Override
    public String toString() {
       return  "-------------Factura N°"+numeroFactura+"----------------\n" +
                "Fecha de emicion= " + fechaEmicion +"\n"+
                "Metodo de pago  = " + metodoDePago +"\n"+
                "Detalle         = " + pedido.getArray() + "\n" +
                "Descuento       = " + pedido.getDescuento() +"\n"+
                "Total           = " + total +"\n"+
                "_____________________________________\n";
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Factura factura)) return false;

        return numeroFactura == factura.numeroFactura;
    }

    @Override
    public int getId() {
        return numeroFactura;
    }

    @Override
    public void setId(int numeroFactura) {
        this.numeroFactura=numeroFactura;
    }
}
