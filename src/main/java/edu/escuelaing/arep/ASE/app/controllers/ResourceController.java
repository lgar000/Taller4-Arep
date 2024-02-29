package edu.escuelaing.arep.ASE.app.controllers;

import edu.escuelaing.arep.ASE.app.annotations.Component;
import edu.escuelaing.arep.ASE.app.annotations.GetMapping;

import java.io.IOException;

/**
 * Clase controladora que maneja las solicitudes para distintas rutas y devuelve contenido HTML, CSS, JS o imágenes.
 */
@Component
public class ResourceController {


    /**
     * Método que maneja la solicitud para la ruta /prueba
     * @return devuelve un contenido Html con un código 200 OK
     */
    @GetMapping(value = "/prueba")
    public static String responseHtml() {
        return  "HTTP/1.1 200 \r\n" +
                "Content-Type:text/html\r\n" +
                "\r\n"+
                "Hola, esta es una prueba!";
    }

    /**
     * Método que maneja la solicitud para la ruta /cliente
     * @return devuelve un contenido Html
     * @throws IOException maneja las excepciones en caso de que ocurra un error de entrada/salida al manejar la respuesta HTTP
     */
    @GetMapping(value = "/cliente")
    public static String clientHtml() throws IOException {
        ContentProvider contentProvider = ContentProvider.getInstance();
        contentProvider.setType("text/html");
        contentProvider.setFile("cliente.html");
        return contentProvider.file();
    }


    /**
     * Método que maneja la solicitud para la ruta /style.css
     * @return devuelve un contenido de tipo css
     * @throws IOException maneja las excepciones en caso de que ocurra un error de entrada/salida al manejar la respuesta HTTP
     */
    @GetMapping(value = "/style.css")
    public static String css() throws IOException {
        ContentProvider contentProvider = ContentProvider.getInstance();
        contentProvider.setType("text/css");
        contentProvider.setFile("style.css");
        return contentProvider.file();
    }

    /**
     * Método que maneja la solicitud para la ruta /jsExample.js
     * @return devuelve un contenido de tipo js
     * @throws IOException maneja las excepciones en caso de que ocurra un error de entrada/salida al manejar la respuesta HTTP
     */
    @GetMapping(value = "/jsExample.js")
    public static String js() throws IOException {
        ContentProvider contentProvider = ContentProvider.getInstance();
        contentProvider.setType("text/javascript");
        contentProvider.setFile("jsExample.js");
        return contentProvider.file();
    }

    /**
     * Método que maneja la solicitud para la ruta /icon
     * @return devuelve un contenido de tipo imagen
     * @throws IOException maneja las excepciones en caso de que ocurra un error de entrada/salida al manejar la respuesta HTTP
     */
    @GetMapping(value = "/icon")
    public static String image() throws IOException {
        ContentProvider contentProvider1 = ContentProvider.getInstance();
        contentProvider1.setType("image/png");
        contentProvider1.setFile("icon.png");
        return contentProvider1.file();
    }

    /**
     * Método que maneja la solicitud para la ruta /chocolateDomeCake
     * @return devuelve un contenido de tipo imagen
     * @throws IOException maneja las excepciones en caso de que ocurra un error de entrada/salida al manejar la respuesta HTTP
     */
    @GetMapping(value = "/chocolateDomeCake")
    public static String imageTwo() throws IOException {
        ContentProvider contentProvider1 = ContentProvider.getInstance();
        contentProvider1.setType("image/png");
        contentProvider1.setFile("chocolate-dome-cake.png");
        return contentProvider1.file();
    }

    /**
     * Método que maneja la solicitud para la ruta /page, para retornar una página que tiene estilo e imagenes
     * @return devuelve un contenido de tipo html
     * @throws IOException maneja las excepciones en caso de que ocurra un error de entrada/salida al manejar la respuesta HTTP
     */
    @GetMapping(value = "/page")
    public static String page() throws IOException {

        ContentProvider contentProvider = ContentProvider.getInstance();

        String cssContent = contentProvider.loadFile("style.css");
        String imageUrl = contentProvider.loadImage("/icon.png");
        String imageUrlTwo = contentProvider.loadImage("/chocolate-dome-cake.png");


        String htmlContent = contentProvider.loadFile("contact.html");


        htmlContent = htmlContent.replace("href=\"./style.css\"", "style=\"" + cssContent + "\"");
        htmlContent = htmlContent.replace("src=\"./icon.png\"", "src=\"data:image/png;base64," + imageUrl + "\"");
        htmlContent = htmlContent.replace("src=\"./chocolate-dome-cake.png\"", "src=\"data:image/png;base64," + imageUrlTwo + "\"");


        return "HTTP/1.1 200 \r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                htmlContent;
    }
}
