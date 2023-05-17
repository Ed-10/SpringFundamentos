package cl.tamila.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Configuracion implements WebMvcConfigurer {
    //Configurando un metodo
    @Value("${eduardo.valores.ruta_images}")
    private String ruta_images;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Ruta:  /home/edi/ImagesSpring
        //registry.addResourceHandler("/ImagesSpring/**").addResourceLocations("file: /home/edi/ImagesSpring");
        registry.addResourceHandler("/ImagesSpring/**").addResourceLocations("file: "+this.ruta_images);
    }
}
