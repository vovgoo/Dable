package com.vovgoo.demo.service;

import com.vovgoo.demo.dtos.image.UploadImageRequest;
import com.vovgoo.demo.entity.User;

public interface ProfileService {
    User uploadProfileImage(UploadImageRequest request);
}
