package br.com.unikasistemas.desafioestagio.model;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PessoaBeanList {

    public List<PessoaBean> getPessoaBeanListGeral(List<Pessoa> pessoa){
        List<PessoaBean> pessoaBeanList = new ArrayList<>();
        for(Pessoa p : pessoa) {
            pessoaBeanList.add(produce(p));
        }
        return pessoaBeanList;
    }

    public PessoaBean produce(Pessoa pessoa){
        PessoaBean pessoaBean = new PessoaBean();
        pessoaBean.setTipoPessoa(pessoa.getTipoPessoa() == 1 ? "Pessoa Jurídica" : "Pessoa Física");
        pessoaBean.setCpfCnpj(pessoa.getCpfCnpj());
        pessoaBean.setRazaoSocial(pessoa.getRazaoSocial());
        pessoaBean.setEnderecosBean(new JRBeanCollectionDataSource(pessoa.getEnderecos().stream().map(EnderecoBean::new).collect(Collectors.toList())));
        return pessoaBean;
    }

}
