package cl.tamila.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@Primary
public class ClienteRestService {
    @Autowired
    private RestTemplate clienteRest;
    @Value("{eduardo.valores.api}")
    private String apiHost;

    public ClienteRestService(RestTemplateBuilder builder){
        this.clienteRest=builder.build();//Inicializamos el objeto de nuestro cliente REST
    }
    /*private HttpHeaders setHeaders(){
        HttpHeaders headers=new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("",apiHost,"");
    }*/
}
