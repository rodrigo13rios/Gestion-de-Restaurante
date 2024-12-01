package otros.adaptadoresGSON;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import productos.Producto;

public class ProductoAdapter extends TypeAdapter<Producto> {
    @Override
    public void write(JsonWriter out, Producto producto) throws IOException {
        if (producto == null) {
            out.nullValue();
        } else {
            // Convertimos Producto a una representación simple (por ejemplo, el nombre o el id)
            out.beginObject();
            out.name("id").value(producto.getId());
            out.name("nombre").value(producto.getNombre());
            out.name("precio").value(producto.getPrecio());
            out.endObject();
        }
    }

    @Override
    public Producto read(JsonReader in) throws IOException {
        in.beginObject();
        int id = 0;
        String nombre = null;
        double precio = 0;

        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "id":
                    id = in.nextInt();
                    break;
                case "nombre":
                    nombre = in.nextString();
                    break;
                case "precio":
                    precio = in.nextDouble();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        return new Producto(id, nombre, precio);
    }
}
