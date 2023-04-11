package org.iesalandalus.luis.modelo;

public class Mascota {
    private String id;
    private String nombre;
    private String tipo;
    private int edad;
    private Cliente cliente;

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
     * @param edad edad de la mascota
     * @param cliente cliente de la mascota
     */
    public Mascota(String id, String nombre, String tipo, int edad, Cliente cliente) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.edad = edad;
        this.cliente = cliente;
    }

    /* Getters y setters */

    public String getId() {
        return id;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
