package cl.tamila.controller;

import com.itextpdf.html2pdf.ConverterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

@Controller
@RequestMapping("/reportes")
public class ReportesController {
    @Value("${eduardo.valores.ruta_images}")
    private String ruta_images;
    @Value("eduardo.ruta.pdf")
    private String ruta_pdf;
    @GetMapping("")
    public String home()
    {
        return "reportes/home";
    }
    //Seccion PDF
    private final TemplateEngine templateEngine;
    public ReportesController(TemplateEngine templateEngine)
    {
        this.templateEngine=templateEngine;
    }
    @Autowired
    private ServletContext servletContext;
    @GetMapping("/pdf")
    public ResponseEntity<?> productos_pdf(HttpServletRequest request, HttpServletResponse response)
    {
        WebContext context=new WebContext(request,response,this.servletContext);
        context.setVariable("titulo","PDF dinamico desde Spring Boot");
        context.setVariable("ruta", this.ruta_images);

        String html= this.templateEngine.process("reportes/pdf",context);

        ByteArrayOutputStream target = new ByteArrayOutputStream();

        ConverterProperties converterProperties= new ConverterProperties();
        converterProperties.setBaseUri(this.ruta_pdf);
    }
}
