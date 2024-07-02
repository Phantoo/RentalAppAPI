package com.openclassrooms.RentalAppAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.openclassrooms.RentalAppAPI.services.FileStorageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Controller
public class FilesController 
{
    @Autowired
    private FileStorageService storageService;

    // @PostMapping("/upload")
    // public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) 
    // {
    //     String message = "";
    //     try {
    //         storageService.save(file);
    //         message = "Uploaded the file successfully: " + file.getOriginalFilename();
    //         return ResponseEntity.status(HttpStatus.OK).body(message);
    //     } 
    //     catch (Exception e) {
    //         message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
    //         return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
    //     }
    // }

    // @GetMapping("/files")
    // public ResponseEntity<List<FileInfo>> getFiles() 
    // {
    //     List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
    //     String filename = path.getFileName().toString();
    //     String url = MvcUriComponentsBuilder
    //         .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

    //     return new FileInfo(filename, url);
    //     }).collect(Collectors.toList());

    //     return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    // }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    @Operation(responses = {
        @ApiResponse(responseCode = "200", 
                    description = "Fetch image with the specified filename", 
                    content = @Content(mediaType = "image/png")),
        @ApiResponse(responseCode = "401",
                    description = "Authentication failure",
                    content = @Content(mediaType = "application/json", 
                        schema = @Schema(type = "object")))
    })
    public ResponseEntity<Resource> getFile(@PathVariable String filename) 
    {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
