package com.vovgoo.demo.mappers;

import com.vovgoo.demo.dtos.image.ImageResponse;
import com.vovgoo.demo.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageResponseMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "originalName", source = "originalName")
    @Mapping(target = "key", source = "key")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "mimeType", source = "mimeType")
    @Mapping(target = "createdAt", source = "createdAt")
    ImageResponse toDto(Image image);
}
