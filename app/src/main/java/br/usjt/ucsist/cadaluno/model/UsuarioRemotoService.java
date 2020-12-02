package br.usjt.ucsist.cadaluno.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioRemotoService {

    @GET("/api/usuarios")
    Call<List<UsuarioRemoto>> getAllUsuariosRemotos(@HeaderMap HashMap<String, String> headers);

    @POST("/api/usuario/novo")
    Call<UsuarioRemoto> salvarUsuarioRemoto(
            @Body
                    UsuarioRemoto usuarioRemoto);

    @POST("/api/usuario/autenticar")
    Call<UsuarioRemoto> autenticarUsuarioRemoto(
            @HeaderMap HashMap<String, String> headers,
            @Body
                    UsuarioRemoto usuarioRemoto);

    @PUT("/api/usuario/{id}")
    Call<UsuarioRemoto> alterarUsuarioRemoto(
            @HeaderMap HashMap<String, String> headers,
            @Path("id") Long id,
            @Body UsuarioRemoto usuarioRemoto);

    @DELETE("/api/usuario/{id}")
    Call<ResponseBody> deletarUsuarioRemoto(
            @Path("id") Long id);

    @POST("/login")
    Call<ResponseBody> logar(
            @Body
                    UsuarioRemoto usuarioRemoto);
}
