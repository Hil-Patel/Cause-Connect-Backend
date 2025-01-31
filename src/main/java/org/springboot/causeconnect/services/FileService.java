package org.springboot.causeconnect.services;

import org.springboot.causeconnect.entities.FileSystem;
import org.springboot.causeconnect.repository.FileSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    @Autowired
    private FileSystemRepository fileSystemRepository;

    // Save files (bankStatement, transcript, profilePic) to the FileSystem entity
    public FileSystem saveFiles(MultipartFile bankStatement, MultipartFile transcript, MultipartFile profilePic) throws IOException {
        FileSystem fileSystem = new FileSystem();
        // Convert MultipartFile to byte[]
        fileSystem.setBankStatement(bankStatement != null ? bankStatement.getBytes() : null);
        fileSystem.setTranscript(transcript != null ? transcript.getBytes() : null);
        fileSystem.setProfilePic(profilePic != null ? profilePic.getBytes() : null);
        return fileSystemRepository.save(fileSystem);
    }
}

