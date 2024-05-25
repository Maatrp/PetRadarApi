package com.api.petradar.placeimages;

import com.api.petradar.aws.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadPlaceImageService {

    @Autowired
    private AWSS3Service awsService;

    public String uploadImage(MultipartFile file) throws Exception {
        String url = "";
        double maxSize = 15 * 1024 * 1024; // 5MB

        // Verificar el tamaño del archivo
        if (file.getSize() > maxSize) {
            throw new Exception("El archivo es demasiado grande");
        }

        // Subir la imagen si no está vacía
        if (!file.isEmpty()) {
            url = awsService.uploadImage(file);
        }

        return url;
    }

}
