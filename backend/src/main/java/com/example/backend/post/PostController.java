package com.example.backend.post;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(path = "/posts")
public class PostController {
    private final PostRepository repository;

    private final PostModelAssembler assembler;

    PostController(PostRepository repository, PostModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // todos os produtos
    @GetMapping(path = "/all")
    CollectionModel<EntityModel<Post>> all() {
        List<EntityModel<Post>> posts = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(posts,
                linkTo(methodOn(PostController.class).all()).withSelfRel());
    }

    @PostMapping(path = "/create")
    Post newpost(@RequestBody Post newpost) {
        return repository.save(newpost);
    }

    // para um único item
    @GetMapping(path = "/search/{id}")
    EntityModel<Post> one(@PathVariable Long id) {

        Post post = repository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        return assembler.toModel(post);
    }

    @PutMapping(path = "/update/{id}")
    Post replacepost(@RequestBody Post newpost, @PathVariable Long id) {
        return repository.findById(id)
                .map(post -> {
                    post.setTitle(newpost.getTitle());
                    post.setDescription(newpost.getDescription());                    
                    return repository.save(post);
                })
                .orElseGet(() -> {
                    return repository.save(newpost);
                });
    }

    @DeleteMapping(path = "/delete/{id}")
    void deletepost(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
