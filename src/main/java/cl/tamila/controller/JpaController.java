package cl.tamila.controller;

import cl.tamila.modelos.CategoriaModel;
import cl.tamila.service.CategoriaService;
import cl.tamila.utilidades.Utilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/jpa-repository")
public class JpaController{
    @Autowired
    private CategoriaService categoriaService; //Inyectando las categorias de la BD
    @GetMapping("")
    public String home(Model model){
        return "jpa-repository/home";
    }
    /////////// CATEGORIAS //////////////////
    @GetMapping("/categorias")
    public String categorias(Model model){
        model.addAttribute("datos",this.categoriaService.listar());//Trayendo el listado de la BD
        return "jpa-repository/categorias";
    }
    @GetMapping("/categorias/add")
    public String categorias_add(Model model){
        CategoriaModel categoria=new CategoriaModel();
        model.addAttribute("categoria",categoria);//Lo convertimos en un objeto
        return "jpa-repository/categorias_add";
    }
    @PostMapping("/categorias/add")
    public String categorias_add_post(@Valid CategoriaModel categoria,BindingResult result,//Validamos a nuestro objeto CategoriaModel llamado categoria.
                                      RedirectAttributes flash,//Manejador de mensajes flash
                                      Model model){
        //Con esto, vamos a formatear el dato de algun error
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo ". concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("errores", errores);
            model.addAttribute("categoria", categoria);
            return "formularios/categorias_add";
        }
        String slug=Utilidades.getSlug(categoria.getNombre());//Pasamos nombre de la categoria para convertirlo a Slug
        if (this.categoriaService.buscarPorSlug(slug)==false){
            flash.addFlashAttribute("clase", "danger");
            flash.addFlashAttribute("mensaje", "La categoria ya existe en la Base de datos");
            return "redirect:/jpa-repository/categorias/add";
        }else{
            categoria.setSlug(slug);
            this.categoriaService.guardar(categoria);
            flash.addFlashAttribute("clase", "success");
            flash.addFlashAttribute("mensaje", "Se ah creado el registro de forma exitosa");
            return "redirect:/jpa-repository/categorias/add";
        }
    }
    @GetMapping("/categorias/editar/{id}")
    public String categorias_editar(@PathVariable("id")Integer id, Model model){//Funcionalidad para traer datos de categoria deacuerdo a su ID
        CategoriaModel categoria = this.categoriaService.buscarPorId(id);//Servicio implementado, class=CategoriaService
        model.addAttribute("categoria",categoria); //Traemos nuestro objeto

        return "jpa-repository/categorias_editar";
    }
    @PostMapping("/categorias/editar/{id}")
    public String categorias_editar_post(@Valid CategoriaModel categoria,BindingResult result,
                                         @PathVariable("id")int id,RedirectAttributes flash, Model model){
        //Con esto, vamos a formatear el dato de algun error
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo ". concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("errores", errores);
            model.addAttribute("categoria", categoria);
            return "formularios/categorias_add";
        }
        categoria.setSlug(Utilidades.getSlug(categoria.getNombre()));
        this.categoriaService.guardar(categoria);

        flash.addFlashAttribute("clase", "success");
        flash.addFlashAttribute("mensaje", "Se ah ediatdo el registro de forma exitosa");
        return "redirect:/jpa-repository/categorias/editar/"+id;
    }
    @GetMapping("/categorias/eliminar/{id}")
    public String categorias_eliminar (@PathVariable("id") Integer id ,RedirectAttributes flash, Model model){
        try {
            this.categoriaService.eliminar(id);
            flash.addFlashAttribute("clase", "success");
            flash.addFlashAttribute("mensaje", "Se elimino de forma exitosa");
            return "redirect:/jpa-repository/categorias";
        }catch (Exception e){
            flash.addFlashAttribute("clase", "danger");
            flash.addFlashAttribute("mensaje", "No es posible eliminar, intentelo mas tarde");
            return "redirect:/jpa-repository/categorias";
        }
    }
}
