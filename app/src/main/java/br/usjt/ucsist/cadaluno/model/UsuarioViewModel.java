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

    public LiveData<Usuario> usuario;

    public UsuarioViewModel (Application application) {
        super(application);
        usuarioRepository = new UsuarioRepository(application);
        usuario = usuarioRepository.getUsuario();
        usuarioRemotoRepository = new UsuarioRemotoRepository();
        usuarioRemotosResponseLiveData = usuarioRemotoRepository.getAllUsuarioRemotos();
        salvoComSucessoLiveData = usuarioRemotoRepository.getSalvoSucesso();
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

    public void salvarUsuarioRemoto(UsuarioRemoto usuarioRemoto){
        usuarioRemotoRepository.salvarUsuarioRemoto(usuarioRemoto);
    }

    public void alterarUsuarioRemoto(UsuarioRemoto usuarioRemoto){
        usuarioRemotoRepository.alterarUsuarioRemoto(usuarioRemoto);
    }

}
