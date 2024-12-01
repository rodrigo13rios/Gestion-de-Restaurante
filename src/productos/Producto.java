package productos;

import otros.Gestorable;

public class Producto implements Gestorable {
    private static int idCounter = 0;
    private int id;
    private String nombre;
    private double precio;

    public Producto(String nombre, double precio) {
        this.id = idCounter;
        this.nombre = nombre;
        this.precio = precio;
        idCounter++;
    }
    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        idCounter += id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return nombre+" - $"+precio;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto producto)) return false;

        return nombre.equals(producto.nombre);
    }
}
