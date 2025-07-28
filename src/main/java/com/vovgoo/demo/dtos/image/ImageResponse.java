package com.vovgoo.demo.dtos.image;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ImageResponse {

    @Schema(
            description = "Уникальный идентификатор изображения",
            example = "a1b2c3d4-e5f6-7a8b-9c0d-ef1234567890",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID id;

    @Schema(
            description = "Оригинальное имя файла изображения",
            example = "profile_picture.png",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String originalName;

    @Schema(
            description = "Ключ для доступа к изображению",
            example = "a1b2c3d4.png",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String key;

    @Schema(
            description = "Размер файла изображения в байтах",
            example = "204800",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long size;

    @Schema(
            description = "MIME тип изображения",
            example = "image/png",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String mimeType;

    @Schema(
            description = "Дата и время создания изображения",
            example = "2024-06-01T12:34:56",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDateTime createdAt;
}
