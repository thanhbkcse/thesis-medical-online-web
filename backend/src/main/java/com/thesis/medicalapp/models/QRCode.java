package com.thesis.medicalapp.models;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class QRCode {
    public static String createQR(String data, int wid, int hei)
    {
        StringBuilder result = new StringBuilder();
        if (!data.isEmpty()) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            try {
                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, wid, hei);
                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                ImageIO.write(bufferedImage, "png", os);
                result.append("data:image/png;base64,");
                result.append(new String(Base64.getEncoder().encode(os.toByteArray())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }
}
