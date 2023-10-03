package cl.tamila.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@Primary
public class QrCodeService {
    public byte[] crearQR(String text, int witdh, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter=new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE,witdh,height);
        ByteArrayOutputStream pngOutputStream= new ByteArrayOutputStream();

        MatrixToImageConfig con = new MatrixToImageConfig(0xFF000002, 0X00000000);
        MatrixToImageWriter.writeToStream(bitMatrix,"PNG", pngOutputStream, con);

        byte[] pngData=pngOutputStream.toByteArray();
        return pngData;

    }
}
