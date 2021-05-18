package com.example.set_images;

public class Local {

    private String nome, endereco, foto;
    private int cetagoria;
    public Local(){

    }

    public Local(String nome, int cetagoria, String endereco, String foto) {

        if (nome.trim().equals("")){

            nome = "Sem Nome";

        }
        this.nome = nome;
        this.cetagoria = cetagoria;
        this.endereco = endereco;
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCetagoria() {
        return cetagoria;
    }

    public void setCetagoria(int cetagoria) {
        this.cetagoria = cetagoria;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
