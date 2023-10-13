package cl.tamila.controller;

import cl.tamila.modelos.ProductosRestModel;
import cl.tamila.service.ClienteRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
@Controller
@RequestMapping("/cliente-rest")
public class ClienteRestController {
    @Autowired
    private ClienteRestService clienteRestService;//Traemos nuestro servicio Generado
    @GetMapping("")
    public String home(Model model){
        //FALTA IMPLEMENTAAAR
        //List<ProductosRestModel> datos=this.clienteRestService.listar();
        //model.addAttribute("datos",datos);
        return "cliente_rest/home";
    }
}
