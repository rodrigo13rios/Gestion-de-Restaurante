package otros;

import excepciones.ElementoNoEncontradoException;
import excepciones.ElementoYaExistenteException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class GestorGenerico<T extends Gestorable> {
    protected ArrayList<T> array = new ArrayList<>();
    protected final Scanner scanner = CTool.scanner;//Scanner común para todos los gestores.

    public abstract T instanciar() throws ElementoYaExistenteException;//Debe crear un objeto, paso a paso, por el usuario.
    public abstract int buscarPos() throws ElementoNoEncontradoException;//Debe buscar en el array un objeto y devolver su índice.

    public void agregar(T elemento) {//Agrega un elemento al array
        array.add(elemento);
        System.out.println("Agregado con éxito!");
    }

    public void editar(){
        try {
            int pos = buscarPos(); //obtener indice del objeto a editar.
            T viejoElemento = getByPos(pos);
            T elemento = instanciar(); //Creamos el nuevo objeto.
            array.set(pos,null);//Borro el elemento original.
            elemento.setId(viejoElemento.getId()); //Seteo el mismo ID que el objeto anterior.
            array.set(pos,elemento); //Reemplazo el antiguo objeto por el nuevo.
            System.out.println("ACTUALIZADO-> "+getByPos(pos));//Print objeto actualizado.
        } catch (ElementoNoEncontradoException e) {
            System.err.println("Error al intentar editar. No se ha encontrado el elemento: "+e.getMessage());
        } catch (ElementoYaExistenteException e) {
            System.err.println("Error al intentar editar. No se ha podido crear el elemento: "+e.getMessage());
        }
    };

    public void borrar(){
        try {
            int pos = buscarPos();
            array.remove(pos);
            System.out.println("Eliminado con éxito!");
        } catch (ElementoNoEncontradoException e) {
            System.err.println("Error al borrar: "+e.getMessage());
        }
    }

    public T getByPos(int pos){//Devuelve el objeto que se encuentre en una posicion del array específica
        return array.get(pos);
    }

    public T getById(int id) throws ElementoNoEncontradoException {
        for (T e : array) {
            if (e.getId() == id) {
                return e;
            }
        }
        throw new ElementoNoEncontradoException("No se ha encontrado ningun elemento con la ID "+id);
    }

    public int getArraySize(){
        return array.size();
    }

    public void mostrarArray(){
        if (array.isEmpty()) {
            System.out.println("Aún no hay elementos creados");
        } else {
            for (T elemento : array){
                System.out.println(elemento);
            }
        }
        System.out.println();//Salto de Linea
    }


    public abstract void guardarArchivo();
    public abstract void cargarArchivo() throws FileNotFoundException;
}
