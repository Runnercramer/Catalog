package com.cris.mvc.catalogproducts.services;

import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    String saveImage(MultipartFile file);
}
