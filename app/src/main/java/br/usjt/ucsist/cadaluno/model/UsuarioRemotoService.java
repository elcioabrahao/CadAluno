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

public interface UsuarioRemotoService {

    @GET("/api/usuarios")
    Call<List<UsuarioRemoto>> getAllUsuariosRemotos();

    @POST("/api/usuario")
    Call<UsuarioRemoto> salvarUsuarioRemoto(
            @Body
                    UsuarioRemoto usuarioRemoto);

    @PUT("/api/usuario/{id}")
    Call<UsuarioRemoto> alterarUsuarioRemoto(
            @Path("id") Long id,
            @Body UsuarioRemoto usuarioRemoto);

    @DELETE("/api/usuario/{id}")
    Call<ResponseBody> deletarUsuarioRemoto(
            @Path("id") Long id);
}
