package cl.tamila.repositorios;

import cl.tamila.modelos.ProductosModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRespositorio extends JpaRepository<ProductosModel,Integer> {
    //Metodo para verificar si existe un Slug
    public boolean existsBySlug(String slug);//Preguntamos si existe un parametro tipo Slug
}
