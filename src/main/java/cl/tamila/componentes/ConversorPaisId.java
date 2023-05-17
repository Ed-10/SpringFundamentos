package cl.tamila.componentes;

import cl.tamila.modelos.PaisModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConversorPaisId implements Converter<String, PaisModel> {
    @Override
    public PaisModel convert(String source) {
        Integer id= Integer.valueOf(source);
        PaisModel datos= new PaisModel();
        datos.setId(id);
        return datos;
    }
}
