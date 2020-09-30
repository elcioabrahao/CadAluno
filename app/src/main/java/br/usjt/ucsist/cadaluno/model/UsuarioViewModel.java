package br.usjt.ucsist.cadaluno.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UsuarioViewModel extends AndroidViewModel {

    public UsuarioRepository usuarioRepository;

    public LiveData<Usuario> usuario;

    public UsuarioViewModel (Application application) {
        super(application);
        usuarioRepository = new UsuarioRepository(application);
        usuario = usuarioRepository.getUsuario();
    }

    public LiveData<Usuario> getUsuario() { return usuario; }

    public void insert(Usuario usuario) { usuarioRepository.insert(usuario); }

}
