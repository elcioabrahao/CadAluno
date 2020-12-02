package br.usjt.ucsist.cadaluno.model;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ContatoService {

    @GET("/api/contatos/{id}")
    Call<List<Contato>> getAllContatosByRemoteId(
            @HeaderMap HashMap<String, String> headers,
            @Path("id") Long idRemoto
    );

    @POST("/api/contato")
    Call<ResponseBody> salvarContato(
            @HeaderMap HashMap<String, String> headers,
            @Body
                    Contato contato);

    @PUT("/api/contato/{id}")
    Call<ResponseBody> alterarContato(
            @HeaderMap HashMap<String, String> headers,
            @Path("id") Long id,
            @Body ContatoPut contatoPut);

    @DELETE("/api/contato/{id}")
    Call<ResponseBody> deletarContato(
            @Path("id") Long id);
}
