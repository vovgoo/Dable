package com.vovgoo.demo.mappers;

import com.stripe.model.Price;
import com.vovgoo.demo.dtos.stripe.PriceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceResponseMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "unitAmount", target = "amount")
    @Mapping(source = "currency", target = "currency")
    @Mapping(source = "billingScheme", target = "billingScheme")
    @Mapping(source = "recurring.interval", target = "recurringInterval")
    @Mapping(source = "recurring.intervalCount", target = "recurringIntervalCount")
    PriceResponse toDto(Price price);

    List<PriceResponse> toDtoList(List<Price> prices);
}
