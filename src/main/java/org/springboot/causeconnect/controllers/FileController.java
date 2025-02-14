package org.springboot.causeconnect.controllers;

import org.springboot.causeconnect.entities.NGO;
import org.springboot.causeconnect.repository.NGORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    @Autowired
    private NGORepository ngoRepository;

    @GetMapping("/{ngoId}/{fileType}")
    public ResponseEntity<byte[]> getFile(@PathVariable int ngoId, @PathVariable String fileType) {
        NGO ngo = ngoRepository.findById(ngoId).orElseThrow(() -> new RuntimeException("NGO not found"));

        byte[] fileData = null;
        String contentType = null;

        switch (fileType) {
            case "bankStatement":
                fileData = ngo.getFileSystem().getBankStatement();
                contentType = "application/pdf";
                break;
            case "transcript":
                fileData = ngo.getFileSystem().getTranscript();
                contentType = "application/pdf";
                break;
            case "profilePic":
                fileData = ngo.getFileSystem().getProfilePic();
                contentType = "image/jpg";
                break;
            default:
                throw new RuntimeException("Invalid file type");
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(fileData);
    }
}
