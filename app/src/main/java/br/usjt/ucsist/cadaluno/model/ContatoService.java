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

    @GET("/api/bba2b794e8254c85b1d5a4cdb6adc8ce/contato2")
    Call<List<Contato>> getAllContatos();

    @POST("/api/bba2b794e8254c85b1d5a4cdb6adc8ce/contato2")
    Call<ResponseBody> salvarContato(
            @Body
                    Contato contato);

//    @PUT("/api/bba2b794e8254c85b1d5a4cdb6adc8ce/contato2/{id}")
//    Call<ResponseBody> alterarContato(
//            @Path("id") String id,
//            @Body ContatoPut contatoPut);

    @DELETE("/api/bba2b794e8254c85b1d5a4cdb6adc8ce/contato2/{id}")
    Call<ResponseBody> deletarContato(
            @Path("id") String id);
}
