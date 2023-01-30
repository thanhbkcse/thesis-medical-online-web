package com.thesis.medicalapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MomoResponse {
    String payUrl;
    String deeplink;
    String deeplinkWebInApp;
    String qrCodeUrl;
}
