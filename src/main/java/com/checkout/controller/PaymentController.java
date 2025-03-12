package com.checkout.controller;

import com.checkout.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/checkout-session")
    public String createCheckoutSession(@RequestParam String successUrl, @RequestParam String cancelUrl) throws StripeException {
        // Create and return the Stripe Checkout session
        return stripeService.createCheckoutSession(successUrl, cancelUrl);
    }
}
