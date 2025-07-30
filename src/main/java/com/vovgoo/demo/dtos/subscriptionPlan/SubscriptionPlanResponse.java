package com.vovgoo.demo.dtos.subscriptionPlan;

import com.vovgoo.demo.dtos.stripe.PriceResponse;
import com.vovgoo.demo.entity.enums.SubscriptionFeature;
import com.vovgoo.demo.entity.enums.SubscriptionLimitType;
import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
@Builder
public class SubscriptionPlanResponse {
    private String name;
    private Set<SubscriptionFeature> enabledFeatures;
    private Map<SubscriptionLimitType, Integer> featureLimits;
    private List<PriceResponse> prices;
}
