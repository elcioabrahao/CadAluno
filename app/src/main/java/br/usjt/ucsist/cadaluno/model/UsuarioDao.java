package br.usjt.ucsist.cadaluno.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Usuario usuario);

    @Query("DELETE FROM usuario")
    void deleteAll();

    @Query("SELECT * from usuario LIMIT 1")
    LiveData<Usuario> getUsuario();

}
