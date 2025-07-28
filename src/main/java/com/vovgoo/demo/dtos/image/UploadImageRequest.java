package com.vovgoo.demo.dtos.image;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadImageRequest {

    @Schema(type = "string", format = "binary")
    private MultipartFile image;
}
