package cl.tamila.controller;

import cl.tamila.service.QrCodeService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/qr")
public class QrController {
    @Autowired
    private QrCodeService qrCodeService;//Inyectamos nuestro servicio
    @GetMapping("")
    public String home(){
        return "qr/home";
    }
    @GetMapping("/crear")
    public String crear(Model model){
        String url="https://github.com/Ed-10";
        byte[] image = new byte[0];
        try {
            image=this.qrCodeService.crearQR(url,250,250);
        }catch (WriterException | IOException e){

        }
        //Conversion de byte arraey en Base64 String
        String qrCode = Base64.getEncoder().encodeToString(image);
        model.addAttribute("qrCode",qrCode);
        model.addAttribute("url",url);

        return "qr/crear";
    }
}
