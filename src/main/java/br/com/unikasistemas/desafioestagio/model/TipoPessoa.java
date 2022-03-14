package br.com.unikasistemas.desafioestagio.model;

import org.apache.wicket.model.IModel;

public enum TipoPessoa implements IModel<Object> {

//    ESCOLHA(0, "Escolha"),
    JURIDICA(1, "Pessoa Jurídica"),
    FISICA(2,"Pessoa Física");

    private final Integer id;
    private final String label;

    public Integer getId() {
        return id;
    }

    private TipoPessoa(Integer id,String label){
        this.label = label;
        this.id = id;
    }

    public String getLabel(){
        return label;
    }

    public static String getLabel(Integer id){
        for (TipoPessoa value : values()) {
            if (value.getId().equals(id)){
                return value.getLabel();
            }
        }

        return null;
    }

    @Override
    public Object getObject() {
        return null;
    }

    @Override
    public void setObject(Object object) {

    }

    @Override
    public void detach() {

    }
}


