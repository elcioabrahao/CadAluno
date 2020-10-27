package br.usjt.ucsist.cadaluno.ui;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import br.usjt.ucsist.cadaluno.R;
import br.usjt.ucsist.cadaluno.model.Contato;
import br.usjt.ucsist.cadaluno.model.ContatoViewModel;


public class HomeFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ContatoViewModel contatoViewModel;
    private List<Contato> contatos;
    private ContatoAdapter adapter;
    private ProgressBar progressBar;

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ContatoAdapter();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        contatoViewModel = new ViewModelProvider(this).get(ContatoViewModel.class);
        contatoViewModel.getContatosResponseLiveData().observe(this, new Observer<List<Contato>>() {
            @Override
            public void onChanged(List<Contato> contatosList) {
                if (contatosList != null) {
                    adapter.setResults(contatosList);
                }
                progressBar.setVisibility(View.GONE);
            }
        });

        adapter.setOnItemClickListener(new ContatoAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position, Contato contato) {
                replaceFragment(R.id.frameLayout,
                        ContatoFragment.newInstance("",contato),
                        "CONTATOFRAGMENT",
                        "contato_click");
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewContatos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        contatoViewModel.getContatos();
    }

    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag,
                                   @Nullable String backStackStateName) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        progressBar = view.findViewById(R.id.progressBar);
    }
}