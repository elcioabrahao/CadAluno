package br.usjt.ucsist.cadaluno.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.usjt.ucsist.cadaluno.R;
import br.usjt.ucsist.cadaluno.model.Contato;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.ContatoHolder> {
    private List<Contato> results = new ArrayList<>();
    private static ItemClickListener itemClickListener;

    @NonNull
    @Override
    public ContatoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contato, parent, false);

        return new ContatoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContatoHolder holder, int position) {
        Contato contato = results.get(position);

        holder.textViewNome.setText(contato.getNome());
        holder.textViewEmail.setText(contato.getEmail());
        holder.textViewTelefone.setText(contato.getTelefone());



    }
    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Contato> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    class ContatoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewNome;
        private TextView textViewEmail;
        private TextView textViewTelefone;


        public ContatoHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNomeContato);
            textViewEmail = itemView.findViewById(R.id.textViewEmailContato);
            textViewTelefone = itemView.findViewById(R.id.textViewTelefoneContato);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener != null) {
                itemClickListener.onItemClick(getAdapterPosition(), results.get(getAdapterPosition()));
            }
        }
    }
    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position, Contato contato);
    }
}