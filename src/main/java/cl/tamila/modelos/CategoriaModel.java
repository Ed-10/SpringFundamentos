package cl.tamila.modelos;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

//Modelo para la tabla Categoria de la BD
//La serializacion no es algo que pueda ser obligatorio en casos variados
@Entity
@Table(name = "categoria")//Nombre de nuestra tabla en la BD
public class CategoriaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Esta Vacio")
    private String nombre;
    private String slug;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getSlug() {
        return slug;
    }
    public void setSlug(String slug) {
        this.slug = slug;
    }
}
