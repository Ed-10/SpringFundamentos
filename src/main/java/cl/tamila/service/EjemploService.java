package cl.tamila.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class EjemploService {
    public String saludo(){
        return "Hola desde un servicio inyectado desde spring";
    }
}
