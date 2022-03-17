package br.com.unikasistemas.desafioestagio.model;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.Serializable;
import java.util.List;

public class PessoaBean implements Serializable {

    private String tipoPessoa;
    private String cpfCnpj;
    private String razaoSocial;
    private JRBeanCollectionDataSource enderecosBean;


    public JRBeanCollectionDataSource getEnderecosBean() {
        return enderecosBean;
    }

    public void setEnderecosBean(JRBeanCollectionDataSource enderecosBean) {
        this.enderecosBean = enderecosBean;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }


}

