package org.springboot.causeconnect.repository;

import org.springboot.causeconnect.entities.FileSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface FileSystemRepository extends JpaRepository<FileSystem, Integer>{

}
