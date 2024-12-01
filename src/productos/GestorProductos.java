package productos;
import com.google.gson.reflect.TypeToken;
import excepciones.ElementoNoEncontradoException;
import excepciones.ElementoYaExistenteException;
import excepciones.NumeroInvalidoException;
import mesas.Mesa;
import otros.CTool;
import otros.GestorGenerico;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class GestorProductos extends GestorGenerico<Producto> {

    @Override
    public Producto instanciar() throws ElementoYaExistenteException {
        System.out.println("Ingrese el nombre del producto:");
        String nombre = scanner.nextLine();
        if (array.contains(new Producto(nombre.toUpperCase(), 0))) {
            throw new ElementoYaExistenteException("El Nombre del producto ya está en uso");
        }
        double precio = 0;
        boolean flag = false;
        do {
            try {
                System.out.println("Ingrese el precio:");
                precio = scanner.nextDouble();
                scanner.nextLine();//ComeSL
                if (precio < 0) {
                    System.err.println("El precio no puede ser menor que 0");
                    System.out.println("Intente nuevamente!");
                } else {
                    flag = true; //Precio Correcto
                }
            } catch (InputMismatchException e) {
                System.err.println("El tipo de valor ingresado no concuerda con lo requerido: "+e.getMessage());
                System.out.println("Vuelva a intentar!");
            }
        } while (!flag);
        return new Producto(nombre.toUpperCase(),precio);
    }

    @Override
    public int buscarPos() throws ElementoNoEncontradoException {
        super.mostrarArray();
        //Print Ej: CocaCola - $3.000
        System.out.println("Ingrese el nombre del producto:");
        String nombre = scanner.nextLine();
        int pos = array.indexOf(new Producto(nombre.toUpperCase(), 0));//Busca en el array un producto con mismo nombre y devuelve su índice.
        if (pos == -1){
            throw new ElementoNoEncontradoException("El nombre del producto es erroneo o no existe");
        }
        System.out.println("*-> " + getByPos(pos));//Print objeto seleccionado
        return pos;
    }

    @Override
    public void guardarArchivo() {
        CTool.guardarJSON("Productos.json",array);
    }
    @Override
    public void cargarArchivo() throws FileNotFoundException {
        array = new ArrayList<>(CTool.cargarJSON("Productos.json", new TypeToken<ArrayList<Producto>>() {}.getType()));
    }
}
