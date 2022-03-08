package br.com.unikasistemas.desafioestagio.controller;

import br.com.unikasistemas.desafioestagio.model.Endereco;
import br.com.unikasistemas.desafioestagio.model.Pessoa;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class FormularioEndereco extends Panel {

    public FormularioEndereco(final String id, final Endereco endereco, final ModalWindow modalEndereco) {
        super(id);

        //Titulo do Formulario
        add(new Label("titulo2", "Criação de Endereço"));


        //Iniciando Formulário de Endereço com método submit e chamando o metodo salvar
        CompoundPropertyModel<Endereco> compoundPropertyModelEndereco = new CompoundPropertyModel<Endereco>(endereco);
        Form<Endereco> formularioEndereco = new Form<Endereco>("formularioEndereco", compoundPropertyModelEndereco);

        IndicatingAjaxButton botaoSubmit = new IndicatingAjaxButton("botaoSubmit", formularioEndereco) {
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
        TextField<String> inputLogradouro = new TextField<String>("logradouro");
        TextField<String> inputBairro = new TextField<String>("bairro");
        TextField<String> inputNumero = new TextField<String>("numero");
        TextField<String> inputComplemento = new TextField<String>("complemento");
        TextField<String> inputCidade = new TextField<String>("cidade");
        TextField<String> inputEstado = new TextField<String>("estado");
        RadioGroup inputEnderecoPrincipal = new RadioGroup("enderecoPrincipal", getDefaultModel());
        inputEnderecoPrincipal.add(new Radio("principalSim", new Model<Boolean>(true)));
        inputEnderecoPrincipal.add(new Radio("principalNao", new Model<Boolean>(false)));

        //Adicionando os campos no formulario
        formularioEndereco.add(inputCep, inputLogradouro, inputBairro, inputNumero, inputComplemento,
                inputCidade, inputEstado, inputEnderecoPrincipal, botaoSubmit);
    }

    public void afterCloseModal(AjaxRequestTarget target, Endereco endereco) {
    }

}


