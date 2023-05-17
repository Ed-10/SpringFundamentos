package cl.tamila.utilidades;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class Utilidades {
    public static String guardarArchivo(MultipartFile multipart, String ruta){
        //Con esto obtenemos el Metadata del archivo que se ingresa al servidor
        if (Utilidades.validaImagen(multipart.getContentType())==false){
            return "No";
        }else {
            long time=System.currentTimeMillis();
            String nombre=time+Utilidades.getExtencion(multipart.getContentType());
            try{
                File imageFile = new File(ruta+nombre);
                multipart.transferTo(imageFile);
                return nombre;
            }catch (IOException e){
                return null;
            }
        }
    }
    //Metodo para validad con que formato de imagenes solo se permiten
    public static boolean validaImagen(String mime){
        boolean retorno=false;
        switch (mime){
            case "image/jpeg":
                retorno=true;
                break;
            case "image/jpg":
                retorno=true;
                break;
            case "image/png":
                retorno=true;
                break;
            default:
                retorno=false;
                break;
        }
        return retorno;
    }
    //Metodo para verificar la extencion de estos
    public static String getExtencion(String mime){
        String retorno="";
        switch (mime){
            case "image/jpeg":
                retorno=".jpeg";
                break;
            case "image/jpg":
                retorno=".jpg";
                break;
            case "image/png":
                retorno=".png";
                break;
        }
        return retorno;

    }
}
