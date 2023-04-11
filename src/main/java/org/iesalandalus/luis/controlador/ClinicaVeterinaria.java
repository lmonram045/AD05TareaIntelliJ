package org.iesalandalus.luis.controlador;

import org.iesalandalus.luis.modelo.Cliente;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;

import java.lang.reflect.InvocationTargetException;




public class ClinicaVeterinaria {
    private Collection coleccion; // Colección de mascotas

    /**
     * Constructor con parámetros. Lo usaremos para conectarnos a la base de datos.
     * @param uri URI de la base de datos
     * @param usuario Usuario de la base de datos
     * @param clave Clave de la base de datos
     */
    public ClinicaVeterinaria(String uri, String usuario, String clave) {
        String driver = "org.exist.xmldb.DatabaseImpl"; // Driver de la base de datos
        //String uriBaseDatos = "xmldb:exist://localhost:8080/exist/xmlrpc/db"; // URI de la base de datos
        //String usuarioBaseDatos = "admin"; // Usuario de la base de datos
        //String claveBaseDatos = "admin"; // Clave de la base de datos
        String recurso = "/db";

        try {
            Class<?> cl = Class.forName(driver); // Carga el driver de la base de datos

            Database db = (Database) getClass().getDeclaredConstructor().newInstance(); // Instancia la base de datos
            DatabaseManager.registerDatabase(db); // Registra la base de datos

            coleccion = DatabaseManager.getCollection(uri + recurso, usuario, clave); // Obtiene la colección de mascotas
            if (coleccion != null) {
                System.out.println("Conectado a la base de datos");
            } else {
                System.out.println("No se ha podido conectar a la base de datos");
                // TODO: NO SE SI DESPUES DE ESTO TENDRÍA QUE HACER ALGO O SALIR DEL PROGRAMA.
            }

            // TODO: La siguiente linea no se si está bien, en lo que pasó el profesor es un poco diferente.
            XPathQueryService servicio = null; // Obtiene el servicio de la colección
            try {
                servicio = coleccion.getService(XPathQueryService.class);
                servicio.setProperty("indent", "yes"); // Indenta el resultado de la consulta
            } catch (XMLDBException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
            System.out.println("Servicio de la base de datos obtenido");

        } catch (XMLDBException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Método para agregar clientes a la base de datos
     */
    public void agregarCliente(Cliente cliente) {

    }

    /**
     * Método para modificar un cliente de la base de datos
     */
    public void modificarCliente(Cliente cliente) {

    }

    /**
     * Método para eliminar un cliente de la base de datos
     */
    public void eliminarCliente(Cliente cliente) {

    }

    /**
     * Método para agregar una mascota a la base de datos
     */
    public void agregarMascota() {

    }

    /**
     * Método para modificar una mascota de la base de datos
     */
    public void modificarMascota() {

    }

    /**
     * Método para eliminar una mascota de la base de datos
     */
    public void eliminarMascota() {

    }

    /**
     * Método para listar clientes de la base de datos
     */
    public ResourceSet listarClientes() {
        return null;
    }

    /**
     * Método para listar mascotas de la base de datos
     */
    public ResourceSet listarMascotas() {
        return null;
    }

    /**
     * Método para encontrar mascotas por tipo
     * @param tipo Tipo de mascota
     * @return Lista de mascotas del tipo indicado
     */
    public ResourceSet buscarMascotasPorTipo(String tipo) {
        return null;
    }

    /**
     * Método para encontrar mascotas a partir de un cliente
     */
    public ResourceSet buscarMascotasPorCliente(int idCliente) {
        return null;
    }

    /**
     * Método para encontrar clientes con varias mascotas
     */
    public ResourceSet buscarClientesConVariasMascotas() {
        return null;
    }

    /**
     * Método para obtener el promedio de edad de las mascotas de un cliente
     */
    public double obtenerPromedioEdadMascotasCliente(int idCliente) {
        return 0;
    }

}
