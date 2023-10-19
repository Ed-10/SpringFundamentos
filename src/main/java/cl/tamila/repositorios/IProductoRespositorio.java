package cl.tamila.repositorios;

import cl.tamila.modelos.CategoriaModel;
import cl.tamila.modelos.ProductosModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductoRespositorio extends JpaRepository<ProductosModel,Integer> {
    //Metodo para realizar consultas, en este caso de una categoria en especifico
    //A esto se le llama consulta de caracter compuesto
    List<ProductosModel> findAllBycategoriaId(CategoriaModel categoria);//Trallendo registros con nuestro filtro
}
