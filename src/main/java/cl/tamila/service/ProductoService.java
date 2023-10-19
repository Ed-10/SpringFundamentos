package cl.tamila.service;

import cl.tamila.modelos.CategoriaModel;
import cl.tamila.modelos.ProductosModel;
import cl.tamila.repositorios.IProductoRespositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ProductoService {
    //Inyeccion de nuestras intefaces
    @Autowired
    private IProductoRespositorio repositorio;
    //Creando metodo para obtener las categorias de la tabla CATEGORIAS
    public List<ProductosModel> listar(){//Metodo listar productos
        return this.repositorio.findAll();//Traemos todos los registros de la tabla Categorias
    }
    //Creando metodo para obtener las categorias de la tabla CATEGORIAS
    public List<ProductosModel> listar2(){//Metodo listar productos
        return this.repositorio.findAll();//Traemos todos los registros de la tabla Categorias
    }
    //creando servicio(METODO) para GUARDAR las categorias en nuestra BD
    public void guardar(ProductosModel producto){
        this.repositorio.save(producto);
    }
    //Servicio para poder buscar el ID de nuestros objetos registrados en la BD
    public ProductosModel buscarPorId(Integer id){//Es Integer por que en nuestro modelo se registro como Integer
        Optional<ProductosModel> optional = this.repositorio.findById(id);//Envolvemos en Optional
        if (optional.isPresent()){//Si existe el regitro
            return optional.get();//Si se cumple la condicion, retorna el CategoriaModel
        }
        return null;
    }
    //Servicio para poder ELIMINAR
    public void eliminar(Integer id){//Integer, esto viene de nuestro modelo CategoriaModel.java
        this.repositorio.deleteById(id);
    }
    //Creando metodo para poder LISTAR nuestras CATEGORIAS
    public List<ProductosModel> listar_por_categorias(CategoriaModel categoria){//Metodo listar productos
        return this.repositorio.findAllBycategoriaId(categoria);//Traemos todos los registros de la tabla Categorias
    }
}
