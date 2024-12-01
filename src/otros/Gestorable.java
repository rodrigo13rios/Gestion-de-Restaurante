package otros;

public interface Gestorable {
    /* El fin de esta interfaz es asegurar la implementacion de
    metodos básicos que serán requeridos por el respectivo Gestor */
    @Override
    String toString();
    @Override
    boolean equals(Object obj);

    public int getId();
    public void setId(int id);
}
