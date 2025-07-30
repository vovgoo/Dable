package com.vovgoo.demo.dtos.stripe;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceResponse {
    private String id;
    private String type;
    private Long amount;
    private String currency;
    private String billingScheme;
    private String recurringInterval;
    private Long recurringIntervalCount;
}
