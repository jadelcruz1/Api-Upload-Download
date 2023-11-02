package com.jardel.uploaddowloadfile;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // não é @RestController pois não se trata de uma api rest
@RequestMapping("/api/files")
public class FileStorangeController {
    // aqui começa a trabalhar com a api de arquivo do java
    private final Path fileStorageLocation;

    public FileStorangeController(FileStorangeProperties fileStorangeProperties) {
        this.fileStorageLocation = Paths.get(fileStorangeProperties.getUploadDir()).toAbsolutePath().normalize();

        //neste construtor passei o caminho relativo que está no application properties para um absoluto "toAbsolutePath()"
        // o objeto "normalize()" foi usado para desconsiderar caracteres no nome do arquivo.

    }


    
}
