package cl.tamila.modelos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class UsuarioCheckboxModel {
    @NotEmpty(message = "Esta vacio")
    private String username;
    @NotEmpty(message = "Esta vacio")
    @Email(message = "Ingresado no es valido")
    private String correo;
    @NotEmpty(message = "Esta vacio")
    private String password;

    //Integrando relacion con el modelo de intereses (Integrating relationship with the interest model)
    //No se sombra directamente el modelo IntesesModel, se coloca dentro de List por tratarse de N registros
    private List<InteresesModel> intereses;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<InteresesModel> getIntereses() {
        return intereses;
    }

    public void setIntereses(List<InteresesModel> intereses) {
        this.intereses = intereses;
    }
}
