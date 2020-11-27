package br.usjt.ucsist.cadaluno.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UsuarioViewModel extends AndroidViewModel {

    public UsuarioRepository usuarioRepository;
    private UsuarioRemotoRepository usuarioRemotoRepository;
    private LiveData<List<UsuarioRemoto>> usuarioRemotosResponseLiveData;
    private LiveData<UsuarioRemoto> salvoComSucessoLiveData;
    private LiveData<UsuarioRemoto> autenticadoComSucessoLiveData;
    private LiveData<String> tokenGeradoComSucessoLiveData;

    public LiveData<Usuario> usuario;

    public UsuarioViewModel (Application application) {
        super(application);
        usuarioRepository = new UsuarioRepository(application);
        usuario = usuarioRepository.getUsuario();
        usuarioRemotoRepository = new UsuarioRemotoRepository();
        usuarioRemotosResponseLiveData = usuarioRemotoRepository.getAllUsuarioRemotos();
        salvoComSucessoLiveData = usuarioRemotoRepository.getSalvoSucesso();
        autenticadoComSucessoLiveData = usuarioRemotoRepository.getAutenticadoSucesso();
        tokenGeradoComSucessoLiveData = usuarioRemotoRepository.getToken();

    }

    public LiveData<Usuario> getUsuario() { return usuario; }

    public void insert(Usuario usuario) { usuarioRepository.insert(usuario); }

    public void getUsuarioRemotos() {
        usuarioRemotoRepository.getUsuarioRemotos();
    }

    public LiveData<List<UsuarioRemoto>> getUsuarioRemotosResponseLiveData() {
        return usuarioRemotosResponseLiveData;
    }

    public LiveData<UsuarioRemoto> getSalvoSucesso() {
        return salvoComSucessoLiveData;
    }

    public LiveData<UsuarioRemoto> getAutenticadoSucesso() {
        return autenticadoComSucessoLiveData;
    }

    public LiveData<String> getToken() {
        return tokenGeradoComSucessoLiveData;
    }

    public void salvarUsuarioRemoto(UsuarioRemoto usuarioRemoto){
        usuarioRemotoRepository.salvarUsuarioRemoto(usuarioRemoto);
    }

    public void autenticarUsuarioRemoto(UsuarioRemoto usuarioRemoto){
        usuarioRemotoRepository.autenticarUsuarioRemoto(usuarioRemoto);
    }

    public void alterarUsuarioRemoto(UsuarioRemoto usuarioRemoto){
        usuarioRemotoRepository.alterarUsuarioRemoto(usuarioRemoto);
    }

    public void logar(UsuarioRemoto usuarioRemoto){
        usuarioRemotoRepository.logar(usuarioRemoto);
    }

}
