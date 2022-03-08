package br.com.unikasistemas.desafioestagio.model;

public enum TipoPessoa {

    JURIDICA("Pessoa Jurídica"), FISICA("Pessoa Física");

    private final String label;

    private TipoPessoa(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }

}


