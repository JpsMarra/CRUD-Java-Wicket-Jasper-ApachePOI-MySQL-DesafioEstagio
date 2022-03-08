package br.com.unikasistemas.desafioestagio.model;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;


@Entity(name = "Endereco")
@Table(name = "endereco")
//@DiscriminatorValue(value="ENDERECO")
public class Endereco implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ENDERECO", nullable = false)
    private Long idEndereco;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PESSOA_ID", referencedColumnName="ID_PESSOA", nullable=false)
    private Pessoa pessoa;

    @Column(name = "CEP")
    private String cep;
    @Column(name = "LOGRADOURO")
    private String logradouro;
    @Column(name = "BAIRRO")
    private String bairro;
    @Column(name = "NUMERO")
    private String numero;
    @Column(name = "COMPLEMENTO")
    private String complemento;
    @Column(name = "CIDADE")
    private String cidade;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "ENDERECO_PRINCIPAL")
    private boolean enderecoPrincipal;

    public String getEnderecoPrincipalNome(){
        if(enderecoPrincipal)
            return "Sim";
        if(!enderecoPrincipal)
            return "Não";
        return "";
    }

    public Long getIdEndereco() {
        return idEndereco;
    }

    public Pessoa getPessoa(){
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public void setIdEndereco(Long idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isEnderecoPrincipal() {
        return enderecoPrincipal;
    }

    public void setEnderecoPrincipal(boolean enderecoPrincipal) {
        this.enderecoPrincipal = enderecoPrincipal;
    }
}
