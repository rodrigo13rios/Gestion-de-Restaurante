package otros.adaptadoresGSON;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import productos.Pedido;
import productos.Producto;

public class PedidoAdapter extends TypeAdapter<Pedido> {
    private final Type productoType = new TypeToken<Producto>() {}.getType();

    @Override
    public void write(JsonWriter out, Pedido pedido) throws IOException {
        if (pedido == null) {
            out.nullValue();
        } else {
            out.beginObject();

            // Serializamos el HashMap de productos
            out.name("array");
            out.beginObject();
            for (Map.Entry<Producto, Integer> entry : pedido.getArray().entrySet()) {
                // Usamos el adaptador de Producto para serializar la clave (Producto)
                String productoJson = new Gson().toJson(entry.getKey());
                out.name(productoJson).value(entry.getValue());
            }
            out.endObject();

            out.name("descuento").value(pedido.getDescuento());

            out.endObject();
        }
    }

    @Override
    public Pedido read(JsonReader in) throws IOException {
        in.beginObject();
        HashMap<Producto, Integer> array = new HashMap<>();
        int descuento = 0;

        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "array":
                    in.beginObject();
                    while (in.hasNext()) {
                        String productoJson = in.nextName();
                        Producto producto = new Gson().fromJson(productoJson, Producto.class);
                        int cantidad = in.nextInt();
                        array.put(producto, cantidad);
                    }
                    in.endObject();
                    break;
                case "descuento":
                    descuento = in.nextInt();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        Pedido pedido = new Pedido();
        pedido.setArray(array);
        pedido.setDescuento(descuento);
        return pedido;
    }
}
