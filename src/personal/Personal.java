package personal;

import otros.Gestorable;

import java.time.LocalDate;

public class Personal implements Gestorable {
    private static int contadorId = 0;
    private int id;
    private String nombre;
    private String apellido;
    private int dni;
    private LocalDate fechaAlta;
    private LocalDate fechaBaja;

    public Personal(String nombre, String apellido, int dni) {
        //Constructor sin fechas (default)
        this.id = contadorId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaAlta = LocalDate.now();
        this.fechaBaja = null;
        contadorId++;
    }
    public Personal(String nombre, String apellido, int dni, LocalDate fechaAlta, LocalDate fechaBaja) {
        //Constructor con fechas
        this.id = contadorId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        contadorId++;
    }


    //vvv Getters y Setters
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
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public int getDni() {
        return dni;
    }
    public void setDni(int dni) {
        this.dni = dni;
    }
    public LocalDate getFechaAlta() {
        return fechaAlta;
    }
    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    public LocalDate getFechaBaja() {
        return fechaBaja;
    }
    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    //^^^ Getters y Setters


    @Override
    public String toString() {
        if (fechaBaja!=null){
            return nombre+" "+apellido+" DNI:"+dni+" ["+fechaAlta+" / "+fechaBaja+"]";
        } else {
            return nombre+" "+apellido+" DNI:"+dni+" ["+fechaAlta+" / en Activo]";
            //Ej: Sebastian Kloster DNI:19096400 [2020/1/18 / en activo]
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Personal personal)) return false;

        return dni == personal.dni;
    }
}
