package cl.tamila.repositorios;

import cl.tamila.modelos.ProductosModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRespositorio extends JpaRepository<ProductosModel,Integer> {
}
