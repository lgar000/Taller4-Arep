package edu.escuelaing.arep.ASE.app.controllers;

import edu.escuelaing.arep.ASE.app.HttpServer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Clase que mediante funciones permite manejar la generación de respuestas HTTP
 * y cargar contenido desde el directorio de recursos, incluyendo archivos e imágenes.
 */
public class ContentProvider {

    private static ContentProvider _instance = new ContentProvider();

    public static ContentProvider getInstance(){
        return _instance;
    }

    private String type;
    private String file;


    /**
     * Define la cabecera HTTP para una respuesta con código 200 (OK).
     * @return  Una cadena de la cabecera HTTP con código 200 y el tipo de contenido.
     */
    public String head() {
        return "HTTP/1.1 200 \r\n" +
                "Content-Type:"+  getType() +"\r\n" +
                "\r\n";
    }

    /**
     * Obtiene el contenido de un archivo HTML y lo devuelve como una cadena de texto.
     * @return devuelve una cadena del contenido del archivo HTML.
     */
    public String bodyHtml() {
        byte[] file;
        try{
            file = Files.readAllBytes(Paths.get("src/main/resources/public/" + getFile()));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return new String(file);
    }

    /**
     * Obtiene el nombre del archivo
     * @return devuelve el nombre del archivo
     */
    public String getFile(){
        return file;
    }

    /**
     * Establece el tipo del archivo
     * @param t una cadena que representa el tipo del archivo
     */
    public void setType(String t){
        this.type = t;
    }

    /**
     *Obtiene el tipo del archivo
     * @return el tipo del archivo
     */
    public String getType(){
        return type;
    }

    /**
     * Establece el nombre del archivo
     * @param file una cadena con el nombre del archivo
     */
    public void setFile(String file){
        this.file = file;
    }

    /**
     * Genera y devuelve la respuesta HTTP para la solicitud de una imagen en formato PNG.
     * @return Una cadena de la respuesta y el contenido de la imagen
     * @throws IOException maneja las excepciones en caso de que ocurra un error de entrada/salida al manejar la respuesta HTTP
     */
    public String bodyImg() throws IOException {
        String response = "HTTP/1.1 200 \r\n" +
                "Content-Type: image/png \r\n" +
                "\r\n";
        BufferedImage bufferedImage = ImageIO.read(new File("src/main/resources/public/"+ getFile()));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpServer server = HttpServer.getInstance();
        DataOutputStream dataOutputStream = new DataOutputStream(server.getOutputStream());
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        dataOutputStream.writeBytes(response);
        dataOutputStream.write(byteArrayOutputStream.toByteArray());
        return response;
    }

    /**
     * Genera y devuelve la respuesta completa para una solicitud HTTP, incluyendo la cabecera y el cuerpo.
     * @return Una cadena de la respuesta HTTP completa.
     * @throws IOException maneja las excepciones en caso de que ocurra un error de entrada/salida al manejar la respuesta HTTP
     */
    public String file() throws IOException {
        if(type.startsWith("text")){
            return head() + bodyHtml();
        }else {
            return bodyImg();
        }
    }

    /**
     * Lee el contenido de un archivo ubicado en el directorio de recursos y lo devuelve como una cadena de texto.
     * @param fileName una cadena con el nombre del archivo a leer
     * @return una cadena del archivo a leer
     * @throws IOException maneja las excepciones en caso de que ocurra un error de entrada/salida al manejar la respuesta HTTP
     */
    public String loadFile(String fileName) throws IOException {
        Path filePath = Paths.get("src/main/resources/public/", fileName);
        byte[] fileBytes = Files.readAllBytes(filePath);
        return new String(fileBytes);
    }

    /**
     * Lee una imagen ubicada en el directorio de recursos, la convierte a formato base64
     * y devuelve la representación en cadena de la imagen.
     * @param fileName nombre de la imagen
     * @return cadena que representa la imagen en formato base64.
     * @throws IOException maneja las excepciones en caso de que ocurra un error de entrada/salida al manejar la respuesta HTTP
     */
  public String loadImage(String fileName) throws IOException {
      Path imagePath = Paths.get("src/main/resources/public", fileName);
      byte[] imageBytes = Files.readAllBytes(imagePath);
      System.out.println("base 64 imagenes"+  Base64.getEncoder().encodeToString(imageBytes));
      return Base64.getEncoder().encodeToString(imageBytes);
  }


}
