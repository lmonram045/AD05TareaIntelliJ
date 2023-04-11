package org.iesalandalus.luis.modelo;

public class Cliente {
    int id;
    String nombre;
    String direccion;
    String telefono;
    String correo;

    /**
     * Constructor por defecto
     */
    public Cliente() {
    }

    /**
     * Constructor con parámetros
     * @param id identificador del cliente
     * @param nombre nombre del cliente
     * @param direccion dirección del cliente
     * @param telefono teléfono del cliente
     * @param correo correo del cliente
     */
    public Cliente(int id, String nombre, String direccion, String telefono, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
    }

    /* Getters y setters */
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
