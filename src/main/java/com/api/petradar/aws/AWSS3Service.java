package com.api.petradar.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Servicio que maneja la lógica para cargar imágenes a Amazon S3.
 */
@Service
public class AWSS3Service {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    /**
     * Carga una imagen a un bucket de Amazon S3 y devuelve la URL pública de la imagen cargada.
     *
     * @param file El archivo de imagen a cargar.
     * @return La URL pública de la imagen cargada.
     */
    public String uploadImage(MultipartFile file) {
        String url = "";
        File mainFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(mainFile)) {
            fos.write(file.getBytes());
            String newFileName = System.currentTimeMillis() + '_' + mainFile.getName();
            amazonS3.putObject(new PutObjectRequest(bucketName, newFileName, mainFile));
            url = "https://petradarimages.s3.eu-west-3.amazonaws.com/" + newFileName;
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());

        }
        return url;
    }
}
