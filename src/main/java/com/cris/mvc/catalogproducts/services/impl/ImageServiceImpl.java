package com.cris.mvc.catalogproducts.services.impl;

import com.cris.mvc.catalogproducts.services.IImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements IImageService {

    private static final String IMG_REPO = "src/main/resources/static/images";
    @Override
    public String saveImage(MultipartFile file) {
        try {
            Files.createDirectories(Paths.get(IMG_REPO));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileName = file.getOriginalFilename();
        Path destinationFile = Path.of(IMG_REPO + File.separator + fileName);

        try{
            file.transferTo(destinationFile);
            return "images/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
