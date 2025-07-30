package com.vovgoo.demo.mappers;

import com.stripe.model.Price;
import com.vovgoo.demo.dtos.subscriptionPlan.SubscriptionPlanResponse;
import com.vovgoo.demo.entity.SubscriptionPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = PriceResponseMapper.class)
public interface SubscriptionPlanMapper {

    @Mapping(source = "plan.name", target = "name")
    @Mapping(source = "plan.enabledFeatures", target = "enabledFeatures")
    @Mapping(source = "plan.featureLimits", target = "featureLimits")
    @Mapping(source = "prices", target = "prices")
    SubscriptionPlanResponse toResponse(SubscriptionPlan plan, List<Price> prices);
}
