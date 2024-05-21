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

        if (file.getSize() > 1000 * 1024) {
            throw new Exception("El archivo es demasiado grande");

        } else if (!file.isEmpty()) {
            url = awsService.uploadImage(file);

        }

        return url;
    }
}
