package mesas;

public enum EstadoMesa {
    LIBRE, OCUPADA, PAGANDO, CERRADA,RESERVADO;

    /*
    Libre: cuando no hay nadie, esperando a ser asignada.
    Ocupada: Cuando se "abre la mesa". Gente ocupandola, con o sin pedido asignado.
    Pagando: Cuando se realiza la cuenta y se lleva a la mesa. Esperando a que pagen.
    Cerrada: Los ocupantes ya pagaron. Esperando a que se vayan y liberen la Mesa.
    Reservado: Cuando reservaron una mesa pero no hay gente ocupandola.
    */
}
