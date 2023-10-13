package cl.tamila.utilidades;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class Utilidades {
    public static String guardarArchivo(MultipartFile multiPart, String ruta){
        //Con esto obtenemos el Metadata del archivo que se ingresa al servidor
        if (Utilidades.validarImagen(multiPart.getContentType())==false){//Con multipart.getContentType, obtenemos el mimetype
            return "no";
        }else {//Retornando nombre del archivo
            long time=System.currentTimeMillis();//Obtenemos la hora y fecha
            String nombreDeImagen=time+Utilidades.getExtencion(multiPart.getContentType());
            try{
                File imageFile = new File(ruta+nombreDeImagen);
                multiPart.transferTo(imageFile);
                return nombreDeImagen;
            }catch (IOException e){
                return null;
            }
        }
    }
    //Metodo para validar con que formato de imagenes solo se permiten, de tipo mime type
    public static boolean validarImagen(String mime){
        boolean retorno;
        switch (mime){//Verificacion de formatos
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
    //Slug, Convertimos una cadena de texto en Slug
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");
    public static String getSlug(String input){
        String nowhitespace=WHITESPACE.matcher(input).replaceAll("-");
        String normalized= Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug=NONLATIN.matcher(normalized).replaceAll("");
        slug=EDGESDHASHES.matcher(slug).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
