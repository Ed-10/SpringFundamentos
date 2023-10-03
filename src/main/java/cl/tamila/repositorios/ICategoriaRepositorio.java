package cl.tamila.repositorios;

import cl.tamila.modelos.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepositorio extends JpaRepository<CategoriaModel, Integer> {
    //Metodo para verificar si existe un Slug
    public boolean existsBySlug(String slug);//Preguntamos si existe un parametro tipo Slug
}
