package br.com.unikasistemas.desafioestagio.model;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.*;

public class CriaPdfReport {

    String enderecoReport = "src/main/java/br/com/unikasistemas/desafioestagio/model/Enderecos.jasper";
    String pessoaReport = "src/main/java/br/com/unikasistemas/desafioestagio/model/Pessoa.jrxml";

    PessoaBeanList pessoaBeanList = new PessoaBeanList();

    public byte[] criaPdfReport(List<Pessoa> pessoas) {


        List<PessoaBean> listaPessoas = pessoaBeanList.getPessoaBeanListGeral(pessoas);

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listaPessoas);

        try {
            /* Compile the master and sub report */
            JasperReport jasperPessoaReport = JasperCompileManager.compileReport(pessoaReport);

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("subreportDIR", enderecoReport);

            return JasperExportManager.exportReportToPdf(JasperFillManager.fillReport(jasperPessoaReport, parameters, beanColDataSource));

        } catch (JRException e) {
            e.printStackTrace();
            return null;
        }
    }

}


