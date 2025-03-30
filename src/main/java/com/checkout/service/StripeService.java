package com.checkout.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.PriceListParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StripeService {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;
    @PostConstruct
    public void init() {
        if (stripeSecretKey == null || stripeSecretKey.isEmpty()) {
            throw new IllegalArgumentException("Stripe API key is not configured properly.");
        }
        Stripe.apiKey = stripeSecretKey;
    }

    public String createCheckoutSession(String successUrl, String cancelUrl) throws StripeException {

        String existingProductId = "prod_S2VNM4pxTff7tg";  // Replace with your logic to fetch existing product ID from DB or configuration

        Product product;

        try {
            // Attempt to fetch the existing product from Stripe using the ID
            product = Product.retrieve(existingProductId);
        } catch (StripeException e) {
            // If product doesn't exist, create a new one
            ProductCreateParams productParams = ProductCreateParams.builder()
                    .setName("MediCare Pro - Annual Plan\n")  // Name of the product
                    .setDescription("Get **24/7 doctor consultations, AI-powered health reports, and priority medical assistance** for a full year.")  // Product description
                    .build();

            product = Product.create(productParams);
        }

        // Fetch existing prices for the product
        PriceListParams priceListParams = PriceListParams.builder()
                .setProduct(existingProductId)
                .build();

        List<Price> prices = Price.list(priceListParams).getData();

        Price price;

        if (!prices.isEmpty()) {
            // Reuse the existing price
            price = prices.get(0);
        } else {
            // Create a new price if no existing price is found
            PriceCreateParams priceParams = PriceCreateParams.builder()
                    .setUnitAmount(5800L)
                    .setCurrency("usd")
                    .setProduct(product.getId())
                    .setRecurring(PriceCreateParams.Recurring.builder()
                            .setInterval(PriceCreateParams.Recurring.Interval.YEAR)
                            .build())
                    .build();
            price = Price.create(priceParams);

        }

        SessionCreateParams sessionParams = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPrice(price.getId())
                                .setQuantity(1L)
                                .build())
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)  // Use subscription mode
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .build();

        return Session.create(sessionParams).getId();
    }
}
