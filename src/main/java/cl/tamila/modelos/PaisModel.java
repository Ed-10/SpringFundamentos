package cl.tamila.modelos;

public class PaisModel {
    public Integer id;
    public String nombre;

    public PaisModel() {
        super();
    }

    public PaisModel(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

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
}
