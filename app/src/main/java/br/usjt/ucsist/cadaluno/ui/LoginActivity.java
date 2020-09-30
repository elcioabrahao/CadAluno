package br.usjt.ucsist.cadaluno.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import br.usjt.ucsist.cadaluno.R;
import br.usjt.ucsist.cadaluno.model.Usuario;
import br.usjt.ucsist.cadaluno.model.UsuarioViewModel;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private TextView textViewNovoCadastro;
    private UsuarioViewModel usuarioViewModel;
    private Usuario usuarioCorrente;
    private EditText editTextEmail;
    private EditText editTextSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Hawk.init(this).build();

        editTextEmail = findViewById(R.id.editTextUsuario);
        editTextSenha = findViewById(R.id.editTextSenha);

        buttonLogin = findViewById(R.id.buttonLogin);
        textViewNovoCadastro = findViewById(R.id.textViewNovoCadastro);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.getUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(@Nullable final Usuario usuario) {
                updateUsuario(usuario);
            }
        });


    }

    private void updateUsuario(Usuario usuario){
        this.usuarioCorrente = usuario;
    }


    @Override
    public void onResume(){
        super.onResume();
        if(Hawk.contains("tem_cadastro")){
            if(Hawk.get("tem_cadastro")){
                desbloquear();
            }else{
                bloquear();
            }
        }else{
            bloquear();
        }
    }

    private void bloquear(){
        buttonLogin.setEnabled(false);
        buttonLogin.setBackgroundColor(getResources().getColor(R.color.cinza));
        textViewNovoCadastro.setVisibility(View.VISIBLE);
    }

    private void desbloquear(){
        buttonLogin.setEnabled(true);
        buttonLogin.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        textViewNovoCadastro.setVisibility(View.GONE);
    }

    public void novoCadastro(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void login(View view) {

        if(usuarioCorrente != null){
            String usuario = editTextEmail.getText().toString();
            String senha = editTextSenha.getText().toString();
            if(usuario.equalsIgnoreCase(usuarioCorrente.getEmail())
            && senha.equals(usuarioCorrente.getSenha())){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                editTextSenha.setText("");
            }else {
                Toast.makeText(this,"Usuário ou Senha Inválidos!",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }
}