package br.com.unikasistemas.desafioestagio.model;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.hibernate.PersistentObjectException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportPdf {

    String masterReportFileName = "C://tools/jasperreports-5.0.1/test"
            + "/jasper_report_template.jrxml";
    String subReportFileName = "C://tools/jasperreports-5.0.1/test"
            + "/AddressReport.jrxml";

    public byte[] reportPdf(List<Pessoa> pessoas){

        PessoaEndereco pessoaEndereco = new PessoaEndereco();
        ArrayList<Pessoa> listaPessoas = pessoaEndereco.pessoasEnderecos();
            JRBeanCollectionDataSource beanColDataSource = new
                    JRBeanCollectionDataSource(pessoas);

      try {
        /* Compile the master and sub report */
        JasperReport jasperMasterReport = JasperCompileManager
                .compileReport(masterReportFileName);
        JasperReport jasperSubReport = JasperCompileManager
                .compileReport(subReportFileName);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("subreportParameter", jasperSubReport);
        JasperFillManager.fillReportToFile(jasperMasterReport, subReportFileName,
                parameters, beanColDataSource);

    } catch (
    JRException e) {
        e.printStackTrace();
    }
      System.out.println("Done filling!!! ...");
      return new byte[0];
    }

}
