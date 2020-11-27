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
import br.usjt.ucsist.cadaluno.model.UsuarioRemoto;
import br.usjt.ucsist.cadaluno.model.UsuarioViewModel;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private TextView textViewNovoCadastro;
    private UsuarioViewModel usuarioViewModel;
    private Usuario usuarioCorrente;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private UsuarioRemoto ur;


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


        usuarioViewModel.getToken().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String token) {

                if(token!=null){

                    Hawk.put("token",token);
                    usuarioViewModel.autenticarUsuarioRemoto(ur);

                }else{
                    Toast.makeText(LoginActivity.this,"Usuário ou senha incorretos",Toast.LENGTH_SHORT).show();
                }


            }
        });


        usuarioViewModel.getAutenticadoSucesso().observe(this, new Observer<UsuarioRemoto>() {
            @Override
            public void onChanged(@Nullable final UsuarioRemoto usuarioRemoto) {

                if(usuarioRemoto!=null){

                    usuarioCorrente = new Usuario();
                    usuarioCorrente.setIdRemoto(usuarioRemoto.getId());

                    Hawk.put("idRemoto",usuarioRemoto.getId());
                    usuarioViewModel.insert(usuarioCorrente);
                    Hawk.put("tem_cadastro", true);
                    Toast.makeText(LoginActivity.this,"Autenticado remoto com sucesso!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    editTextSenha.setText("");
                }else{
                    Toast.makeText(LoginActivity.this,"Usuário ou senha incorretos",Toast.LENGTH_SHORT).show();
                }


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
        textViewNovoCadastro.setVisibility(View.VISIBLE);
    }

    private void desbloquear(){
        textViewNovoCadastro.setVisibility(View.GONE);
    }

    public void novoCadastro(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void login(View view) {

        String usuario = editTextEmail.getText().toString();
        String senha = editTextSenha.getText().toString();

        if(usuarioCorrente != null){
            if(usuario.equalsIgnoreCase(usuarioCorrente.getEmail())
            && senha.equals(usuarioCorrente.getSenha())){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                editTextSenha.setText("");
            }else {
                Toast.makeText(this,"Usuário ou Senha Inválidos!",
                        Toast.LENGTH_SHORT).show();
            }
        }else{



            ur = new UsuarioRemoto();
            ur.setEmail(usuario);
            ur.setSenha(senha);
            usuarioViewModel.logar(ur);



            //usuarioViewModel.autenticarUsuarioRemoto(ur);
        }
    }
}