package com.vovgoo.demo.service.impl;

import com.vovgoo.demo.config.properties.YandexS3Properties;
import com.vovgoo.demo.entity.Image;
import com.vovgoo.demo.exceptions.ImageUploadException;
import com.vovgoo.demo.repository.ImageRepository;
import com.vovgoo.demo.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final S3Client s3Client;
    private final YandexS3Properties yandexS3Properties;

    @Override
    @Transactional
    public Image uploadImage(MultipartFile file) {
        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();

        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(yandexS3Properties.getBucket())
                    .key(key)
                    .build();

            RequestBody requestBody = RequestBody.fromInputStream(inputStream, file.getSize());

            s3Client.putObject(putObjectRequest, requestBody);

            Image image = Image.builder()
                    .originalName(file.getOriginalFilename())
                    .mimeType(file.getContentType())
                    .size(file.getSize())
                    .key(key)
                    .build();

            return imageRepository.save(image);
        } catch (IOException e) {
            throw new ImageUploadException("Failed to upload image to cloud storage", e);
        }
    }

    @Override
    public void deleteImage(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(yandexS3Properties.getBucket())
                .key(key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);

        imageRepository.deleteByKey(key);
    }
}
