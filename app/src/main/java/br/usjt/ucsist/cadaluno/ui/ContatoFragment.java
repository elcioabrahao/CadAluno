package br.usjt.ucsist.cadaluno.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import br.usjt.ucsist.cadaluno.R;
import br.usjt.ucsist.cadaluno.model.Contato;
import br.usjt.ucsist.cadaluno.model.ContatoViewModel;
import br.usjt.ucsist.cadaluno.model.Usuario;
import br.usjt.ucsist.cadaluno.model.UsuarioViewModel;
import br.usjt.ucsist.cadaluno.util.ImageUtil;

import static android.app.Activity.RESULT_OK;

public class ContatoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private Contato mParam2;
    private ContatoViewModel contatoViewModel;
    private Contato contatoCorrente;
    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextTelefone;
    private Button salvarContato;
    private TextView linkContato;
    private ImageView foto;

    public ContatoFragment() {
        // Required empty public constructor
    }


    public static ContatoFragment newInstance(String param1, Contato param2) {
        ContatoFragment fragment = new ContatoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = (Contato) getArguments().getSerializable(ARG_PARAM2);
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
        foto.setImageResource(R.drawable.ic_place_holder);
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
        linkContato = view.findViewById(R.id.linkContato);
        foto = view.findViewById(R.id.imagemContato);

        linkContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });

        if(mParam2 != null){
            contatoCorrente = mParam2;
            editTextNome.setText(contatoCorrente.getNome());
            editTextEmail.setText(contatoCorrente.getEmail());
            editTextTelefone.setText(contatoCorrente.getTelefone());
            foto.setImageBitmap(ImageUtil.decode(contatoCorrente.getImagem()));
        }

        salvarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

    }

    private void tirarFoto(){
        dispatchTakePictureIntent();
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            foto.setImageBitmap(imageBitmap);
            contatoCorrente.setImagem(ImageUtil.encode(imageBitmap));
        }

    }

    public void salvar() {

        contatoCorrente.setNome(editTextNome.getText().toString());
        contatoCorrente.setEmail(editTextEmail.getText().toString());
        contatoCorrente.setTelefone(editTextTelefone.getText().toString());
        contatoCorrente.setRemoto(Hawk.get("idRemoto",0L));

        if(mParam2 != null){
            contatoViewModel.alterarContato(contatoCorrente);
        }else{
            contatoViewModel.salvarContato(contatoCorrente);
        }


    }
}