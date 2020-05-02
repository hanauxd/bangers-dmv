package lk.apiit.eirlss.dmv.services;

import lk.apiit.eirlss.dmv.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private final String workingDirectory = System.getProperty("user.dir");
    @Value("${dmv.csv.storage}")
    private String storage;

    public ResponseEntity<?> downloadFile(String filename) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(getMimeType(filename)))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"".concat(filename))
                .body(getResource(filename));
    }

    private String getMimeType(String filename) {
        try {
            return Files.probeContentType(getPath(filename));
        } catch (IOException e) {
            throw new CustomException("File not found.", HttpStatus.NOT_FOUND);
        }
    }

    private UrlResource getResource(String filename) {
        try {
            Path path = getPath(filename);
            UrlResource resource = new UrlResource(path.toUri());
            if (!resource.exists()) throw new CustomException("File not found.", HttpStatus.NOT_FOUND);
            return resource;
        } catch (MalformedURLException e) {
            throw new CustomException("Failed to get the file path.", HttpStatus.BAD_REQUEST);
        }
    }

    public Path getPath(String filename) {
        String path = workingDirectory.concat(File.separator).concat(storage).concat(File.separator).concat(filename);
        return Paths.get(path);
    }
}
