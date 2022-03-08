package br.com.unikasistemas.desafioestagio.controller;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;


public class Confirmacao extends Panel {

    public Confirmacao(String id, final ModalWindow modalConfirmacao) {
        super(id);

        final Form formConfirmacao = new Form("formConfirmacao");

        AjaxButton botaoSim = new AjaxButton("botaoSim", formConfirmacao) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                if (target != null) {
                    retornoConfirmacao(target, true);
                    modalConfirmacao.close(target);
                }
            }
        };

            AjaxButton botaoNao = new AjaxButton("botaoNao", formConfirmacao) {

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form form) {
                    if (target != null) {
                        retornoConfirmacao(target, false);
                        modalConfirmacao.close(target);
                    }
                }
            };
            formConfirmacao.add(botaoSim);
            formConfirmacao.add(botaoNao);

            add(formConfirmacao);
    }

    public void retornoConfirmacao(AjaxRequestTarget target, boolean retorno){
    }

}