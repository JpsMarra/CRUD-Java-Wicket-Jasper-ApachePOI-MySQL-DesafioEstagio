package br.com.unikasistemas.desafioestagio.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Pessoa")
@Table(name = "pessoa")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Pessoa implements Serializable {

    @OneToMany(mappedBy = "pessoa", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos;

    @Id
    @Column(name = "ID_PESSOA")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPessoa;
    @Column(name = "TIPO_PESSOA")
    private TipoPessoa tipoPessoa;
    @Column(name = "CPFCNPJ")
    private String cpfCnpj;
    @Column(name = "RAZAO_SOCIAL")
    private String razaoSocial;
    @Column(name = "NOME_ALTERNATIVO")
    private String nomeAlternativo;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "RG")
    private String rg;
    @Column(name = "TELEFONE")
    private String telefone;
    @Column(name = "INSCRICAO_ESTADUAL")
    private String inscricaoEstadual;
    @Column(name = "DATA_NASCIMENTO")
    private String dataNascimento;
    @Column(name = "ATIVO")
    private boolean ativo;

    public String getAtivoNome(){
        if(ativo)
            return "Ativo";
        if(!ativo)
            return "Inativo";
    return "";
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
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

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public String getNomeAlternativo() {
        return nomeAlternativo;
    }

    public void setNomeAlternativo(String nomeAlternativo) {
        this.nomeAlternativo = nomeAlternativo;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public void addEndereco(Endereco endereco){
        if(this.enderecos == null){
            this.enderecos = new ArrayList<Endereco>();
        }
        enderecos.add(endereco);
    }

    @Override
    public String toString(){
        return "{Contato: idPessoa='" + idPessoa + "'tipoPessoa='" + tipoPessoa + "' cpfCnpj='" + cpfCnpj + "' razaoSocial='" +
                razaoSocial + "'nomeAlternativo='" + nomeAlternativo + "'email='" + email + "'rg='" + rg + "' telefone='" +
                telefone + "' inscricaoEstadual='" + inscricaoEstadual + "' dataNascimento='" +
                dataNascimento + "' ativo='" + ativo + "'}";

    }

}

