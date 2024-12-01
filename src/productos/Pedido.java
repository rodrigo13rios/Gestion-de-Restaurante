package productos;

import excepciones.ElementoNoEncontradoException;

import java.util.HashMap;

public class Pedido {
    private HashMap<Producto,Integer> array = new HashMap<>();
    private int descuento = 0;

    public Pedido() {

    }
    public Pedido(Pedido pedido) {//Constructor de 'Copia Profunda' (permite clonar el objeto)
        this.array = new HashMap<>(pedido.getArray());//Copia profunda del array
        this.descuento = pedido.getDescuento();
    }

    public HashMap<Producto, Integer> getArray() {
        return array;
    }
    public void setArray(HashMap<Producto, Integer> array) {
        this.array = array;
    }
    public int getDescuento() {
        return descuento;
    }
    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }


    public void agregar(Producto producto, Integer cantidad){
        if (array.containsKey(producto)) {
            array.put(producto,(array.get(producto) + cantidad));
        } else {
            array.put(producto,cantidad);
        }
        System.out.println("*->"+producto.getNombre()+" cant.Total:"+array.get(producto));
    }
    public void setCantidad(Producto producto, int cantidad) throws ElementoNoEncontradoException {
        if (array.containsKey(producto)) {
            if (cantidad > 0) {
                array.put(producto, cantidad);
                System.out.println("*->"+producto.getNombre()+" cant.Total:"+array.get(producto));
            } else {
                array.remove(producto);
                System.out.printf("Producto "+producto.getNombre()+" ha sido removido del pedido!");
            }
        } else {
            throw new ElementoNoEncontradoException("El producto no se encuentra en el pedido");
        }
    }

    public double hacerCuenta(){
        double total = 0;
        for (Producto producto : array.keySet()) {
            total += (producto.getPrecio() * array.get(producto));
            //Total += precio * cantidad;
        }
        if (descuento > 0) {
            total -= total * descuento / 100;
            //Total - 'respectivo descuento'
        }
        return total;
    }


    public void mostrarPedido(){
        if (array.isEmpty()) {
            System.out.println("Aún no hay productos en el pedido");
        } else {
            for (Producto p : array.keySet()) {
                System.out.println(p.getNombre()+" - cant:"+array.get(p));
            }
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");//Concatenador de texto
        for (Producto producto : array.keySet()) {
            sb.append(array.get(producto)).append(" ").append(producto.getNombre()).append(" | ");
            // [                     3                               CocaCola                 |
            // [3 CocaCola |
        }

        // Eliminar el último separador" | "y cerrar el corchete
        if (sb.length() > "[".length()) { // "Si hay texto más allá del prefijo inicial..." Para evitar errores si el array está vacío
            sb.setLength(sb.length() - 3);
        }
        sb.append("]");

        return sb.toString();
    }


}
