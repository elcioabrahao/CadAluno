package br.usjt.ucsist.cadaluno.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuarioRemotoRepository {

    private static final String USUARIO_SERVICE_BASE_URL = "http://10.0.2.2:8080";

    private UsuarioRemotoService usuarioRemotoService;
    private MutableLiveData<List<UsuarioRemoto>> usuarioRemotosResponseMutableLiveData;
    private MutableLiveData<UsuarioRemoto> salvoSucessoMutableLiveData;
    private MutableLiveData<UsuarioRemoto> autenticadoSucessoMutableLiveData;
    private MutableLiveData<String> tokenMutableLiveData;

    public UsuarioRemotoRepository() {
        usuarioRemotosResponseMutableLiveData = new MutableLiveData<>();
        salvoSucessoMutableLiveData = new MutableLiveData<>();
        autenticadoSucessoMutableLiveData = new MutableLiveData<>();
        tokenMutableLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        usuarioRemotoService = new retrofit2.Retrofit.Builder()
                .baseUrl(USUARIO_SERVICE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UsuarioRemotoService.class);

    }

    public void getUsuarioRemotos(HashMap<String,String> headers) {
        usuarioRemotoService.getAllUsuariosRemotos(headers)
                .enqueue(new Callback<List<UsuarioRemoto>>() {
                    @Override
                    public void onResponse(Call<List<UsuarioRemoto>> call, Response<List<UsuarioRemoto>> response) {
                        if (response.body() != null) {
                            Log.d("RESPOSTA", "tenho resultato-->"+response.body());
                            usuarioRemotosResponseMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UsuarioRemoto>> call, Throwable t) {
                        Log.e("RESPOSTA", "FALHOU->"+t.getMessage());
                        usuarioRemotosResponseMutableLiveData.postValue(null);
                    }
                });
    }

    public LiveData<List<UsuarioRemoto>> getAllUsuarioRemotos() {
        return usuarioRemotosResponseMutableLiveData;
    }

    public LiveData<UsuarioRemoto> getSalvoSucesso() {
        return salvoSucessoMutableLiveData;
    }

    public LiveData<UsuarioRemoto> getAutenticadoSucesso() {
        return autenticadoSucessoMutableLiveData;
    }

    public LiveData<String> getToken() {
        return tokenMutableLiveData;
    }

    public void salvarUsuarioRemoto(UsuarioRemoto usuarioRemoto){

        usuarioRemotoService.salvarUsuarioRemoto(usuarioRemoto)
                .enqueue(new Callback<UsuarioRemoto>() {
                    @Override
                    public void onResponse(Call<UsuarioRemoto> call, Response<UsuarioRemoto> response) {
                        if (response.body() != null) {
                            Log.d("RESPOSTA", "tenho resultato-->"+response.body());
                            salvoSucessoMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<UsuarioRemoto> call, Throwable t) {
                        Log.e("RESPOSTA", "FALHOU->"+t.getMessage());
                        salvoSucessoMutableLiveData.postValue(null);
                    }
                });

    }

    public void autenticarUsuarioRemoto(HashMap<String,String> headers, UsuarioRemoto usuarioRemoto){

        usuarioRemotoService.autenticarUsuarioRemoto(headers, usuarioRemoto)
                .enqueue(new Callback<UsuarioRemoto>() {
                    @Override
                    public void onResponse(Call<UsuarioRemoto> call, Response<UsuarioRemoto> response) {
                        if (response.body() != null) {
                            Log.d("RESPOSTA", "tenho resultato-->"+response.body());
                            autenticadoSucessoMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<UsuarioRemoto> call, Throwable t) {
                        Log.e("RESPOSTA", "FALHOU->"+t.getMessage());
                        autenticadoSucessoMutableLiveData.postValue(null);
                    }
                });

    }

    public void alterarUsuarioRemoto(HashMap<String,String> headers,UsuarioRemoto usuarioRemoto){

        usuarioRemotoService.alterarUsuarioRemoto(headers,usuarioRemoto.getId(),usuarioRemoto)
                .enqueue(new Callback<UsuarioRemoto>() {
                    @Override
                    public void onResponse(Call<UsuarioRemoto> call, Response<UsuarioRemoto> response) {
                        if (response.body() != null) {
                            Log.d("RESPOSTA", "tenho resultato-->"+response.body());
                            salvoSucessoMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<UsuarioRemoto> call, Throwable t) {
                        Log.e("RESPOSTA", "FALHOU->"+t.getMessage());
                        salvoSucessoMutableLiveData.postValue(null);
                    }
                });
    }

    public Call<ResponseBody> deletarUsuarioRemoto(UsuarioRemoto usuarioRemoto){
        return usuarioRemotoService.deletarUsuarioRemoto(usuarioRemoto.getId());
    }

    public void logar(UsuarioRemoto usuarioRemoto){

        usuarioRemotoService.logar(usuarioRemoto)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.body() != null) {
                            tokenMutableLiveData.postValue(response.headers().get("Authorization"));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        tokenMutableLiveData.postValue(null);
                    }
                });

    }


}

