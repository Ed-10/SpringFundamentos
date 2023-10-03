package cl.tamila.service;

import cl.tamila.modelos.CategoriaModel;
import cl.tamila.modelos.ProductosModel;
import cl.tamila.repositorios.IProductoRespositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class ProductoService {
    @Autowired
    private IProductoRespositorio repositorio;
    public List<ProductosModel> lista(){
        return this.repositorio.findAll();//Traemos todos los registros de la tabla Categorias
    }
}
