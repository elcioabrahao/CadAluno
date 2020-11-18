package br.usjt.ucsist.cadaluno.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    public UsuarioRemotoRepository() {
        usuarioRemotosResponseMutableLiveData = new MutableLiveData<>();
        salvoSucessoMutableLiveData = new MutableLiveData<>();

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

    public void getUsuarioRemotos() {
        usuarioRemotoService.getAllUsuariosRemotos()
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

    public void alterarUsuarioRemoto(UsuarioRemoto usuarioRemoto){

        usuarioRemotoService.alterarUsuarioRemoto(usuarioRemoto.getId(),usuarioRemoto)
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


}

