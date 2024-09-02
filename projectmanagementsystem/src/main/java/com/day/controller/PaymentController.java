package com.day.controller;

import com.day.modal.PlanType;
import com.day.modal.User;
import com.day.response.PaymentLinkResponse;
import com.day.service.UserService;
import com.stripe.Stripe;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Value("${stripe.api.key}")
    private String apiKey;

    @Value("${stripe.api.secret}")
    private String secretApiKey;

    @Autowired
    private UserService userService;

    @PostMapping("/{planType}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @PathVariable PlanType planType,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        // 설정된 API 키로 Stripe를 초기화
        Stripe.apiKey = secretApiKey;

        // 결제 금액 설정
        long amount = 12923; // 기본 월간 요금 12,923 KRW (단위는 원)
        if (planType.equals(PlanType.ANNUALLY)) {
            amount = amount * 12; // 연간 요금
            amount = (long) (amount * 0.7); // 30% 할인 적용
        }

        // 가격 객체 생성
        PriceCreateParams priceParams = PriceCreateParams.builder()
                .setUnitAmount(amount)
                .setCurrency("krw")
                .setProductData(
                        PriceCreateParams.ProductData.builder()
                                .setName(planType.toString() + " Subscription")
                                .build()
                )
                .build();

        // Price 객체 생성
        Price price = Price.create(priceParams);

        // PaymentLink 생성 요청 매개변수 설정
        PaymentLinkCreateParams params = PaymentLinkCreateParams.builder()
                .addLineItem(
                        PaymentLinkCreateParams.LineItem.builder()
                                .setPrice(price.getId())  // 생성된 Price의 ID 사용
                                .setQuantity(1L)
                                .build()
                )
                .setAfterCompletion(
                        PaymentLinkCreateParams.AfterCompletion.builder()
                                .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                .setRedirect(
                                        PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                .setUrl("http://localhost:5173/upgrade_plan/success?planType=" + planType)
                                                .build()
                                )
                                .build()
                )
                .build();

        // PaymentLink 생성
        PaymentLink paymentLink = PaymentLink.create(params);

        // 응답 생성
        PaymentLinkResponse res = new PaymentLinkResponse();
        res.setPayment_link_id(paymentLink.getId());
        res.setPayment_link_url(paymentLink.getUrl());

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
