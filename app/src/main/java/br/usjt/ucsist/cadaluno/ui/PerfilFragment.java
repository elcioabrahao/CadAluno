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
import br.usjt.ucsist.cadaluno.model.Usuario;
import br.usjt.ucsist.cadaluno.model.UsuarioRemoto;
import br.usjt.ucsist.cadaluno.model.UsuarioViewModel;


public class PerfilFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private UsuarioViewModel usuarioViewModel;
    private Usuario usuarioCorrente;
    private EditText editTextNome;
    private EditText editTextCpf;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private Button salvarUsuario;

    private boolean mParam1;
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }


    public static PerfilFragment newInstance(boolean param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getBoolean(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Hawk.init(getActivity()).build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        editTextNome = view.findViewById(R.id.editTextNomeF);
        editTextCpf = view.findViewById(R.id.editTextCpfF);
        editTextEmail = view.findViewById(R.id.editTextEmailF);
        editTextSenha = view.findViewById(R.id.editTextSenhaF);
        salvarUsuario = view.findViewById(R.id.buttonSalvarF);

        salvarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

        usuarioCorrente = new Usuario();

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.getUsuario().observe(getActivity(), new Observer<Usuario>() {
            @Override
            public void onChanged(@Nullable final Usuario usuario) {
                updateView(usuario);
            }
        });

        usuarioViewModel.getSalvoSucesso().observe(getActivity(), new Observer<UsuarioRemoto>() {
            @Override
            public void onChanged(@Nullable final UsuarioRemoto usuarioRemoto) {
//                String mensagem = "Usuario Remoto falhou ao salvar!";
                if(usuarioRemoto!=null){
//                    mensagem = "Usuario Remoto salvo com sucesso!";

                    usuarioCorrente.setIdRemoto(usuarioRemoto.getId());

                    usuarioViewModel.insert(usuarioCorrente);

                    Hawk.put("tem_cadastro", true);
                    Toast.makeText(getActivity(),"Registro salvo com sucesso!",
                            Toast.LENGTH_SHORT).show();
                    if(mParam1){
                        getActivity().finish();
                    }
                }
//                Toast.makeText(getActivity(),mensagem,Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void updateView(Usuario usuario){
        if(usuario != null && usuario.getId() > 0){
            usuarioCorrente = usuario;
            editTextNome.setText(usuario.getNome());
            editTextCpf.setText(usuario.getCpf());
            editTextEmail.setText(usuario.getEmail());
            editTextSenha.setText(usuario.getSenha());
        }
    }

    public void salvar() {

        usuarioCorrente.setNome(editTextNome.getText().toString());
        usuarioCorrente.setCpf(editTextCpf.getText().toString());
        usuarioCorrente.setEmail(editTextEmail.getText().toString());
        usuarioCorrente.setSenha(editTextSenha.getText().toString());

        if(mParam1){
            // Novo cadastro
            usuarioViewModel.salvarUsuarioRemoto(new UsuarioRemoto(
                    usuarioCorrente.getNome(),
                    usuarioCorrente.getCpf(),
                    usuarioCorrente.getEmail(),
                    usuarioCorrente.getSenha()));
        }else{
            usuarioViewModel.alterarUsuarioRemoto(new UsuarioRemoto(
                    usuarioCorrente.getIdRemoto(),
                    usuarioCorrente.getNome(),
                    usuarioCorrente.getCpf(),
                    usuarioCorrente.getEmail(),
                    usuarioCorrente.getSenha()));
        }


    }

}