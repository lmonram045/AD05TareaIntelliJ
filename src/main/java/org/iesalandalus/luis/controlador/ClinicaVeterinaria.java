package org.iesalandalus.luis.controlador;

import org.iesalandalus.luis.modelo.Cliente;
import org.iesalandalus.luis.modelo.Mascota;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XQueryService;

import java.lang.reflect.InvocationTargetException;

/**
 * Clase que controla la aplicación.
 */
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

            servicio = coleccion.getService(XPathQueryService.class);
            servicio.setProperty("indent", "yes"); // Indenta el resultado de la consulta
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
        if (coleccion != null) {
            try {
                // Obtenemos el servicio de consulta XPath
                XPathQueryService servicio = coleccion.getService(XPathQueryService.class);

                // Preparamos el XML del nuevo cliente
                String clienteXml = "<cliente id=\"" + cliente.getId() + "\">" +
                        "<nombre>" + cliente.getNombre() + "</nombre>" +
                        "<direccion>" + cliente.getDireccion() + "</direccion>" +
                        "<telefono>" + cliente.getTelefono() + "</telefono>" +
                        "<correo>" + cliente.getCorreo() + "</correo>" +
                        "</cliente>";

                // Construimos la consulta XQuery Update para insertar el nuevo cliente
                String consultaXQuery = "xquery version \"3.1\";" +
                        "let $nuevoCliente :=" + clienteXml +
                        "return update insert $nuevoCliente into doc(\"/db/veterinario/clientes.xml\")//clientes";

                // Ejecutamos la consulta XQuery
                ResourceSet resultado = servicio.query(consultaXQuery);
                // Si hay resultado, significa que se ha insertado el cliente.
                if (resultado.getSize() > 0)
                    System.out.println("Cliente insertado correctamente");
                else
                    System.out.println("No se ha podido insertar el cliente");

            } catch (XMLDBException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para modificar un cliente de la base de datos
     */
    public void modificarCliente(Cliente cliente) {
        if (coleccion != null) {
            try {
                XQueryService servicioXQuery = coleccion.getService(XQueryService.class);

                // Preparamos el XML del cliente modificado
                String clienteModificadoXml = "<cliente id=\"" + cliente.getId() + "\">" +
                        "<nombre>" + cliente.getNombre() + "</nombre>" +
                        "<direccion>" + cliente.getDireccion() + "</direccion>" +
                        "<telefono>" + cliente.getTelefono() + "</telefono>" +
                        "<correo>" + cliente.getCorreo() + "</correo>" +
                        "</cliente>";

                // Construimos la consulta XQuery Update para modificar el cliente
                String consultaXQuery = "xquery version \"3.1\"; " +
                        "let $clienteModificado := " + clienteModificadoXml + " " +
                        "return update replace doc(\"/db/veterinario/clientes.xml\")//clientes/cliente[@id=\"" + cliente.getId() + "\"] with $clienteModificado";

                // Ejecutamos la consulta XQuery y la guardamos en un ResourceSet
                ResourceSet resultado = servicioXQuery.query(consultaXQuery);

                // Si hay resultado, significa que se ha modificado el cliente.
                if (resultado.getSize() > 0)
                    System.out.println("Cliente modificado correctamente");

            } catch (XMLDBException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para eliminar un cliente de la base de datos
     */
    public void eliminarCliente(int idCliente) {
        if (coleccion != null) {
            try {
                // Obtenemos el servicio de consulta XQuery
                XQueryService servicioXQuery = coleccion.getService(XQueryService.class);

                // Construimos la consulta XQuery Update para eliminar el cliente
                String consultaXQuery = "xquery version \"3.1\"; " +
                        "update delete doc(\"/db/veterinario/clientes.xml\")//clientes/cliente[@id=\"" + idCliente + "\"]";

                // Ejecutamos la consulta XQuery y la guardamos en un ResourceSet
                ResourceSet resultado = servicioXQuery.query(consultaXQuery);

                // Si hay resultado, significa que se ha eliminado el cliente.
                if (resultado.getSize() > 0)
                    System.out.println("Cliente eliminado correctamente");

            } catch (XMLDBException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para agregar una mascota a la base de datos
     */
    public void agregarMascota(Mascota mascota) {
        if (coleccion != null) {
            try {
                // Obtenemos el servicio de consulta XQuery
                XQueryService servicioXQuery = coleccion.getService(XQueryService.class);

                // Construimos el XML de la nueva mascota
                String mascotaXml = "<mascota id=\"" + mascota.getId() + "\" cliente_id=\"" + mascota.getIdCliente() + "\">" +
                        "<nombre>" + mascota.getNombre() + "</nombre>" +
                        "<tipo>" + mascota.getTipo() + "</tipo>" +
                        "<raza>" + mascota.getRaza() + "</raza>" +
                        "<edad>" + mascota.getEdad() + "</edad>" +
                        "</mascota>";

                // Construimos la consulta XQuery Update para insertar la nueva mascota
                String consultaXQuery = "xquery version \"3.1\"; " +
                        "update insert " + mascotaXml + " into doc(\"/db/veterinario/mascotas.xml\")//mascotas";

                // Ejecutamos la consulta XQuery y la guardamos en un ResourceSet
                ResourceSet resultado = servicioXQuery.query(consultaXQuery);

                // Si hay resultado, significa que se ha insertado la mascota.
                if (resultado.getSize() > 0)
                    System.out.println("Mascota agregada correctamente");

            } catch (XMLDBException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para modificar una mascota de la base de datos
     */
    public void modificarMascota(Mascota mascota) {
        if (coleccion != null) {
            try {
                // Obtenemos el servicio de consulta XQuery
                XQueryService servicioXQuery = coleccion.getService(XQueryService.class);

                // Construimos la consulta XQuery Update para modificar la mascota
                String consultaXQuery = "xquery version \"3.1\"; " +
                        "let $idMascota := " + mascota.getId() + "," +
                        "    $nombre := \"" + mascota.getNombre() + "\"," +
                        "    $tipo := \"" + mascota.getTipo() + "\"," +
                        "    $raza := \"" + mascota.getRaza() + "\"," +
                        "    $edad := " + mascota.getEdad() + " " +
                        "return (" +
                        "    update value doc(\"/db/veterinario/mascotas.xml\")//mascotas/mascota[@id=$idMascota]/nombre with $nombre," +
                        "    update value doc(\"/db/veterinario/mascotas.xml\")//mascotas/mascota[@id=$idMascota]/tipo with $tipo," +
                        "    update value doc(\"/db/veterinario/mascotas.xml\")//mascotas/mascota[@id=$idMascota]/raza with $raza," +
                        "    update value doc(\"/db/veterinario/mascotas.xml\")//mascotas/mascota[@id=$idMascota]/edad with $edad" +
                        ")";

                // Ejecutamos la consulta XQuery y la guardamos en un ResourceSet
                ResourceSet resultado = servicioXQuery.query(consultaXQuery);

                // Si hay resultado, significa que se ha modificado la mascota.
                if (resultado.getSize() > 0)
                    System.out.println("Mascota modificada correctamente");

            } catch (XMLDBException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para eliminar una mascota de la base de datos
     */
    public void eliminarMascota(int idMascota) {
        if (coleccion != null) {
            try {
                // Obtenemos el servicio de consulta XQuery
                XQueryService servicioXQuery = coleccion.getService(XQueryService.class);

                // Construimos la consulta XQuery Update para eliminar la mascota
                String consultaXQuery = "xquery version \"3.1\"; " +
                        "let $idMascota := " + idMascota + " " +
                        "return update delete doc(\"/db/veterinario/mascotas.xml\")//mascotas/mascota[@id=$idMascota]";

                // Ejecutamos la consulta XQuery y la guardamos en un ResourceSet
                ResourceSet resultado = servicioXQuery.query(consultaXQuery);

                // Si hay resultado, significa que se ha eliminado la mascota.
                if (resultado.getSize() > 0)
                    System.out.println("Mascota eliminada correctamente");

            } catch (XMLDBException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para listar clientes de la base de datos
     */
    public ResourceSet listarClientes() {
        ResourceSet resultado = null;

        if (coleccion != null) {
            try {
                // Obtenemos el servicio de consulta XQuery
                XQueryService servicioXQuery = coleccion.getService(XQueryService.class);

                // Construimos la consulta XQuery para listar todos los clientes
                String consultaXQuery = "xquery version \"3.1\"; " +
                        "doc(\"/db/veterinario/clientes.xml\")//clientes/cliente";

                // Ejecutamos la consulta XQuery y la guardamos en un ResourceSet
                resultado = servicioXQuery.query(consultaXQuery);

            } catch (XMLDBException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return resultado;
    }

    /**
     * Método para listar mascotas de la base de datos
     */
    public ResourceSet listarMascotas() {
        ResourceSet resultado = null;

        if (coleccion != null) {
            try {
                // Obtenemos el servicio de consulta XQuery
                XQueryService servicioXQuery = coleccion.getService(XQueryService.class);

                // Construimos la consulta XQuery para listar todas las mascotas
                String consultaXQuery = "xquery version \"3.1\"; " +
                        "doc(\"/db/veterinario/mascotas.xml\")//mascotas/mascota";

                // Ejecutamos la consulta XQuery y la guardamos en un ResourceSet
                resultado = servicioXQuery.query(consultaXQuery);

            } catch (XMLDBException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return resultado;
    }

    /**
     * Método para encontrar mascotas por tipo
     * @param tipo Tipo de mascota
     * @return Lista de mascotas del tipo indicado
     */
    public ResourceSet buscarMascotasPorTipo(String tipo) {
        ResourceSet resultado = null;

        if (coleccion != null) {
            try {
                // Obtenemos el servicio de consulta XQuery
                XQueryService servicioXQuery = coleccion.getService(XQueryService.class);

                // Construimos la consulta XQuery para buscar mascotas por tipo
                String consultaXQuery = "xquery version \"3.1\"; " +
                        "doc(\"/db/veterinario/mascotas.xml\")//mascotas/mascota[tipo=\"" + tipo + "\"]";

                // Ejecutamos la consulta XQuery y la guardamos en un ResourceSet
                resultado = servicioXQuery.query(consultaXQuery);

            } catch (XMLDBException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return resultado;
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
        ResourceSet resultado = null;

        if (coleccion != null) {
            try {
                // Obtenemos el servicio de consulta XQuery
                XQueryService servicioXQuery = coleccion.getService(XQueryService.class);

                // Construimos la consulta XQuery para buscar clientes con varias mascotas
                String consultaXQuery = "xquery version \"3.1\"; " +
                        "for $cliente in doc(\"/db/veterinario/clientes.xml\")//clientes/cliente " +
                        "where count(doc(\"/db/veterinario/mascotas.xml\")//mascotas/mascota[@cliente_id=$cliente/@id]) > 1 " +
                        "return $cliente";

                // Ejecutamos la consulta XQuery y la guardamos en un ResourceSet
                resultado = servicioXQuery.query(consultaXQuery);

            } catch (XMLDBException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return resultado;
    }

    /**
     * Método para obtener el promedio de edad de las mascotas de un cliente
     */
    public double obtenerPromedioEdadMascotasCliente(int idCliente) {
        double promedioEdad = 0.0;

        if (coleccion != null) {
            try {
                // Obtenemos el servicio de consulta XQuery
                XQueryService servicioXQuery = coleccion.getService(XQueryService.class);

                // Construimos la consulta XQuery para obtener el promedio de edad de las mascotas del cliente
                String consultaXQuery = "xquery version \"3.1\"; " +
                        "let $mascotas := doc(\"/db/veterinario/mascotas.xml\")//mascotas/mascota[@cliente_id=\"" + idCliente + "\"] " +
                        "return avg($mascotas/edad)";

                // Ejecutamos la consulta XQuery y la guardamos en un ResourceSet
                ResourceSet resultado = servicioXQuery.query(consultaXQuery);

                // Si hay resultado, extraemos el promedio de edad de las mascotas del cliente
                if (resultado.getSize() > 0) {
                    Resource recurso = resultado.getResource(0); // Obtenemos el primer recurso
                    promedioEdad = Double.parseDouble(recurso.getContent().toString());
                }

            } catch (XMLDBException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return promedioEdad;
    }

}
