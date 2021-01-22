package com.alura.forum.controller;

import com.alura.forum.controller.dto.DetalhesDoTopicoDTO;
import com.alura.forum.controller.dto.TopicoDTO;
import com.alura.forum.controller.form.AtualizacaoTopicoForm;
import com.alura.forum.controller.form.TopicoForm;
import com.alura.forum.model.Topico;
import com.alura.forum.repository.CursoRepository;
import com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping("/")
    public List<TopicoDTO> list() {
        List<Topico> topicos = repository.findAll();
        return TopicoDTO.converter(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTopicoDTO> detalhar(@PathVariable Long id) {
        Optional<Topico> topico = repository.findById(id);
        if(topico.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoTopicoDTO(topico.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    @Transactional
    public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
        Topico topico = form.converter(cursoRepository);
        repository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDTO(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDTO> autalizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
        Optional<Topico> optional = repository.findById(id);
        if(optional.isPresent()) {
            Topico topico = form.atualizar(id, repository);
            return ResponseEntity.ok(new TopicoDTO(topico));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Topico> optional = repository.findById(id);
        if(optional.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
