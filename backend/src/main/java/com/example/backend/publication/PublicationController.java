package com.example.backend.publication;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(path = "/publications")
public class PublicationController {
    private final PublicationRepository repository;

    private final PublicationModelAssembler assembler;

    PublicationController(PublicationRepository repository, PublicationModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // todos os produtos
    @GetMapping(path = "/all")
    CollectionModel<EntityModel<Publication>> all() {
        List<EntityModel<Publication>> publications = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(publications,
                linkTo(methodOn(PublicationController.class).all()).withSelfRel());
    }

    @PostMapping(path = "/create")
    Publication newPublication(@RequestParam("title") String title, @RequestParam("description") String description) {
        Publication newpublication = new Publication(title, description);
        return repository.save(newpublication);
    }

    // para um único item
    @GetMapping(path = "/search/{id}")
    EntityModel<Publication> one(@PathVariable Long id) {

        Publication publication = repository.findById(id)
                .orElseThrow(() -> new PublicationNotFoundException(id));

        return assembler.toModel(publication);
    }

    @PutMapping(path = "/update/{id}")
    Publication replacepublication(@RequestBody Publication newpublication, @PathVariable Long id) {
        return repository.findById(id)
                .map(publication -> {
                    publication.setTitle(newpublication.getTitle());
                    publication.setDescription(newpublication.getDescription());
                    return repository.save(publication);
                })
                .orElseGet(() -> {
                    return repository.save(newpublication);
                });
    }

    @DeleteMapping(path = "/delete/{id}")
    void deletePublication(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
