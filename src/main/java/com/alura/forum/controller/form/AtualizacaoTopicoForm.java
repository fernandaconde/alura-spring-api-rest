package com.alura.forum.controller.form;

import com.alura.forum.model.Topico;
import com.alura.forum.repository.TopicoRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AtualizacaoTopicoForm {

    @NotNull
    @NotEmpty
    @Size(min = 5)
    private String titulo;
    @NotNull @NotEmpty @Size(min = 10)
    private String mensagem;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }


    public Topico atualizar(Long id, TopicoRepository repository) {
        Topico topico = repository.getOne(id);
        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);

        return topico;
    }
}
