package com.jardel.uploaddowloadfile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.micrometer.common.util.StringUtils;

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

    // criação do endpoint de upload
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // StringUtils para enocntrar o nome do arquivo que será salvo.
        //cleanPath é para "normalizar", desconsiderar, os caracteres especiais no nome do arquivo.

        try {
            Path targetLocation = fileStorageLocation.resolve(fileName);
            file.transferTo(targetLocation);
      
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/download/")
                .path(fileName)
                .toUriString();
      
            return ResponseEntity.ok("File uploaded successfully. Download link: " + fileDownloadUri);
          } catch (IOException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body("File upload failed.");
          }
    }




    
}
