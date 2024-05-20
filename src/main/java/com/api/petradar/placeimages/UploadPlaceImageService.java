package com.api.petradar.placeimages;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public class UploadPlaceImageService {

    public String uploadImage(MultipartFile file) throws Exception {
        if(file.isEmpty() || !Objects.equals(file.getContentType(), "lo que sea")) {
            throw new Exception("El archivo no es una imagen válida");
        } else if(file.getSize() > 15000000) {
            throw new Exception("El archivo es demasiado grande");
        } else if(!file.isEmpty()) {

            // string url=awsService.Load(file, "petradar-images");

        }

        return "";
    }
}
