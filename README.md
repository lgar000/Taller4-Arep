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

Para empaquetar el proyecto ejecute

```
mvn package
```

## Pruebas 

Para probar el funcionamiento, una vez tenga descargado el proyecto y ejecutado correctamente proceda a ingresar a un navergador, donde debera ingresar http://localhost:35000/archivoSolicitado.extension, donde las extensiones pueden ser (js, html, css, png). Los archivos a solicitar se encuentra dentro del proyecto.

## Diseño



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
