package edu.escuelaing.arep.ASE.app.controllers;

import edu.escuelaing.arep.ASE.app.annotations.Component;
import edu.escuelaing.arep.ASE.app.annotations.GetMapping;

import java.io.IOException;

@Component
public class ResourceController {



    @GetMapping(value = "/prueba")
    public static String index() {
        return  "HTTP/1.1 200 \r\n" +
                "Content-Type:text/html\r\n" +
                "\r\n"+
                "Hola, esta es una prueba!";
    }


    @GetMapping(value = "/cliente")
    public static String web() throws IOException {
        ContentProvider contentProvider = ContentProvider.getInstance();
        contentProvider.setType("text/html");
        contentProvider.setFile("cliente.html");
        return contentProvider.file();
    }




    @GetMapping(value = "/style.css")
    public static String css() throws IOException {
        ContentProvider contentProvider = ContentProvider.getInstance();
        contentProvider.setType("text/css");
        contentProvider.setFile("style.css");
        return contentProvider.file();
    }


    @GetMapping(value = "/jsExample.js")
    public static String js() throws IOException {
        ContentProvider contentProvider = ContentProvider.getInstance();
        contentProvider.setType("text/javascript");
        contentProvider.setFile("jsExample.js");
        return contentProvider.file();
    }

    @GetMapping(value = "/page")
    public static String page() throws IOException {
        ContentProvider contentProvider = ContentProvider.getInstance();
       // contentProvider.setType("text/html");

      /***  // Cargar el contenido del archivo HTML
        String htmlContent = contentProvider.loadFile("contact.html");

        // Cargar el contenido del archivo de estilo (CSS)
        String cssContent = contentProvider.loadFile("style.css");

        // Cargar el contenido del archivo de imagen (por ejemplo, imagen.png)
        String imageUrl = "src/main/resources/public/icon.png";
        String imageData = contentProvider.loadImage(imageUrl);

        // Reemplazar marcadores de posición en el HTML con el contenido de la imagen y el estilo
        htmlContent = htmlContent.replace("{{CSS_CONTENT}}", "<style>" + cssContent + "</style>");
        htmlContent = htmlContent.replace("{{IMAGE_SRC}}", "data:image/png;base64," + imageData);

        // Devolver el HTML modificado
        return "HTTP/1.1 200 \r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                htmlContent;**/
        // Cargar el contenido del archivo de estilo (CSS)
        String cssContent = contentProvider.loadFile("style.css");

        // Cargar el contenido del archivo de imagen (por ejemplo, imagen.png)
        String imageUrl = "src/main/resources/public/icon.png";
        String imageData = contentProvider.loadImage(imageUrl);
        String htmlContent = contentProvider.loadFile("contact.html");

        htmlContent = htmlContent.replace("src/main/resources/public/style.css", cssContent);
        htmlContent = htmlContent.replace("src/main/resources/public/icon.png", "data:image/png;base64," + imageData);

        // Devolver el HTML modificado
        return "HTTP/1.1 200 \r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                htmlContent;
    }

}
