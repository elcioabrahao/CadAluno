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

public class ContatoRepository {

    private static final String CONTATOS_SERVICE_BASE_URL = "https://crudcrud.com";

    private ContatoService contatoService;
    private MutableLiveData<List<Contato>> contatosResponseMutableLiveData;
    private MutableLiveData<Boolean> salvoSucessoMutableLiveData;

    public ContatoRepository() {
        contatosResponseMutableLiveData = new MutableLiveData<>();
        salvoSucessoMutableLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        contatoService = new retrofit2.Retrofit.Builder()
                .baseUrl(CONTATOS_SERVICE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ContatoService.class);

    }

    public void getContatos() {
        contatoService.getAllContatos()
                .enqueue(new Callback<List<Contato>>() {
                    @Override
                    public void onResponse(Call<List<Contato>> call, Response<List<Contato>> response) {
                        if (response.body() != null) {
                            Log.d("RESPOSTA", "tenho resultato-->"+response.body());
                            contatosResponseMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Contato>> call, Throwable t) {
                        Log.e("RESPOSTA", "FALHOU->"+t.getMessage());
                        contatosResponseMutableLiveData.postValue(null);
                    }
                });
    }

    public LiveData<List<Contato>> getAllContatos() {
        return contatosResponseMutableLiveData;
    }

    public LiveData<Boolean> getSalvoSucesso() {
        return salvoSucessoMutableLiveData;
    }

    public void salvarContato(Contato contato){

        contatoService.salvarContato(contato)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.body() != null) {
                            Log.d("RESPOSTA", "tenho resultato-->"+response.body());
                            salvoSucessoMutableLiveData.postValue(new Boolean(true));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("RESPOSTA", "FALHOU->"+t.getMessage());
                        salvoSucessoMutableLiveData.postValue(new Boolean(false));
                    }
                });

    }

//    public void alterarContato(Contato contato){
//
//        ContatoPut contatoPut = new ContatoPut(contato.getNome(),contato.getEmail(),
//                contato.getTelefone());
//
//        contatoService.alterarContato(contato.getId(),contatoPut)
//                .enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        if (response.body() != null) {
//                            Log.d("RESPOSTA", "tenho resultato-->"+response.body());
//                            salvoSucessoMutableLiveData.postValue(new Boolean(true));
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.e("RESPOSTA", "FALHOU->"+t.getMessage());
//                        salvoSucessoMutableLiveData.postValue(new Boolean(false));
//                    }
//                });
//    }

    public Call<ResponseBody> deletarContato(Contato contato){
        return contatoService.deletarContato(contato.getId());
    }


}
