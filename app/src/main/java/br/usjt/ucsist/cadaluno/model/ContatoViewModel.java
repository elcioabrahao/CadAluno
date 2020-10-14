package br.usjt.ucsist.cadaluno.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContatoViewModel extends AndroidViewModel {

    private ContatoRepository contatoRepository;
    private LiveData<List<Contato>> contatosResponseLiveData;
    private LiveData<Boolean> salvoComSucessoLiveData;

    public ContatoViewModel(@NonNull Application application) {
        super(application);
        Log.d("RESPOSTA","CRIACAO DA VIEWMODEL");
        contatoRepository = new ContatoRepository();
        contatosResponseLiveData = contatoRepository.getAllContatos();
        salvoComSucessoLiveData = contatoRepository.getSalvoSucesso();
    }

    public void getContatos() {
        contatoRepository.getContatos();
    }

    public LiveData<List<Contato>> getContatosResponseLiveData() {
        return contatosResponseLiveData;
    }

    public LiveData<Boolean> getSalvoSucesso() {
        return salvoComSucessoLiveData;
    }

    public void salvarContato(Contato contato){
        contatoRepository.salvarContato(contato);
    }

//    public void alterarContato(Contato contato){
//        contatoRepository.alterarContato(contato);
//    }
}
