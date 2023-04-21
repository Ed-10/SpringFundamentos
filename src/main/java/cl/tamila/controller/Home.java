package cl.tamila.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class Home {
    @Value("${eduardo.valores.nombre}")
    private String EduardoValoresNombre;
    @GetMapping("/valores")
    @ResponseBody
    public String valores(){
        return "eduardo.valores.nombre= "+this.EduardoValoresNombre;
    }
    @GetMapping("")
    public String home(){
        return "/home/home";
    }

    @GetMapping("/nosotros")
    @ResponseBody
    public String nosotros(){
        return "Hola desde nosotros";
    }
    @GetMapping("/parametros/{id}/{slug}")
    @ResponseBody
    public String parametros(@PathVariable("id") int id, @PathVariable("slug") String slug){
        return "id= "+ id + "slug= "+ slug;
    }
    @GetMapping("/query-string")
    @ResponseBody
    public String query_string(@RequestParam("id") int id, @RequestParam("slug") String slug){
        return "id= "+ id + "slug= "+ slug;
    }
}
