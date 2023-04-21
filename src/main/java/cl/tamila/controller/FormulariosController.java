package cl.tamila.controller;

import cl.tamila.modelos.UsuarioModel;
import cl.tamila.modelos.UsuarioValidaciones;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/formularios")
public class FormulariosController {
    @GetMapping("")
    public String home(){
        return "formularios/home";
    }
    //################### Formulario simple ##########################
    @GetMapping("/simple")
    public String simple() {
        return "formularios/simple";
    }
    @PostMapping("/simple")
    @ResponseBody
    public String simple_post(
            @RequestParam(name = "username")String username,
            @RequestParam(name = "correo")String correo,
            @RequestParam(name = "password")String password
    )
    {
        return "FORMULARIO SIMPLE: "+"<br/>username = "+ username + "<br/>correo="
                + correo + "<br/>password=" + password;
        }

    //################### Formulario de objetos ##########################
    @GetMapping("/objeto")
    public String objeto() {
        return "formularios/objeto";
    }
    @PostMapping("/objeto")
    @ResponseBody
    public String objeto_post(UsuarioModel usuario){
        return "FORMULARIO CON OBJETOS: "+"<br/>username = "+ usuario.getUsername() + "<br/>correo="
                + usuario.getCorreo() + "<br/>password=" + usuario.getPassword();
    }
    //################### Formulario de objetos 2##########################
    @GetMapping("/objeto2")
    public String objeto2(Model model) {
        model.addAttribute("usuario", new UsuarioModel());
        return "formularios/objeto2";
    }
    @PostMapping("/objeto2")
    @ResponseBody
    public String objeto2_post(UsuarioModel usuario){
        return "FORMULARIO CON OBJETOS: "+"<br/>username = "+ usuario.getUsername() + "<br/>correo="
                + usuario.getCorreo() + "<br/>password=" + usuario.getPassword();
    }
    //################### Trabajando con validaciones ##########################
    @GetMapping("/validaciones")
    public String validaciones(Model model) {
        model.addAttribute("usuario", new UsuarioValidaciones());
        return "formularios/validaciones";
    }
    @PostMapping("/validaciones")
    public String validaciones_post(@Valid UsuarioValidaciones usuario, BindingResult result, Model model){
        //Con esto, vamos a formatear el dato de algun error
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo ". concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("errores", errores);
            model.addAttribute("usuario", usuario);
            return "formularios/validaciones";
        }
        model.addAttribute("usuario", usuario);
        return "formularios/validaciones_result";
    }
}
