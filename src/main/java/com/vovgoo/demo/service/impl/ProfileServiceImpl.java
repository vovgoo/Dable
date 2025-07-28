package com.vovgoo.demo.service.impl;

import com.vovgoo.demo.dtos.image.UploadImageRequest;
import com.vovgoo.demo.entity.Image;
import com.vovgoo.demo.entity.User;
import com.vovgoo.demo.repository.UserRepository;
import com.vovgoo.demo.service.ImageService;
import com.vovgoo.demo.service.ProfileService;
import com.vovgoo.demo.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

    private final ImageService imageService;
    private final UserRepository userRepository;
    private final SecurityService securityService;

    @Override
    @Transactional
    public User uploadProfileImage(UploadImageRequest request) {
        String username = securityService.getCurrentUserDetails().getUsername();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        if (user.getProfileImage() != null) {
            imageService.deleteImage(user.getProfileImage().getKey());
        }

        Image image = imageService.uploadImage(request.getImage());

        user.setProfileImage(image);

        return userRepository.save(user);
    }
}
