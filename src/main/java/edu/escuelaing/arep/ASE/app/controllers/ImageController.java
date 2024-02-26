package edu.escuelaing.arep.ASE.app.controllers;

import edu.escuelaing.arep.ASE.app.annotations.Component;
import edu.escuelaing.arep.ASE.app.annotations.GetMapping;

import java.io.IOException;
@Component
public class ImageController {

    @GetMapping(value = "/icon")
    public static String imge() throws IOException {
       ContentProvider contentProvider1 = ContentProvider.getInstance();
        contentProvider1.setType("image/png");
        contentProvider1.setFile("icon.png");
        return contentProvider1.file();
    }
}
