package br.com.cast.turmaformacao.taskmanager.model.http;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.cast.turmaformacao.taskmanager.model.entities.Address;

public final class AddressService {

    private static final String URL = "http://correiosapi.apphb.com/cep/";

    private AddressService(){
        super();
    }

    public static Address getAdressByZipCode(String zipCode){

        Address address = null;

        try{
            java.net.URL url = new URL(URL + zipCode);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json"); //So aceita uma resposta em JSON
            int responseCode = conn.getResponseCode();//Executa o get e retorna o codigo de resposta
            Log.i("getAdressByZipCode",
                    "Código de retorno da requisição do cep: " + responseCode);

            if(responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Error code: " + responseCode);
            }

            InputStream inputStream = conn.getInputStream();//Recupera o body, dentro do InputStream fica os bytes do JSON

            ObjectMapper objectMapper = new ObjectMapper();//Resposável por dar acesso a biblioteca do jackson
            address = objectMapper.readValue(inputStream, Address.class);

            conn.disconnect();//Desconecta do servidor
        }
        catch (IOException e){
            Log.e(AddressService.class.getName(), e.getMessage());
        }

        return null;
    }

}
