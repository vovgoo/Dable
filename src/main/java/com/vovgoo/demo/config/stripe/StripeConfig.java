package com.vovgoo.demo.config.stripe;

import com.stripe.Stripe;
import com.vovgoo.demo.config.properties.StripeProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StripeConfig {
    private final StripeProperties stripeProperties;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeProperties.getSecretKey();
    }
}
