package br.usjt.ucsist.cadaluno.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import br.usjt.ucsist.cadaluno.R;
import br.usjt.ucsist.cadaluno.model.Contato;
import br.usjt.ucsist.cadaluno.model.ContatoViewModel;
import br.usjt.ucsist.cadaluno.model.Usuario;
import br.usjt.ucsist.cadaluno.model.UsuarioViewModel;

public class ContatoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ContatoViewModel contatoViewModel;
    private Contato contatoCorrente;
    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextTelefone;
    private Button salvarContato;

    public ContatoFragment() {
        // Required empty public constructor
    }


    public static ContatoFragment newInstance(String param1, String param2) {
        ContatoFragment fragment = new ContatoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        contatoViewModel = new ViewModelProvider(this).get(ContatoViewModel.class);
        contatoViewModel.getSalvoSucesso().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean sucesso) {
                String mensagem = "Contato falhou ao salvar!";
                if(sucesso){
                    mensagem = "Contato salvo com sucesso!";
                    limpar();
                }
                Toast.makeText(getActivity(),mensagem,Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void limpar(){
        editTextTelefone.setText("");
        editTextEmail.setText("");
        editTextNome.setText("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contato, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        contatoCorrente = new Contato();

        editTextNome = view.findViewById(R.id.editTextNomeC);
        editTextEmail = view.findViewById(R.id.editTextEmailC);
        editTextTelefone = view.findViewById(R.id.editTextTelefoneC);
        salvarContato = view.findViewById(R.id.buttonSalvarC);

        salvarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

    }

    public void salvar() {

        contatoCorrente.setNome(editTextNome.getText().toString());
        contatoCorrente.setEmail(editTextEmail.getText().toString());
        contatoCorrente.setTelefone(editTextTelefone.getText().toString());

        contatoViewModel.salvarContato(contatoCorrente);
    }
}