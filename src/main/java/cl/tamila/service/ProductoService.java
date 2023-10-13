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
    //Inyeccion de nuestra intefas
    @Autowired
    private IProductoRespositorio repositorio;
    //Creando metodo para obtener las categorias de la tabla CATEGORIAS
    public List<ProductosModel> listar(){//Metodo listar productos
        return this.repositorio.findAll();//Traemos todos los registros de la tabla Categorias
    }
    //creando servicio(METODO) para GUARDAR las categorias en nuestra BD
    public void guardar(ProductosModel producto){
        this.repositorio.save(producto);
    }
}
