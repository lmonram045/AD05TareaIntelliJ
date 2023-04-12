package org.iesalandalus.luis.modelo;

public class Mascota {
    private String id;
    private String nombre;
    private String tipo;
    private String raza;
    private int edad;
    private int idCliente;

    /**
     * Constructor por defecto
     */
    public Mascota() {
    }

    /**
     * Constructor con parámetros
     * @param id identificador de la mascota
     * @param nombre nombre de la mascota
     * @param tipo tipo de la mascota
     * @param raza raza de la mascota
     * @param edad edad de la mascota
     * @param idCliente identificador del cliente
     */
    public Mascota(String id, String nombre, String tipo, String raza, int edad, int idCliente) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.raza = raza;
        this.edad = edad;
        this.idCliente = idCliente;
    }

    /* Getters y setters */

    public String getId() {
        return id;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
