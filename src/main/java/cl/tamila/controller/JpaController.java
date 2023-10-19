package cl.tamila.controller;

import cl.tamila.modelos.CategoriaModel;
import cl.tamila.modelos.ProductosModel;
import cl.tamila.service.CategoriaService;
import cl.tamila.service.ProductoService;
import cl.tamila.utilidades.Utilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/jpa-repository")
public class JpaController{
    @Autowired
    private CategoriaService categoriaService; //Inyectando las categorias de la BD
    @Autowired
    private ProductoService productoService; //Inyectando los productos de la BD
    @Value("${eduardo.valores.url_images}")
    private String url_images;
    @Value("${eduardo.valores.ruta_images}")
    private String ruta_images;
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
            return "jpa-repository/categorias_add";
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
    public String categorias_editar_post(@Valid CategoriaModel categoria,
                                         @PathVariable("id")int id,
                                         BindingResult result,
                                         RedirectAttributes flash,
                                         Model model)
    {
        //Con esto, vamos a formatear el dato de algun error
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo ". concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("errores", errores);
            model.addAttribute("categoria", categoria);
            return "jpa-repository/categorias_add";
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

    /////////////////////////////////////// SECCION PRODUCTOS ///////////////////////////////////////////////////////////

    //Seccion para poder listar y mostrar los productos
    @GetMapping("/productos")
    public String productos(Model model){
        model.addAttribute("datos",this.productoService.listar());//Trayendo el listado de la BD
        return "jpa-repository/productos";
    }
    //Creando un filtro para poder traer PORDUCTOS a travez de la CATEGORIA:
    @GetMapping("/productos/categorias/{id}")
    public String productos_categorias(@PathVariable("id") Integer id,
                                      Model model){
        CategoriaModel categoria = this.categoriaService.buscarPorId(id);//Servicio implementado, class=CategoriaService
        if (categoria==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Pagina no encontrada");
        }
        model.addAttribute("datos",this.productoService.listar_por_categorias(categoria));//Trayendo el listado de la BD
        model.addAttribute("categoria",categoria);
        return "jpa-repository/productos_categorias";
    }
    //Seccion para poder agregar nuevos productos  productos-wherein
    @GetMapping("/productos/add")
    public String productos_add(Model model){
        ProductosModel productos=new ProductosModel();
        model.addAttribute("productos",productos);//Lo convertimos en un objeto
        model.addAttribute("listarCategorias",this.categoriaService.listar());//Vamos a traer las categorias y listarlas
        return "jpa-repository/productos_add";
    }
    @PostMapping("/productos/add")
    public String categorias_add_post(@Valid ProductosModel productos,//Validamos a nuestro objeto ProductosModel llamado productos.
                                      @RequestParam ("archivoImage") MultipartFile multiPart,
                                      BindingResult result,
                                      RedirectAttributes flash,//Manejador de mensajes flash
                                      Model model){
        //Con esto, vamos a formatear el dato de algun error
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo ". concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("errores", errores);
            model.addAttribute("productos", productos);
            return "jpa-repository/productos_add";
        }
        if (multiPart.isEmpty()){//Si no subieron nada
            flash.addFlashAttribute("clase", "danger");
            flash.addFlashAttribute("mensaje", "La imagen es obligatoria");
            return "redirect:/jpa-repository/productos/add";
        }
        if (!multiPart.isEmpty()) {
            String nombreImagen= Utilidades.guardarArchivo(multiPart,this.ruta_images+"ImagesSpring/");
            if (nombreImagen == "no"){
                flash.addFlashAttribute("clase", "danger");
                flash.addFlashAttribute("mensaje", "El formato del archivo no es valido");
                return "redirect:/jpa-repository/productos/add";
            }
            if (nombreImagen!=null){
                productos.setFoto(nombreImagen);
            }
        }
        productos.setSlug(Utilidades.getSlug(productos.getNombre()));
        this.productoService.guardar(productos);
        flash.addFlashAttribute("clase", "success");
        flash.addFlashAttribute("mensaje", "Se creo el registro exitosamente");

        return "redirect:/jpa-repository/productos/add";
        }
    //Seccion para poder EDITAR productos
    @GetMapping("/productos/editar/{id}")
    public String productos_editar(@PathVariable("id")Integer id, Model model){//Funcionalidad para traer datos de categoria deacuerdo a su ID

        ProductosModel productos = this.productoService.buscarPorId(id);//Servicio implementado, class=ProductoService
        model.addAttribute("productos",productos);//Lo convertimos en un objeto
        productos.setCategoriaId(productos.getCategoriaId());

        return "jpa-repository/productos_editar";
    }
    @PostMapping("/productos/editar/{id}")
    public String productos_editar_post(@Valid ProductosModel productos,//Validamos a nuestro objeto ProductosModel llamado productos.
                                        @PathVariable("id") Integer id,
                                        @RequestParam ("archivoImage") MultipartFile multiPart,
                                        BindingResult result,
                                        RedirectAttributes flash,//Manejador de mensajes flash
                                        Model model){
        //Con esto, vamos a formatear el dato de algun error
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo ". concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("errores", errores);
            model.addAttribute("productos", productos);
            return "jpa-repository/productos_add";
        }
        if (!multiPart.isEmpty()) {
            String nombreImagen= Utilidades.guardarArchivo(multiPart,this.ruta_images+"ImagesSpring/");
            if (nombreImagen == "no"){
                flash.addFlashAttribute("clase", "danger");
                flash.addFlashAttribute("mensaje", "El formato del archivo no es valido");
                return "redirect:/jpa-repository/productos/editar/"+id;
            }
            if (nombreImagen!=null){
                productos.setFoto(nombreImagen);
            }
        }
        productos.setSlug(Utilidades.getSlug(productos.getNombre()));
        this.productoService.guardar(productos);

        flash.addFlashAttribute("clase", "success");
        flash.addFlashAttribute("mensaje", "El registro se ah editado con exito");
        return "redirect:/jpa-repository/productos/editar/"+id;

    }
    //Seccion para poder ELIMINAR productos
    @GetMapping("/productos/eliminar/{id}")
    public String productos_eliminar(@PathVariable("id") Integer id,
                                     RedirectAttributes flash
                                     )
    {
        ProductosModel productos = this.productoService.buscarPorId(id);//Servicio implementado, class=ProductoService y traemos valor por ID
        File myObjet=new File(this.ruta_images+"ImagesSpring/"+productos.getFoto());

        if (myObjet.delete()){
            this.productoService.eliminar(id);
            flash.addFlashAttribute("clase", "success");
            flash.addFlashAttribute("mensaje", "Se elimino de forma correcta");
        }else {
            flash.addFlashAttribute("clase", "danger");
            flash.addFlashAttribute("mensaje", "Ocurrio un error, vuelva a intentarlo");
        }
        return "redirect:/jpa-repository/productos";
    }

    //Aqui pueden estar algunos modelos genericos que pueden reutilizarse en varias clases mas
    @ModelAttribute
    public void setGenericos(Model model){
        model.addAttribute("listarCategorias",this.categoriaService.listar());//Vamos a traer las categorias y listarlas
        model.addAttribute("url_images",this.url_images);
    }
}
