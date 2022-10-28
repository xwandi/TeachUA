package com.softserve.teachua.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

@Slf4j
@Component
public class QRCodeService {
    private static final Integer WIDTH = 200;
    private static final Integer HEIGHT = 200;
    private static final Integer ENCODELEVEL = 3;
    private static final String ENCODING = "utf-8";
    @Value("${baseURL}")
    private String BASE_URL;

    /**
     *
     * @param content
     *            content to be put into image
     * @param width
     *            width of QR code
     * @param height
     *            height of QR code
     * @param hints
     *            settings of the QR code
     * @param qrColorsConfig
     *            colors configuration for QR code image
     *            if null, the method will use a black-and-white style
     *
     * @return returns a generated QR code image as {@code BufferedImage}
     */
    private BufferedImage getQrCodeImage(String content, int width, int height, Hashtable hints, MatrixToImageConfig qrColorsConfig) {
        QRCodeWriter writer = new QRCodeWriter();
        if (hints == null) {
            hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, ENCODING);
        }
        BitMatrix matrix = null;

        try {
            matrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            log.debug("Error occured while making image");
        }

        if (qrColorsConfig != null)
            return MatrixToImageWriter.toBufferedImage(matrix, qrColorsConfig);
        else
            return MatrixToImageWriter.toBufferedImage(matrix);
    }

    private byte[] getQrCodeImageBytes(String content, int width, int height, MatrixToImageConfig qrColorsConfig) {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.forBits(ENCODELEVEL));
        hints.put(EncodeHintType.CHARACTER_SET, ENCODING);
        BufferedImage qrCodeImage = getQrCodeImage(content, width, height, hints, qrColorsConfig);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            ImageIO.write(qrCodeImage, "png", out);
        } catch (IOException e) {
            log.debug("Error while writing code to outputStream");
        }

        byte[] binaryData = out.toByteArray();

        return binaryData;
    }

    private String formContentUrl(Long serialNumber) {
        return BASE_URL + "/certificate/" + serialNumber;
    }

    public ByteArrayInputStream getCertificateQrCodeAsStream(Long serialNumber) {
        MatrixToImageConfig qrStyleConfig;

        switch (Long.toString(serialNumber).charAt(0)) {
            case '1':   // trainer id
            case '2':   // moderator id
                qrStyleConfig = new MatrixToImageConfig(new Color(255, 255, 255).getRGB(), new Color(0, 0, 0, 0).getRGB());
                break;
            default:
                qrStyleConfig = new MatrixToImageConfig(new Color(0, 0, 0).getRGB(), new Color(0, 0, 0, 0).getRGB());
        }

        return new ByteArrayInputStream(getQrCodeImageBytes(formContentUrl(serialNumber), WIDTH, HEIGHT, qrStyleConfig));
    }
}
