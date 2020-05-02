package lk.apiit.eirlss.dmv.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LicenseService {
    private final FileService fileService;
    @Value("${dmv.csv.filename}")
    private String filename;

    @Autowired
    public LicenseService(FileService fileService) {
        this.fileService = fileService;
    }

    public ResponseEntity<?> getCSV() {
        return fileService.downloadFile(filename);
    }
}
