package br.usjt.ucsist.cadaluno.ui;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.usjt.ucsist.cadaluno.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(R.id.frameLayout,
                HomeFragment.newInstance("",""),
                "HOMEFRAGMENT",
                "HOME");


        bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        replaceFragment(R.id.frameLayout,
                                HomeFragment.newInstance("",""),
                                "HOMEFRAGMENT",
                                "HOME");
                        return true;

                    case R.id.contatos:
                        replaceFragment(R.id.frameLayout,
                                ContatoFragment.newInstance("",""),
                                "CONTATOFRAGMENT",
                                "CONTATO");
                        return true;

                    case R.id.perfil:
                        replaceFragment(R.id.frameLayout,
                                PerfilFragment.newInstance(false,""),
                                "PERFILFRAGMENT",
                                "PERFIL");
                        return true;

                    case R.id.configuracao:
                        replaceFragment(R.id.frameLayout,
                                ConfiguracaoFragment.newInstance("",""),
                                "CONFIGURACAOFRAGMENT",
                                "CONFIGURACAO");
                        return true;


                }
                return false;
            }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.sair:
                finish();
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag,
                                   @Nullable String backStackStateName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }
}