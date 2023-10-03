package cl.tamila.modelos;

import javax.persistence.*;
//import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "productos")
public class ProductosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Vacio")
    private String nombre;
    private String slug;
    @NotEmpty(message = "Vacio")
    private String descripcion;
    @NotNull(message = "No puede ser NUll")//El valor del precio no sea NULO
    @Min(5)//Asignamos un vamor minimo
    //@Max(5000)Se puede asignar un valor maximo
    private String precio;
    private String foto;
    //Añadiendo RELACION con nuestro modelo CategoriaModel, relacion de UNO a MUCHOS con OBJETO CategoriaModel
    @OneToOne
    @JoinColumn(name = "categoria_id")//
    private CategoriaModel categoriaId;
    ///////////////////////////////////////////////////////
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
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getPrecio() {
        return precio;
    }
    public void setPrecio(String precio) {
        this.precio = precio;
    }
    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
    public CategoriaModel getCategoriaId() {
        return categoriaId;
    }
    public void setCategoriaId(CategoriaModel categoriaId) {
        this.categoriaId = categoriaId;
    }
}
