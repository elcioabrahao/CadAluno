package br.usjt.ucsist.cadaluno.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ContatoPut implements Serializable {

    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("telefone")
    @Expose
    private String telefone;
    @SerializedName("imagem")
    @Expose
    private String imagem;
    @SerializedName("remoto")
    @Expose
    private Long remoto;

    public ContatoPut(String nome, String email, String telefone, String imagem, Long remoto) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.imagem = imagem;
        this.remoto = remoto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Long getRemoto() {
        return remoto;
    }

    public void setRemoto(Long remoto) {
        this.remoto = remoto;
    }
}

