## TALLER DE ARQUITECTURAS DE SERVIDORES DE APLICACIONES, META PROTOCOLOS DE OBJETOS, PATRÓN IOC, REFLEXIÓN

Para este taller se construyó un servidor Web (tipo Apache) en Java. El servidor debe ser capaz de entregar páginas html e imágenes tipo PNG. Igualmente el servidor debe proporcionar un framework IoC para la construcción de aplicaciones web a partir de POJOS. Usando el servidor se debe construir una aplicación web de ejemplo. El servidor debe atender múltiples solicitudes no concurrentes.

### Prerrequisitos

- Java
- Maven
- Git


### Instalación

Para hacer uso del proyecto clone el repositorio usando el siguiente comando

```
git clone https://github.com/lgar000/Taller4-Arep.git
```

Ubiquese en la carpeta en la cual clono el repositorio. A continuación
acceda a la carpeta principal del proyecto mediante el siguiente comando

```
cd Taller4-Arep
```

Para empaquetar 

```
mvn package
```

Abra el proyecto en su IDE de preferencia y jecute

```
java -cp .\target\classes\ edu.escuelaing.arep.ASE.app.App
```

O ejecutelo desde su IDE 

## Pruebas 

Para probar el funcionamiento, una vez tenga descargado el proyecto y ejecutado correctamente proceda a ingresar a un navegador, donde deberá ingresar http://localhost:35000/archivoSolicitado, donde las extensiones pueden ser (js, html, css, png). Tenga en cuenta que os archivos a solicitar se encuentran definidos en la clase ResourceController.


Si desea obtener un recurso de tipo html, debe ingresar en el navegador la siguiente ruta localhost:35000/cliente


![recursoHttp](https://github.com/lgar000/Taller4-Arep/blob/main/Imagenes/recursoHtml.png)


Si desea obtener un recurso de tipo css, debe ingresar en el navegador la siguiente ruta localhost:35000/style.css


![recursoCss](https://github.com/lgar000/Taller4-Arep/blob/main/Imagenes/recursoCss.png)


Si desea obtener un recurso de tipo js, debe ingresar en el navegador la siguiente ruta localhost:35000/jsExample,js


![recursoJs](https://github.com/lgar000/Taller4-Arep/blob/main/Imagenes/recursoJs.png)


Si ingresa la ruta: localhost:35000/prueba, obtendrá un html cuyo resultado será


![prueba](https://github.com/lgar000/Taller4-Arep/blob/main/Imagenes/prueba.png)

Puede obtener imagenes mediante las siguientes rutas:

localhost:35000/icon

![icon](https://github.com/lgar000/Taller4-Arep/blob/main/Imagenes/icon.png)

localhost:35000/chocolateDomeCake

![prueba](https://github.com/lgar000/Taller4-Arep/blob/main/Imagenes/chocolateDomeCake.png)

Además puede obtener páginas que contengan imagenes y estilo en la ruta: localhost:35000/page

![page](https://github.com/lgar000/Taller4-Arep/blob/main/Imagenes/page.png)

## Diseño

Tenemos un servidor que responde solicitudes de tipo GET para páginas html, js, css e imágenes de tipo png. Además, el servidor debe proporcionar un framework IoC para la construcción de aplicaciones web a partir de POJOS. Para lograrlo tenemos la carpeta annotations, que contiene las anotaciones Component
y GetMapping, estas fueron definidas mediante @interface, lo que nos permite definir anotaciones personalizadas.  En component tenemos las siguientes anotaciones: @Retention(RetentionPolicy.RUNTIME), significa que Component estará disponible durante el tiempo de ejecución y permitirá inspeccionar las clases y métodos anotados durante la ejecución del programa. También tiene @Target(ElementType.TYPE), nos indica que se puede aplicar a clases, interfaces y otras anotaciones. Esta se utiliza para marcar clases como componentes. Por otro lado tenemos a GetMapping, en el que se define String value(), que se usa para especificar la ruta asociada a un método.

También tenemos el componente ResourceController, que es la encargada de manejar las solicitudes para distintas rutas y devuelve contenido HTML, CSS, JS o imágenes. Cada método definido dentro de ResourceController, tiene la anotación @GetMapping, donde especificamos la ruta que manejara el método. Para apoyar la funcionalidad del componente tenemos la clase ContentProvider, que proporciona funciones para manejar la generación de respuestas HTTP y cargar contenido desde el directorio de recursos, incluyendo archivos e imágenes.

Adicionalmente en el servidor se buscan  las clases que se encuentran en el paquete: edu.escuelaing.arep.ASE.app.controllers y están anotadas con @Component, y para cada una de esas clases, busca métodos anotados con @GetMapping. Luego, agrega estos métodos a un mapa (endpointMethods) donde la clave es el valor de la anotación @GetMapping (la ruta del mapeo) y el valor es el método correspondiente.

## Construido Con

* [Java 11](https://www.oracle.com/co/java/technologies/javase/jdk11-archive-downloads.html) - Lenguaje de programación y desarrollo
* [Html](https://developer.mozilla.org/es/docs/Web/HTML) - Lenguaje de marcado para la elaboración de páginas web
* [JavaScript](https://developer.mozilla.org/es/docs/Web/CSS) -JavaScript es un lenguaje de programación interpretado
* [Maven](https://maven.apache.org/) - Gestión de dependencias
* [Intellij](https://www.jetbrains.com/es-es/idea/) - Entorno de desarrollo integrado para el desarrollo de programas informáticos
* [Git](https://rometools.github.io/rome/) - Sistema de control de versiones distribuido


## Autor

* **Laura García** - [lgar000](https://github.com/lgar000)

## Licencia

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
