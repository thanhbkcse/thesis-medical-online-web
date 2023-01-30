package com.thesis.medicalapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Momo {
    private String partnerCode;
    private String requestId;
    private String orderId;
    private String amount;
    private String responseTime;
    private String message;
    private String resultCode;
    private String payUrl;
    private String deeplink;
    private String qrCodeUrl;
}
