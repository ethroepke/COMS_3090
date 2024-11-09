package coms309.controller;

import coms309.image.Image;
import coms309.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

@RestController
@EnableWebSocketMessageBroker
@RequestMapping("/images")
public class ImageController {

    // Directory path set in application.properties
    @Value("${image.dir}")
    private String directory;

    @Autowired
    private ImageRepository imageRepository;

    /**
     * Retrieves an image by its ID.
     * @param id The ID of the image.
     * @return The image file in byte array format.
     **/
    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
        Optional<Image> imageOpt = imageRepository.findById(id);
        if (imageOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Image image = imageOpt.get();
        File imageFile = new File(image.getFilePath());

        if (!imageFile.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        try {
            byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
            return ResponseEntity.ok(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Handles the upload of an image file.
     * @param imageFile The uploaded file.
     * @return A success or failure message.
     **/
    @PostMapping
    public ResponseEntity<String> handleFileUpload(@RequestParam("image") MultipartFile imageFile) {
        // Validate file type
        if (!isImageFile(imageFile)) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body("Only JPEG or PNG files are supported.");
        }

        // Save file to disk
        try {
            File destinationFile = new File(directory + File.separator + imageFile.getOriginalFilename());
            imageFile.transferTo(destinationFile);

            // Save image record in database
            Image image = new Image(destinationFile.getAbsolutePath());
            imageRepository.save(image);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("File uploaded successfully: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

    /**
     * Converts an image file path to a MultipartFile-like InputStreamResource.
     * @param imagePath The path to the image file.
     * @return The image as an InputStreamResource.
     * @throws IOException if the file cannot be read.
     **/
    public InputStreamResource loadImageAsResource(String imagePath) throws IOException {
        File file = new File(imagePath);
        InputStream input = new FileInputStream(file);
        return new InputStreamResource(input);
    }

    /**
     * Checks if the uploaded file is an image.
     * @param file The file to check.
     * @return True if the file is an image, false otherwise.
     **/
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.equals(MediaType.IMAGE_JPEG_VALUE) ||
                contentType.equals(MediaType.IMAGE_PNG_VALUE));
    }
}