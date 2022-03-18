package br.com.unikasistemas.desafioestagio.controller;

import br.com.unikasistemas.desafioestagio.model.Endereco;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class FormularioEndereco extends Panel {

    public FeedbackPanel fb;

    public FormularioEndereco(final String id, final Endereco endereco, final ModalWindow modalEndereco) {
        super(id);

        //Titulo do Formulario
        add(new Label("titulo2", "Criação de Endereço"));


        //Iniciando Formulário de Endereço com método submit e chamando o método salvar
        CompoundPropertyModel<Endereco> compoundPropertyModelEndereco = new CompoundPropertyModel<>(endereco);
        Form<Endereco> formularioEndereco = new Form<>("formularioEndereco", compoundPropertyModelEndereco);
        IndicatingAjaxButton botaoSubmit = new IndicatingAjaxButton("botaoSubmit", formularioEndereco) {
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(fb);
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                modalEndereco.close(target);
                afterCloseModal(target, endereco);
            }
        };

        //Adicionando o formulario
        add(formularioEndereco);


        //Declarando os campos de texto e radio
        TextField<String> inputCep = new TextField<String>("cep");
        inputCep.setRequired(true);
        TextField<String> inputLogradouro = new TextField<String>("logradouro");
        inputLogradouro.setRequired(true);
        TextField<String> inputBairro = new TextField<String>("bairro");
        inputBairro.setRequired(true);
        TextField<String> inputNumero = new TextField<String>("numero");
        inputNumero.setRequired(true);
        TextField<String> inputComplemento = new TextField<String>("complemento");
        inputComplemento.setRequired(true);
        TextField<String> inputCidade = new TextField<String>("cidade");
        inputCidade.setRequired(true);
        TextField<String> inputEstado = new TextField<String>("estado");
        inputEstado.setRequired(true);
        RadioGroup inputEnderecoPrincipal = new RadioGroup("enderecoPrincipal", getDefaultModel());
        inputEnderecoPrincipal.add(new Radio("principalSim", new Model<Boolean>(true)));
        inputEnderecoPrincipal.add(new Radio("principalNao", new Model<Boolean>(false)));
        inputEnderecoPrincipal.setRequired(true);
        //Adicionando os campos no formulario
        formularioEndereco.add(inputCep, inputLogradouro, inputBairro, inputNumero, inputComplemento,
                inputCidade, inputEstado, inputEnderecoPrincipal, botaoSubmit);

        fb = new FeedbackPanel("fb");
        fb.setOutputMarkupId(true);
        formularioEndereco.add(fb).setOutputMarkupId(true);

    }

    public void afterCloseModal(AjaxRequestTarget target, Endereco endereco) {
    }

}


