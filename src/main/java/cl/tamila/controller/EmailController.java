package cl.tamila.controller;

import cl.tamila.modelos.MailModel;
import cl.tamila.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;

@Controller
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
    @GetMapping("")
    public String home(Model model){
        return "email/home";
    }
    @GetMapping("/send")
    public String send(Model model, RedirectAttributes flash) throws MessagingException {
        model.addAttribute("datos", new MailModel());
        this.emailService.sendMail("eduardomartinez67@aragon.unam.mx","Prueba","Hola a todos, soy un mensaje");


        flash.addFlashAttribute("clase", "success");
        flash.addFlashAttribute("mensaje", "Se envio el E-mail con exitoo");
        return "redirect:/email";
    }
}
