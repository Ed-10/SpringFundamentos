package cl.tamila.controller;

import cl.tamila.modelos.*;
import cl.tamila.utilidades.Utilidades;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/formularios")
public class FormulariosController {
    @Value("${eduardo.valores.ruta_images}")
    private String ruta_images;
    @Value("${eduardo.valores.url_images}")
    private String url_images;
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
    //################### Trabajando con Select Dinamico ##########################
    @GetMapping("/select_dinamico")
    public String select_dinamico(Model model) {

        model.addAttribute("usuario", new UsuarioDinamico());
        return "formularios/select_dinamico";
    }
    @PostMapping("/select_dinamico")
    public String select_dinamico_post(@Valid UsuarioDinamico usuario, BindingResult result, Model model){
        //Con esto, vamos a formatear el dato de algun error
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo ". concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("errores", errores);
            model.addAttribute("usuario", usuario);
            return "formularios/select_dinamico";
        }
        model.addAttribute("usuario", usuario);
        return "formularios/selectDinamico_result";
    }
    //################### Form checkbox ##########################
    @GetMapping("/checkbox")
    public String checkbox(Model model) {
        model.addAttribute("usuario", new UsuarioCheckboxModel());
        return "formularios/checkbox";
    }
    @PostMapping("/checkbox")
    public String checkbox_post(@Valid UsuarioCheckboxModel usuario, BindingResult result, Model model){
        //Con esto, vamos a formatear el dato de algun error
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo ". concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("errores", errores);
            model.addAttribute("usuario", usuario);
            return "formularios/checkbox";
        }
        model.addAttribute("usuario", usuario);
        return "formularios/checkbox_result";
    }
    //################### Form mensjaes flash ##########################
    @GetMapping("/flash")
    public String flash(Model model) {
        model.addAttribute("usuario", new UsuarioModel());
        return "formularios/flash";
    }
    @PostMapping("/flash")
    public String flash_post(UsuarioModel usuario, RedirectAttributes flash){
        flash.addFlashAttribute("clase", "success");
        flash.addFlashAttribute("mensaje", "Ejemplo de mensaje flash");
        return "redirect:/formularios/flash-respuesta";
    }
    //################### UPLOAD Guardar archivos, las imagenes ##########################
    @GetMapping("/upload")
    public String upload(Model model) {
        model.addAttribute("usuario", new UsuarioUploadModel());
        return "formularios/upload";
    }

    @PostMapping("/upload")
    public String upload_post(@Valid UsuarioUploadModel usuario, BindingResult result, Model model, @RequestParam("archivoImage")MultipartFile multiPart, RedirectAttributes flash){
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo ". concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("errores", errores);
            model.addAttribute("usuario", usuario);
            return "formularios/upload";
        }
        if (multiPart.isEmpty()){
            flash.addFlashAttribute("clase", "danger");
            flash.addFlashAttribute("mensaje", "La imagen es obligatoria");
            return "redirect:/formularios/upload";
        }else {
            String nombreImagen= Utilidades.guardarArchivo(multiPart,this.ruta_images+"Prueba1/");
            if (nombreImagen=="no"){
                flash.addFlashAttribute("clase", "danger");
                flash.addFlashAttribute("mensaje", "El formato del archivo no es valido");
                return "redirect:/formularios/upload";
            }
            if (nombreImagen!=null){
                usuario.setFoto(nombreImagen);
            }
        }
        model.addAttribute("usuario", usuario);
        return "formularios/upload_respuesta";
    }

    //################### DATOS que se cargan en formularios ##########################
    @GetMapping("/flash-respuesta")
    public String flash_respuesta() {
        return "formularios/flash_respuesta";
    }
    //################### Campos genericos mediante @ModelAddAttribute
    //  Con esta anotacion tenemos disponibles estos datos en cualquier otro lugar ##########################
    @ModelAttribute
    public void setGenericos(Model model){
        //Generando lista de Paises
        List<PaisModel> paises= new ArrayList<PaisModel>();
        paises.add(new PaisModel(1,"Mexico"));
        paises.add(new PaisModel(2,"Chile"));
        paises.add(new PaisModel(3,"EU"));
        paises.add(new PaisModel(4,"Canada"));
        paises.add(new PaisModel(5,"Peru"));
        paises.add(new PaisModel(6,"Alemania"));
        paises.add(new PaisModel(7,"Japon"));
        model.addAttribute("paises",paises);
        //Generando lista de Intereses
        List<InteresesModel> intereses= new ArrayList<InteresesModel>();
        intereses.add(new InteresesModel(1, "Música"));
        intereses.add(new InteresesModel(2, "Deportes"));
        intereses.add(new InteresesModel(3, "Viajes"));
        intereses.add(new InteresesModel(4, "Cine"));
        intereses.add(new InteresesModel(5, "Gastronomía"));
        intereses.add(new InteresesModel(6, "Arte"));
        intereses.add(new InteresesModel(7, "Literatura"));
        intereses.add(new InteresesModel(8, "Politica"));
        model.addAttribute("intereses",intereses);

        model.addAttribute("url_images",this.url_images);
    }

}
