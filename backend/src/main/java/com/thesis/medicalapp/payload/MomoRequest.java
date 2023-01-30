package com.thesis.medicalapp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MomoRequest {
    private String partnerCode;
    private String partnerName;
    private String storeId;
    private String requestType;
    private String ipnUrl;
    private String redirectUrl;
    private String orderId;
    private String amount;
    private String lang;
    private Boolean autoCapture;
    private String orderInfo;
    private String requestId;
    private String extraData;
    private String signature;
}
