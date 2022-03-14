package br.com.unikasistemas.desafioestagio.controller;

import br.com.unikasistemas.desafioestagio.model.*;
import br.com.unikasistemas.desafioestagio.view.Inicio;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.feedback.ErrorLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.*;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FormularioCriar extends Panel {

    public ModalWindow modalEndereco;
    public FormularioEndereco formEndereco;
    public ModalWindow modalConfirmacao;
    public Confirmacao confirmacao;
    public ListView<Endereco> listaEnderecos;
    public WebMarkupContainer containerEnderecos;
    public AjaxLink botaoInserirEndereco;
    public LoadableDetachableModel enderecosModel;
    public Pessoa pessoa;
    public CompoundPropertyModel<Pessoa> compoundPropertyModelPessoa;
    public Form<Pessoa> formularioPessoa;
    public DropDownChoice<Integer> comboTipoPessoa;
    public TextField<String> inputCpfCnpj;
    public TextField<String> inputRazaoSocial;
    public TextField<String> inputNomeAlternativo;
    public TextField<String> inputEmail;
    public TextField<String> inputRg;
    public TextField<String> inputTelefone;
    public TextField<String> inputInscricaoEstadual;
    public RadioGroup inputAtivo;
    public WebMarkupContainer containerPj;
    public WebMarkupContainer containerPf;
    public WebMarkupContainer containerPjVisible;
    public WebMarkupContainer containerPfVisible;

    public LoadableDetachableModel enderecosModel(final Pessoa pessoa){
        enderecosModel = new LoadableDetachableModel(){
            @Override
            protected Object load() {
            return pessoa.getEnderecos();
            }
        };
        return enderecosModel;
    }

    public FormularioCriar(String id, final Pessoa pessoa, final List<Endereco> enderecos) {
        super(id);
        this.pessoa = pessoa;

        //Adicionando os componentes na página
        add(modalConfirmacao());
        add(modalEndereco());
        add(containerEnderecos());
        containerEnderecos.add(listaEnderecos());
        add(botaoInserirEndereco());

        //Titulo
        add(new Label("titulo", "Criação de Pessoa"));

        //CompoundPropertyModel e Declaração do formulario com o método submit
        compoundPropertyModelPessoa = new CompoundPropertyModel<Pessoa>(pessoa);
        formularioPessoa = new Form<Pessoa>("formularioPessoa", compoundPropertyModelPessoa){
            @Override
            public void onSubmit() {
                Pessoa pessoaSubmetida = getModelObject();
                salvar(pessoaSubmetida);
            }
        };
        add(formularioPessoa);
        //Adicionando o formulario na página

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

        //Declaração dos inputs de texto, dropdown e radiogroup do formulario
        comboTipoPessoa = new DropDownChoice<Integer>("tipoPessoa", tiposPessoa, choices);
        comboTipoPessoa.setOutputMarkupId(true);
        comboTipoPessoa.setOutputMarkupPlaceholderTag(true);
        comboTipoPessoa.setNullValid(false);
        comboTipoPessoa.add(new AjaxFormComponentUpdatingBehavior("change") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(containerPjVisible);
                target.add(containerPfVisible);
                if(comboTipoPessoa != null && comboTipoPessoa.getModelObject() != null && comboTipoPessoa.getModelObject().equals(TipoPessoa.JURIDICA.getId())) {
                    inputInscricaoEstadual.setRequired(true).add(StringValidator.maximumLength(30));
                    inputNomeAlternativo.setRequired(true).add(StringValidator.maximumLength(50));
                }
                if(comboTipoPessoa != null && comboTipoPessoa.getModelObject() != null && comboTipoPessoa.getModelObject().equals(TipoPessoa.FISICA.getId())) {
                    inputRg.setLabel(Model.of("RG")).setRequired(true).add(StringValidator.maximumLength(15));
                }
        }});

        inputCpfCnpj = new TextField<String>("cpfCnpj");
        inputRazaoSocial = new TextField<String>("razaoSocial");
        inputNomeAlternativo = new TextField<String>("nomeAlternativo");
        inputEmail = new TextField<String>("email");
        inputRg = new TextField<String>("rg");
        inputTelefone = new TextField<String>("telefone");
        inputInscricaoEstadual = new TextField<String>("inscricaoEstadual");
        inputAtivo = new RadioGroup("ativo", getDefaultModel());
        inputAtivo.add(new Radio("ativoSim", new Model<Boolean>(true)));
        inputAtivo.add(new Radio("ativoNao", new Model<Boolean>(false)));

        formularioPessoa.add(comboTipoPessoa, inputCpfCnpj,inputRazaoSocial, inputEmail, inputTelefone, inputAtivo);

        containerPf = new WebMarkupContainer("containerPf"){
            @Override
            public boolean isVisible(){
                if(comboTipoPessoa != null && comboTipoPessoa.getModelObject() != null && comboTipoPessoa.getModelObject().equals(TipoPessoa.FISICA.getId())){
                    return true;
                }
                else {
                    return false;
                }
            }};
        containerPfVisible = new WebMarkupContainer("containerPfVisible");
        containerPfVisible.setOutputMarkupPlaceholderTag(true);
        containerPfVisible.setOutputMarkupId(true);
        containerPf.add(inputRg);
        containerPf.add(criarDataTextFieldNascimento());
        containerPf.setOutputMarkupPlaceholderTag(true);
        containerPf.setOutputMarkupId(true);
        containerPfVisible.add(containerPf);
        formularioPessoa.add(containerPfVisible);

        containerPj = new WebMarkupContainer("containerPj"){
            @Override
            public boolean isVisible(){
                if(comboTipoPessoa != null && comboTipoPessoa.getModelObject() != null && comboTipoPessoa.getModelObject().equals(TipoPessoa.JURIDICA.getId())){
                    return true;
                }
                else {
                    return false;
                }
            }};
        containerPjVisible = new WebMarkupContainer("containerPjVisible");
        containerPjVisible.setOutputMarkupPlaceholderTag(true);
        containerPjVisible.setOutputMarkupId(true);
        containerPj.add(inputNomeAlternativo);
        containerPj.add(inputInscricaoEstadual);
        containerPj.setOutputMarkupPlaceholderTag(true);
        containerPj.setOutputMarkupId(true);
        containerPjVisible.add(containerPj);
        formularioPessoa.add(containerPjVisible);

        //Validadores
        inputRazaoSocial.setLabel(Model.of("Razão Social/Nome")).setRequired(true).add(StringValidator.maximumLength(50));
        comboTipoPessoa.setLabel(Model.of("Tipo de Pessoa")).setRequired(true);
        inputCpfCnpj.setLabel(Model.of("CPF/CNPJ")).setRequired(true).add(StringValidator.maximumLength(18));
        inputEmail.setLabel(Model.of("E-mail do Contato")).add(EmailAddressValidator.getInstance());


        //Mensagem de Feedback para os Validadores
        add(new FeedbackPanel("feedbackMessage", new ErrorLevelFeedbackMessageFilter(FeedbackMessage.ERROR)));

    }

    //---------------------------------------------------------------------------------------------------------------------

    protected void salvar(Pessoa pessoaSubmetida) {

        if (pessoaSubmetida.getIdPessoa() != null) {
            PessoaDAO.getInstance().merge(pessoaSubmetida);
            setResponsePage(Inicio.class);
        } else {
            PessoaDAO.getInstance().persist(pessoaSubmetida);
            setResponsePage(Inicio.class);
        }
    }

    private DateTextField criarDataTextFieldNascimento() {

        DatePicker datePicker = new DatePicker() {

            @Override
            protected boolean alignWithIcon() {
                return true;
            }

            @Override
            protected boolean enableMonthYearSelection() {
                return false;
            }
        };

        DateTextField dataNascimento = new DateTextField("dataNascimento", "dd/MM/yyyy");

        datePicker.setAutoHide(true);
        dataNascimento.add(datePicker);
        dataNascimento.add(new AttributeModifier("onfocus", "$(this).setMask('date');"));
        dataNascimento.add(new AttributeModifier("onclick", "$(this).setMask('99/99/9999');"));
        dataNascimento.setOutputMarkupId(true);
        return dataNascimento;
    }

    private ModalWindow modalConfirmacao() {
        modalConfirmacao = new ModalWindow("modalConfirmacao");
        modalConfirmacao.setInitialHeight(170);
        modalConfirmacao.setHeightUnit("px");
        modalConfirmacao.setInitialWidth(600);
        modalConfirmacao.setWidthUnit("px");
        return modalConfirmacao;
    }

    private ModalWindow modalEndereco(){
        modalEndereco = new ModalWindow("modalEndereco");
        modalEndereco.setInitialHeight(600);
        modalEndereco.setHeightUnit("px");
        modalEndereco.setInitialWidth(800);
        modalEndereco.setWidthUnit("px");
        return modalEndereco;
    }

    private AjaxLink botaoInserirEndereco(){
        botaoInserirEndereco = new AjaxLink("botaoInserirEndereco"){

            @Override
            public void onClick(AjaxRequestTarget target) {
                formEndereco = new FormularioEndereco(modalEndereco.getContentId(), new Endereco(), modalEndereco){
                    @Override
                    public void afterCloseModal(AjaxRequestTarget target, Endereco endereco) {
                        pessoa.addEndereco(endereco);
                        endereco.setPessoa(pessoa);
                    }
                };
                modalEndereco.setOutputMarkupPlaceholderTag(true);
                modalEndereco.setContent(formEndereco);
                formEndereco.setOutputMarkupId(true);
                modalEndereco.show(target);
            }
        };
        return botaoInserirEndereco;
    }

    private WebMarkupContainer containerEnderecos(){
        containerEnderecos = new WebMarkupContainer("divEnderecos");
        containerEnderecos.setVisible(true);
        containerEnderecos.setOutputMarkupPlaceholderTag(true);
        return containerEnderecos;
    }

    private ListView listaEnderecos(){
        listaEnderecos = new ListView<Endereco>("enderecos", enderecosModel(pessoa)) {
            @Override
            protected void populateItem(ListItem<Endereco> item) {
                final Endereco endereco = (Endereco) item.getModelObject();
                item.add(new Label("cep", endereco.getCep()));
                item.add(new Label("logradouro", endereco.getLogradouro()));
                item.add(new Label("bairro", endereco.getBairro()));
                item.add(new Label("numero", endereco.getNumero()));
                item.add(new Label("complemento", endereco.getComplemento()));
                item.add(new Label("estado", endereco.getEstado()));
                item.add(new Label("cidade", endereco.getCidade()));
                item.add(new Label("enderecoPrincipal", endereco.getEnderecoPrincipalNome()));
                item.add(new AjaxLink<Void>("editarEndereco") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        formEndereco = new FormularioEndereco(modalEndereco.getContentId(), endereco, modalEndereco);
                        modalEndereco.setContent(formEndereco);
                        modalEndereco.show(target);
                    }

                });
                item.add(new AjaxLink("excluirEndereco") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        confirmacao = new Confirmacao(modalConfirmacao.getContentId(), modalConfirmacao){
                            @Override
                            public void retornoConfirmacao(AjaxRequestTarget target, boolean retorno) {
                                if(retorno) {
//                                    pessoa.getEnderecos().removeIf(end -> endereco.getIdEndereco().equals(end.getIdEndereco()));
//                                    pessoa.getEnderecos().removeIf(endereco -> PessoaDAO.getInstance().getEnderecoById(endereco.getIdEndereco()).equals(endereco));
                                    pessoa.getEnderecos().remove(endereco);
                                    PessoaDAO.getInstance().removeEnderecoById(endereco.getIdEndereco());
                                }
                            }
                        };
                        modalConfirmacao.setOutputMarkupPlaceholderTag(true);
                        modalConfirmacao.setContent(confirmacao);
                        modalConfirmacao.show(target);
                    }
                });
            }
        };
        return listaEnderecos;
    }

}
