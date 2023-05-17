package cl.tamila.componentes;

import cl.tamila.modelos.InteresesModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConversorIntereses implements Converter<String, InteresesModel> {
    @Override
    public InteresesModel convert(String source) {
        Integer id= Integer.valueOf(source);
        InteresesModel datos= new InteresesModel();
        datos.setId(id);
        return datos;
    }
}
