package com.vovgoo.demo.service;

import com.vovgoo.demo.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image uploadImage(MultipartFile file);
    void deleteImage(String key);
}
