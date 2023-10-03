package cl.tamila.service;

import cl.tamila.modelos.CategoriaModel;
import cl.tamila.repositorios.ICategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class CategoriaService {
    @Autowired
    private ICategoriaRepositorio repositorio; //Inyectando la interfaz ICategoriaRepositorio
    //Creando metodo para obtener las categorias de la tabla CATEGORIAS
    public List<CategoriaModel> listar(){
        return this.repositorio.findAll();//Traemos todos los registros de la tabla Categorias
    }
    //creando servicio(METODO) para GUARDAR las categorias en nuestra BD
    public void guardar(CategoriaModel categoria){//Recibe un objeto CategoriaModel
        this.repositorio.save(categoria);//Insertarmos categoria
    }
    //Servicio para poder buscar el ID de nuestros objetos registrados en la BD
    public CategoriaModel buscarPorId(Integer id){//Es Integer por que en nuestro modelo se registro como Integer
        Optional<CategoriaModel> optional = this.repositorio.findById(id);//Envolvemos en Optional
        if (optional.isPresent()){//Si existe el regitro
            return optional.get();//Si se cumple la condicion, retorna el CategoriaModel
        }
        return null;
    }
    //Servicio(Metodo) para poder buscar nuestro campo Slug (Buscamos por un campo especifico)
    public boolean buscarPorSlug(String slug){
        if (this.repositorio.existsBySlug(slug)){
            return false;
        }else {
            return true;
        }
    }
    //Servicio para poder ELIMINAR
    public void eliminar(Integer id){//Integer, esto viene de nuestro modelo CategoriaModel.java
        this.repositorio.deleteById(id);
    }

}
