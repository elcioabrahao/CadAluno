package br.usjt.ucsist.cadaluno.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

public class UsuarioRepository {

    private UsuarioDao usuarioDao;
    private LiveData<Usuario> usuario;

    UsuarioRepository(Application application) {
        AlunoRoomDatabase db = AlunoRoomDatabase.getDatabase(application);
        usuarioDao = db.usuarioDao();
        usuario = usuarioDao.getUsuario();
    }
    LiveData<Usuario> getUsuario() {
        return usuario;
    }
    void insert(Usuario usuario) {
        AlunoRoomDatabase.databaseWriteExecutor.execute(() -> {
            usuarioDao.insert(usuario);
        });
    }
}