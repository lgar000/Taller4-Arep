package edu.escuelaing.arep.ASE.app;


import edu.escuelaing.arep.ASE.app.annotations.Component;
import edu.escuelaing.arep.ASE.app.annotations.GetMapping;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;

public class HttpServer {


    private static HttpServer _instance = new HttpServer();
    public static HttpServer getInstance(){
        return _instance;
    }

    private static OutputStream outputStream = null;

    public static Map<String, Method> endpointMethods = new HashMap<>();


    public static void run(String[] args) throws IOException, URISyntaxException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {

        String root = "edu.escuelaing.arep.ASE.app.controllers";
        Set<Class<?>> classSet = getClasses(root);
        for(Class<?> classTaller : classSet){
            boolean hasMyAnnotation = classTaller.isAnnotationPresent(Component.class);
            if(hasMyAnnotation){
                String className = classTaller.getName();
                Class<?> c = Class.forName(className);
                Method[] cMethods = c.getMethods();
                for(Method method : cMethods){
                    if(method.isAnnotationPresent(GetMapping.class)){
                        String key = method.getAnnotation(GetMapping.class).value();
                        endpointMethods.put(key, method);
                        System.out.println("key: " + key + " value: " + method);
                    }
                }
            }
        }

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            outputLine=null;
            boolean firstLine = true;
            String uriStr = "";
            String method="";
            String path;
            while ((inputLine = in.readLine()) != null) {
                if (firstLine) {
                    uriStr = inputLine.split(" ")[1];
                    method = inputLine.split(" ")[0];
                    firstLine = false;
                }
                URI file = new URI(uriStr);

                System.out.println("inputlineAntesDelIf"+ inputLine);
                System.out.println("uriStr"+ uriStr);
                if(uriStr.startsWith("/hello?")){
                    System.out.println("entro en el hello");
                    String movieTitle=getName(uriStr);
                    String movieData=searchDataMovie(movieTitle);
                    outputLine=informationFormat(movieData);
                    System.out.println(outputLine);
                }else{
                    if(method.equalsIgnoreCase("GET")){
                            if(!Objects.equals(uriStr, "/favicon.ico")){
                                if(endpointMethods.containsKey(uriStr)){
                                    outputLine = (String) endpointMethods.get(uriStr).invoke(null);
                                }else{
                                    outputLine = httpError();
                                }
                            }
                        }
                }

                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }



            out.println(outputLine);

            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    private static String httpError() {
        String outputLine = "HTTP/1.1 400 Not Found\r\n"
                + "Content-Type:text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Error Not found</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <h1>Resource not found </h1>\n"
                + "    </body>\n";
        return outputLine;

    }

    public static String getName(String uri){
        String name = uri.replace("/hello?name=", "");
        return name;
    }

    public  static String searchDataMovie(String movieTitle) throws IOException {
        String movieData="";
        if(Cache.movieInCache(movieTitle)){
            movieData=Cache.getMovieInCache(movieTitle);
            return movieData;
        }else{
            movieData=HttpConnection.HttpConnection(movieTitle);
            Cache.saveInCache(movieTitle,movieData);
            return movieData;
        }
    }

    /**
     *Método para ordenar la información de la pelicula
     * @param movieData json con la información de la pelicula consultada
     * @return conjunto de divs con los datos
     * @throws JSONException
     */
    public static String informationFormat( String movieData) throws JSONException {
        HashMap<String, String> movieDetails= new HashMap<>();
        JSONArray jsonArray= new JSONArray(movieData);
        Iterator<Object> iterator=jsonArray.iterator();
        while (iterator.hasNext()){
            JSONObject jsonObject= (JSONObject) iterator.next();
            for(String key : jsonObject.keySet()){
                movieDetails.put(key, jsonObject.get(key).toString());
            }
        }
        String data="";
        for(String key: movieDetails.keySet()){
            data+="<div>"+key+": "+ movieDetails.get(key)+"</div>";
        }
        return "HTTP/1.1 200 OK\r\n"
                + "Content-type: text/html\r\n"
                + "\r\n"
                +data;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * metodo que busca los diferentes archivos .class en base a una ruta de un posible
     * directorio, asegurandose que este lo sea, una vez encuentra los archivos .class
     * los concatena con el path suministrado y los agrega a un set que sera retornado
     * @param raiz path de la carpeta que se desea analizar
     * @return set de todas las clases en un directorio especifico
     */
    private static Set<Class<?>> getClasses(String raiz){
        Set<Class<?>> classes = new HashSet<>();
        String path = raiz.replace(".", "/");
        try{
            File file = new File(getClassPaths() + "/" + path);
            if(file.exists() && file.isDirectory()){
                for(File classFile : file.listFiles()){
                    if(classFile.isFile() && classFile.getName().endsWith(".class")){
                        String className = raiz + "." + classFile.getName().replace(".class", "");
                        Class<?> clazz = Class.forName(className);
                        classes.add(clazz);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return classes;
    }


    /**
     * metodo que busca la ubicacion de la carpeta target
     * @return path de la carpeta target
     */
    private static String getClassPaths() {
        String classPath = System.getProperty("java.class.path");
        String[] classPaths = classPath.split(System.getProperty("path.separator"));
        return classPaths[0];
    }

    /**
     * Se encarga de validar el tipo de archivo(text/html, text/js, text/css, image/jpg, o image/png).)
     * para construir y retornar el contenido que el cliente solicita.
     * @param path es un String que representa la dirección del archivo
     * @param clientSocket el Socket de la conexión del cliente
     * @return un String con la respuesta solicitada si es js, html o css, si es de tipo imagen
     * carga la imagen desde el archivo y la envia como parte de la respuesta
     * @throws IOException maneja los error producidos en la entrada/salida
     */
    public static String httpRequestClient(String path, Socket clientSocket ) throws IOException {

        String extension = "";

        if (path.endsWith(".html")) {
            extension = "text/html";
        } else if (path.endsWith(".js")) {
            extension = "text/javascript";
        } else if (path.endsWith(".css")) {
            extension = "text/css";
        } else if (path.endsWith(".png")) {
            extension = "image/png";
        }else if (path.endsWith(".jpg")) {
            extension = "image/jpg";
        }

        String outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type:" + extension + "\r\n"
                + "\r\n";
        Path page = Paths.get("target/classes/public" + path);
        if ( extension.startsWith("text/") ) {
            Charset charset = StandardCharsets.UTF_8;
            try (BufferedReader reader = Files.newBufferedReader(page, charset)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    outputLine += line;
                }
            }
        } else if  (extension.startsWith("image/")) {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: " + extension);
            out.println();
            BufferedImage image = ImageIO.read(new File("target/classes/public" + path));
            ImageIO.write(image, "PNG", clientSocket.getOutputStream());
        }else {
            outputLine = httpError(); // Not Found
        }


        return outputLine;
    }
}