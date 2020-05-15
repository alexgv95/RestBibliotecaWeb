/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestServices;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;

/**
 * Jersey REST client generated for REST resource:ServicioBiblioteca
 * [biblioteca]<br>
 * USAGE:
 * <pre>
 *        ServicioBiblioteca client = new ServicioBiblioteca();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Alex
 */
public class ServicioBiblioteca {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/RestBibliotecaServer/webresources";

    public ServicioBiblioteca() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("biblioteca");
    }

    public String importarLibro(String nombreFichero, String contenidoFichero, String token) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (nombreFichero != null) {
            resource = resource.queryParam("nombreFichero", nombreFichero);
        }
        if (contenidoFichero != null) {
            resource = resource.queryParam("contenidoFichero", contenidoFichero);
        }
        resource = resource.path("libro/import");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).header(HttpHeaders.AUTHORIZATION, token).get(String.class);
    }

    public String exportarLibro(String numLibro, String nombreFichero, String token) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (nombreFichero != null) {
            resource = resource.queryParam("nombreFichero", nombreFichero);
        }
        resource = resource.path(java.text.MessageFormat.format("libro/{0}/export", new Object[]{numLibro}));
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).header(HttpHeaders.AUTHORIZATION, token).get(String.class);
    }

    public <T> T importarBiblioteca(Class<T> responseType, String nombreFichero, String contenidoFichero, String token) {
        WebTarget resource = webTarget;
        resource = resource.path("import");
        resource = resource.queryParam("nombreFichero", nombreFichero).queryParam("contenidoFichero", contenidoFichero);
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).header(HttpHeaders.AUTHORIZATION, token).get(responseType);
    }

    public String exportarBiblioteca(String nombreFichero, String token) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("export");
        resource = resource.queryParam("nombreFichero", nombreFichero);
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).header(HttpHeaders.AUTHORIZATION, token).get(String.class);
    }

    public String getLibrosTexto(String token) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("libro/texto");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).header(HttpHeaders.AUTHORIZATION, token).get(String.class);
    }

    public <T> T putLibro(Object requestEntity, Class<T> responseType, String numLibro, String token) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("libro/{0}", new Object[]{numLibro})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).header(HttpHeaders.AUTHORIZATION, token).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), responseType);
    }

    public <T> T deleteLibro(Class<T> responseType, String numLibro, String token) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("libro/{0}", new Object[]{numLibro})).request().header(HttpHeaders.AUTHORIZATION, token).delete(responseType);
    }

    public <T> T getLibro(Class<T> responseType, String numLibro, String token) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("libro/{0}", new Object[]{numLibro}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).header(HttpHeaders.AUTHORIZATION, token).get(responseType);
    }

    public <T> T postLibro(Object requestEntity, Class<T> responseType, String token) throws ClientErrorException {
        return webTarget.path("libro").request(javax.ws.rs.core.MediaType.APPLICATION_XML).header(HttpHeaders.AUTHORIZATION, token).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), responseType);
    }

    public <T> T postBiblioteca(Object requestEntity, Class<T> responseType, String token) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).header(HttpHeaders.AUTHORIZATION, token).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), responseType);
    }

    public <T> T getBiblioteca(Class<T> responseType, String token) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).header(HttpHeaders.AUTHORIZATION, token).get(responseType);
    }

    public <T> T getLibros(Class<T> responseType, String token) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("libro");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).header(HttpHeaders.AUTHORIZATION, token).get(responseType);
    }

    public void close() {
        client.close();
    }

}
