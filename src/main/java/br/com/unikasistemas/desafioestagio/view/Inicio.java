package br.com.unikasistemas.desafioestagio.view;
import br.com.unikasistemas.desafioestagio.controller.Confirmacao;
import br.com.unikasistemas.desafioestagio.controller.FormularioCriar;
import br.com.unikasistemas.desafioestagio.model.*;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import sun.security.pkcs11.wrapper.CK_SSL3_MASTER_KEY_DERIVE_PARAMS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inicio extends HomePage {

    public ModalWindow modalCriar;
    public FormularioCriar formCriar;
    public ModalWindow modalConfirmacao;
    public Confirmacao confirmacao;
    public PageableListView<Pessoa> listaResultados;
    public WebMarkupContainer containerResultados;
    public AjaxLink botaoCriar;
    public AjaxButton botaoFiltrar;
    public Form<Pessoa> formularioFiltrar;
    public DropDownChoice<TipoPessoa> pesquisaTipoPessoa;
    public TextField<String> pesquisaRazaoSocial;
    public TextField<String> pesquisaCpfCnpj;
    public RadioGroup pesquisaAtivo;
    public List<Endereco> enderecos;
    public Pessoa pessoa;
//    public CompoundPropertyModel<Pessoa> compoundPropertyModelPessoa;
    public AjaxLink botaoExcel;
    public AjaxLink botaoPdf;

    //Loadable Lista de Pessoas
    LoadableDetachableModel pessoasModel = new LoadableDetachableModel(){
        @Override
        protected Object load() {
            List<Pessoa> pessoas = PessoaDAO.getInstance().findAll();
            return pessoas;
        }
    };

    public Inicio(){

        //Adicionando componentes na página
        add(modalCriar());
        add(modalConfirmacao());
        add(containerResultados());
        containerResultados.add(listaResultados());
        containerResultados.add(new AjaxPagingNavigator("navigator", listaResultados()));
        containerResultados.setVersioned(false);
        add(botaoCriar());
        add(botaoExcel());
        add(botaoPdf());

        pessoa = new Pessoa();
//        compoundPropertyModelPessoa = new CompoundPropertyModel<Pessoa>(pessoa);
        formularioFiltrar = new Form<Pessoa>("formularioFiltrar");

        add(formularioFiltrar);

        //Primeiro item do formulário - DropDown
        pesquisaTipoPessoa = new DropDownChoice<>("pesquisaTipoPessoa",
                Arrays.asList(TipoPessoa.values()), new ChoiceRenderer<TipoPessoa>("label"));
//        pesquisaTipoPessoa.setNullValid(false);
        //Adicionando 2 TextFields e um Radio button no formulario
        pesquisaRazaoSocial = new TextField<>("pesquisaRazaoSocial", new Model<String>());
        pesquisaCpfCnpj = new TextField<>("pesquisaCpfCnpj", new Model<String>());
        pesquisaAtivo = new RadioGroup("pesquisaAtivo", new Model());
        pesquisaAtivo.add(new Radio("ativoSim", new Model<Boolean>(true)));
        pesquisaAtivo.add(new Radio("ativoNao", new Model<Boolean>(false)));

        formularioFiltrar.add(pesquisaTipoPessoa, pesquisaRazaoSocial, pesquisaCpfCnpj, pesquisaAtivo, botaoFiltrar(pessoa));

    }



    private AjaxButton botaoFiltrar(Pessoa pessoa){
        botaoFiltrar = new AjaxButton("botaoFiltrar", formularioFiltrar) {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                PessoaDAO.getInstance().Filter(pessoa);

//                listaResultados.setDefaultModelObject(pessoas);
//                containerResultados.setVisible(true);
//                target.add(containerResultados);

            }
        };
        return botaoFiltrar;
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
                //Gerar Excel de todas as pessoas
            }
        };
        return botaoExcel;
    }

    private AjaxLink botaoPdf() {
        botaoPdf = new AjaxLink("botaoPdf") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                //Gerar PDF de todas as pessoas
            }
        };
        return botaoPdf;
    }

    private WebMarkupContainer containerResultados(){
        //Corresponde a div de resultados no html
        containerResultados = new WebMarkupContainer("divResultados");
        containerResultados.setVisible(true);
        containerResultados.setOutputMarkupPlaceholderTag(true);
        return containerResultados;
    }

    private PageableListView listaResultados(){
        listaResultados = new PageableListView<Pessoa>("pessoas", pessoasModel, 5) {
            @Override
            protected void populateItem(final ListItem<Pessoa> listItem) {
                final Pessoa pessoa = (Pessoa) listItem.getModelObject();
                listItem.add(new Label("tipoPessoa", pessoa.getTipoPessoa().getLabel()));
                listItem.add(new Label("cpfCnpj", pessoa.getCpfCnpj()));
                listItem.add(new Label("razaoSocial", pessoa.getRazaoSocial()));
//                listItem.add(new Label("nomeAlternativo", pessoa.getNomeAlternativo()));
                listItem.add(new Label("email", pessoa.getEmail()));
//                listItem.add(new Label("rg", pessoa.getRg()));
                listItem.add(new Label("telefone", pessoa.getTelefone()));
                listItem.add(new Label("inscricaoEstadual", pessoa.getInscricaoEstadual()));
//                listItem.add(new Label("dataNascimento", pessoa.getDataNascimento()));
                listItem.add(new Label("ativo", pessoa.getAtivoNome()));
                listItem.add(new AjaxLink<Void>("linkEditar") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        formCriar = new FormularioCriar(modalCriar.getContentId(), pessoa, pessoa.getEnderecos());
                        modalCriar.setContent(formCriar);
                        modalCriar.show(target);
                    }
                });
                listItem.add(new AjaxLink("linkExcluir") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        confirmacao = new Confirmacao(modalConfirmacao.getContentId(), modalConfirmacao){
                            @Override
                            public void retornoConfirmacao(AjaxRequestTarget target, boolean retorno) {
                                if(retorno) {
                                    PessoaDAO.getInstance().removeById((pessoa.getIdPessoa()));
                                }
                                target.add(containerResultados);
                            }
                        };
                        modalConfirmacao.setOutputMarkupPlaceholderTag(true);
                        modalConfirmacao.setContent(confirmacao);
                        modalConfirmacao.show(target);
                    }
                });
                listItem.add(new AjaxLink<Void>("linkExcel") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        CriaExcel criaExcel = new CriaExcel();
                        criaExcel.CriaExcel(pessoa);
                    }
                });
                listItem.add(new AjaxLink<Void>("linkPdf") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {

                    }
                });
            }
        };
        return listaResultados;
    }

}
