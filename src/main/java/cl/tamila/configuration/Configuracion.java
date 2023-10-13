package cl.tamila.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration//Clase que afecta de forma global
public class Configuracion implements WebMvcConfigurer {//personaliza y configura aspectos específicos de la configuración de Spring MVC
    //Configurando un metodo
    @Value("${eduardo.valores.ruta_images}")//Ruta generada en application.properties
    private String ruta_images;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {//Metodo para asignar una ruta de recusrsos estaticos
        // /opt/lampp/htdocs/pruebasApache
        //Ruta:  /home/edi/ImagesSpring
        //registry.addResourceHandler("/ImagesSpring/**").addResourceLocations("file: /home/edi/ImagesSpring");
        registry.addResourceHandler("/desarrolloWeb/**").addResourceLocations("file: "+this.ruta_images);
    }
}
