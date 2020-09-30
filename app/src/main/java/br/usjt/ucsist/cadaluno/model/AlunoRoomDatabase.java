package br.usjt.ucsist.cadaluno.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Usuario.class}, version = 1, exportSchema = false)
public abstract class AlunoRoomDatabase extends RoomDatabase {

    public abstract UsuarioDao usuarioDao();
    private static volatile AlunoRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static AlunoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AlunoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AlunoRoomDatabase.class, "aluno_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}