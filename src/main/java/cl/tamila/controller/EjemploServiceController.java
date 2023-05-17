package cl.tamila.controller;

import cl.tamila.service.EjemploService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ejemplo-service")
public class EjemploServiceController {
    @Autowired
    private EjemploService emeploservice;
    @GetMapping("")
    public String home(Model model){
        model.addAttribute("ejemplo", this.emeploservice.saludo());
        return "ejemplo_service/home";
    }
}
