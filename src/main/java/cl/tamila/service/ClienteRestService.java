package cl.tamila.service;

import cl.tamila.modelos.CategoriaRestModel;
import cl.tamila.modelos.ProductosRestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Primary
public class ClienteRestService {
    @Autowired
    private RestTemplate clienteRest;
    @Value("${eduardo.valores.api}")
    private String apiHost;

    public ClienteRestService(RestTemplateBuilder builder){
        this.clienteRest=builder.build();//Inicializamos el objeto de nuestro cliente REST
    }
    private HttpHeaders setHeaders(){
        HttpHeaders headers=new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Authorization","");//Falta tocker de autorizacion
        return headers;
    }
    public List<ProductosRestModel> listar(){
        HttpEntity<String> entity=new HttpEntity<String>(this.setHeaders());
        ResponseEntity<List<ProductosRestModel>> response=this.clienteRest.exchange(apiHost+"productos",HttpMethod.GET,entity,new ParameterizedTypeReference<List<ProductosRestModel>>(){
        });
        return response.getBody();
    }
}
