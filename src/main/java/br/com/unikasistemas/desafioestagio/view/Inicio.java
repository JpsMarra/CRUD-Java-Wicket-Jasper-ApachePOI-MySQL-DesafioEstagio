package br.com.unikasistemas.desafioestagio.view;

import br.com.unikasistemas.desafioestagio.controller.Confirmacao;
import br.com.unikasistemas.desafioestagio.controller.FormularioCriar;
import br.com.unikasistemas.desafioestagio.model.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;
import org.apache.wicket.util.resource.IResourceStream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Inicio extends HomePage {

    public ModalWindow modalCriar;
    public FormularioCriar formCriar;
    public ModalWindow modalConfirmacao;
    public Confirmacao confirmacao;
    public WebMarkupContainer containerResultados;
    public Form<Pessoa> formularioFiltrar;
    public DropDownChoice<Integer> pesquisaTipoPessoa;
    public DropDownChoice<Boolean> pesquisaAtivo;
    public TextField<String> pesquisaRazaoSocial;
    public TextField<String> pesquisaCpfCnpj;
    public List<Endereco> enderecos;
    public Pessoa pessoa;
    public AjaxDownloadBehavior ajaxDownloadBehavior;
    public AjaxDownloadBehavior ajaxDownloadBehaviorGeral;
    public WebMarkupContainer containerFiltrar;
    public List<Pessoa> pessoas;
    public AjaxLink botaoCriar;
    public AjaxLink botaoExcel;
    public AjaxLink botaoPdf;
    public AjaxLink linkEditar;
    public AjaxLink linkExcluir;
    public AjaxLink linkExcel;
    public AjaxLink linkPdf;
    public PageableListView listaResultados;
    public byte[] bytes;

//    Loadable Lista de Pessoas
    LoadableDetachableModel pessoasModel = new LoadableDetachableModel(){
        @Override
        protected Object load() {
            pessoas = PessoaDAO.getInstance().findAll();
            return pessoas;
        }
    };

    public Inicio(){

        //Adicionando componentes no construtor
        add(modalCriar());
        add(modalConfirmacao());
        add(containerResultados());
        add(botaoCriar());
        add(botaoExcel());
        add(botaoPdf());
        add(containerFiltrar());

        ajaxDownloadBehavior = ajaxDownloadBehavior();
        add(ajaxDownloadBehavior).setOutputMarkupId(true);

        ajaxDownloadBehaviorGeral = ajaxDownloadBehaviorGeral();
        add(ajaxDownloadBehaviorGeral).setOutputMarkupId(true);
    }

    private WebMarkupContainer containerFiltrar(){
        containerFiltrar = new WebMarkupContainer("containerFiltrar");
        pessoa = new Pessoa();
        formularioFiltrar = new Form<Pessoa>("formularioFiltrar", new CompoundPropertyModel<>(pessoa)){
            @Override
            public void onSubmit() {
                LoadableDetachableModel listaFiltrar = new LoadableDetachableModel() {
                    @Override
                    protected Object load() {
                        return PessoaDAO.getInstance().filterPessoa(pessoa);
                    }
                };
                containerResultados.addOrReplace(listaResultados(listaFiltrar));
                containerResultados.addOrReplace(new AjaxPagingNavigator("navigator", listaResultados).setOutputMarkupId(true));

            }
        };

        List tiposPessoa = Arrays.stream(TipoPessoa.values()).map(TipoPessoa::getId).collect(Collectors.toList());
        ChoiceRenderer<Integer> choices = new ChoiceRenderer<Integer>(){
            @Override
            public Object getDisplayValue(Integer object) {
                return TipoPessoa.getLabel(object);
            }

            @Override
            public String getIdValue(Integer object, int index) {
                return object.toString();
            }
        };

        pesquisaTipoPessoa = new DropDownChoice<Integer>("tipoPessoa", tiposPessoa, choices);
        pesquisaTipoPessoa.setNullValid(true);
        pesquisaTipoPessoa.setOutputMarkupId(true);

        pesquisaRazaoSocial = new TextField<>("razaoSocial");
        pesquisaCpfCnpj = new TextField<>("cpfCnpj");

        pesquisaAtivo = new DropDownChoice<Boolean>("ativo", Arrays.asList(true, false), new ChoiceRenderer<Boolean>());
        pesquisaAtivo.setNullValid(false);
        pesquisaAtivo.setOutputMarkupId(true);


        formularioFiltrar.add(pesquisaTipoPessoa, pesquisaRazaoSocial, pesquisaCpfCnpj, pesquisaAtivo);
        formularioFiltrar.setOutputMarkupId(true);
        containerFiltrar.add(formularioFiltrar);
        containerFiltrar.setOutputMarkupId(true);
        return containerFiltrar;
    }

    private AjaxLink botaoCriar(){
        botaoCriar = new AjaxLink("botaoCriar"){
            @Override
            public void onClick(AjaxRequestTarget target) {
                enderecos = new ArrayList<>();
                formCriar = new FormularioCriar(modalCriar.getContentId(), new Pessoa(), enderecos);
                modalCriar.setOutputMarkupPlaceholderTag(true);
                modalCriar.setContent(formCriar);
                formCriar.setOutputMarkupId(true);
                modalCriar.show(target);
            }
        };
        botaoCriar.setOutputMarkupId(true);
        return botaoCriar;
    }

    private ModalWindow modalConfirmacao() {
        modalConfirmacao = new ModalWindow("modalConfirmacao");
        modalConfirmacao.setInitialHeight(170);
        modalConfirmacao.setHeightUnit("px");
        modalConfirmacao.setInitialWidth(600);
        modalConfirmacao.setWidthUnit("px");
        return modalConfirmacao;
    }

    private ModalWindow modalCriar(){
        modalCriar = new ModalWindow("modalFormulario");
        modalCriar.setInitialHeight(700);
        modalCriar.setHeightUnit("px");
        modalCriar.setInitialWidth(1100);
        modalCriar.setWidthUnit("px");
        return modalCriar;
    }

    private AjaxLink botaoExcel (){
        botaoExcel = new AjaxLink("botaoExcel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                bytes = new CriaExcel().criaExcelGeral(pessoas);
                ajaxDownloadBehaviorGeral.initiate(target);
            }
        };
        botaoExcel.setOutputMarkupId(true);
        return botaoExcel;
    }

    private AjaxLink botaoPdf() {
        botaoPdf = new AjaxLink("botaoPdf") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                //Gerar PDF de todas as pessoas
            }
        };
        botaoPdf.setOutputMarkupId(true);
        return botaoPdf;
    }

    private AjaxLink linkEditar(Pessoa pessoa){
        linkEditar = new AjaxLink("linkEditar") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                formCriar = new FormularioCriar(modalCriar.getContentId(), pessoa, pessoa.getEnderecos());
                modalCriar.setContent(formCriar);
                modalCriar.show(target);
            }
        };
        linkEditar.setOutputMarkupId(true);
        return linkEditar;
    }

    private AjaxLink linkExcluir(){
        linkExcluir = new AjaxLink("linkExcluir") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                confirmacao = new Confirmacao(modalConfirmacao.getContentId(), modalConfirmacao){
                    @Override
                    public void retornoConfirmacao(AjaxRequestTarget target, boolean retorno) {
                        if(retorno) {
                            PessoaDAO.getInstance().removeById((pessoa.getIdPessoa()));
                        }
                    }
                };
                modalConfirmacao.setOutputMarkupPlaceholderTag(true);
                modalConfirmacao.setContent(confirmacao);
                modalConfirmacao.show(target);
            }
        };
        linkExcluir.setOutputMarkupId(true);
        return linkExcluir;
    }

    private AjaxLink linkExcel(Pessoa pessoa){
        linkExcel = new AjaxLink("linkExcel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                bytes = new CriaExcel().criaExcel(pessoa);
                ajaxDownloadBehavior.initiate(target);
            }
        };
        linkExcel.setOutputMarkupId(true);
        return linkExcel;
    }

    private AjaxLink linkPdf(){
        linkPdf = new AjaxLink("linkPdf") {
            @Override
            public void onClick(AjaxRequestTarget target) {

            }
        };
        linkPdf.setOutputMarkupId(true);
        return linkPdf;
    }

    private AjaxDownloadBehavior ajaxDownloadBehavior(){
        ajaxDownloadBehavior = new AjaxDownloadBehavior(){
            @Override
            protected String getFileName() {
                return "testeExcel.xlsx";
            }

            @Override
            protected IResourceStream getResourceStream() {

                return new AbstractResourceStreamWriter() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void write(OutputStream output) throws IOException {
                        try {
                            output.write(bytes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (output != null) {
                                output.flush();
                                output.close();
                            }
                        }
                    }
                };
            }
        };
        return ajaxDownloadBehavior;
    }

    private AjaxDownloadBehavior ajaxDownloadBehaviorGeral(){
        ajaxDownloadBehaviorGeral = new AjaxDownloadBehavior(){
            @Override
            protected String getFileName() {
                return "testeExcel.xlsx";
            }

            @Override
            protected IResourceStream getResourceStream() {

                return new AbstractResourceStreamWriter() {
                    private static final long serialVersionUID = -4006396448390364264L;

                    @Override
                    public void write(OutputStream output) throws IOException {
                        try {
                            output.write(bytes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (output != null) {
                                output.flush();
                                output.close();
                            }
                        }
                    }
                };
            }
        };
        return ajaxDownloadBehaviorGeral;
    }

    private WebMarkupContainer containerResultados(){
        //Corresponde a div de resultados no html
        containerResultados = new WebMarkupContainer("divResultados");
        containerResultados.setOutputMarkupPlaceholderTag(true);
        PageableListView listaResultados = listaResultados(pessoasModel);
        containerResultados.addOrReplace(listaResultados);
        containerResultados.addOrReplace(new AjaxPagingNavigator("navigator", listaResultados).setOutputMarkupId(true));
        return containerResultados;
    }

    private PageableListView listaResultados(LoadableDetachableModel listaLoadable){
        listaResultados = new PageableListView<Pessoa>("pessoas", listaLoadable, 10) {
            @Override
            protected void populateItem(final ListItem<Pessoa> listItem) {

                final Pessoa pessoa = listItem.getModelObject();

                listItem.add(new Label("tipoPessoa", TipoPessoa.getLabel(pessoa.getTipoPessoa()))).setOutputMarkupId(true);
                listItem.add(new Label("cpfCnpj", pessoa.getCpfCnpj())).setOutputMarkupId(true);
                listItem.add(new Label("razaoSocial", pessoa.getRazaoSocial())).setOutputMarkupId(true);
                listItem.add(new Label("email", pessoa.getEmail())).setOutputMarkupId(true);
                listItem.add(new Label("telefone", pessoa.getTelefone())).setOutputMarkupId(true);
                listItem.add(new Label("inscricaoEstadual", pessoa.getInscricaoEstadual())).setOutputMarkupId(true);
                listItem.add(new Label("ativo", pessoa.getAtivoNome())).setOutputMarkupId(true);
                listItem.add(linkEditar(pessoa)).setOutputMarkupId(true);
                listItem.add(linkExcluir()).setOutputMarkupId(true);
                listItem.add(linkExcel(pessoa)).setOutputMarkupId(true);
                listItem.add(linkPdf()).setOutputMarkupId(true);



            }
        };
        listaResultados.setOutputMarkupId(true);
        return listaResultados;
    }

}
