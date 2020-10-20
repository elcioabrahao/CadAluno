package br.usjt.ucsist.cadaluno.model;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ContatoService {

    @GET("/api/ff8408fcadb0418ca299e901626b2b5b/contato")
    Call<List<Contato>> getAllContatos();

    @POST("/api/ff8408fcadb0418ca299e901626b2b5b/contato")
    Call<ResponseBody> salvarContato(
            @Body
                    Contato contato);

    @PUT("/api/ff8408fcadb0418ca299e901626b2b5b/contato/{id}")
    Call<ResponseBody> alterarContato(
            @Path("id") String id,
            @Body ContatoPut contatoPut);

    @DELETE("/api/ff8408fcadb0418ca299e901626b2b5b/contato/{id}")
    Call<ResponseBody> deletarContato(
            @Path("id") String id);
}
