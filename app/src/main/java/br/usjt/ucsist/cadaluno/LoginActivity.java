package br.usjt.ucsist.cadaluno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private TextView textViewNovoCadastro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Hawk.init(this).build();

        buttonLogin = findViewById(R.id.buttonLogin);
        textViewNovoCadastro = findViewById(R.id.textViewNovoCadastro);

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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}