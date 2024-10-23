package com.example.backend.publication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(path = "/publications")
public class PublicationController {
    private final PublicationRepository repository;

    private final PublicationModelAssembler assembler;

    @Value("${file.upload-dir}")
    private String uploadDir;

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
    Publication newPublication(@RequestParam("title") String title, @RequestParam("description") String description,
            @RequestParam("image") MultipartFile file) {
        Publication newPublication = new Publication();
        newPublication.setTitle(title);
        newPublication.setDescription(description);
        try {
            // Verifica se o arquivo é vazio e salva a imagem padrão
            if (file.isEmpty()) {
                Path path = Paths.get(uploadDir + File.separator + "default.png");
                newPublication.setImage(path.toString());
                return repository.save(newPublication); // Retorna aqui se o arquivo é vazio
            }

            // Caminho onde a imagem será salva
            Path path = Paths.get(uploadDir + File.separator + title + "_"
                    + file.getOriginalFilename());

            // Criar os diretórios se não existirem
            Files.createDirectories(path.getParent());

            // Copiar o arquivo para o diretório de destino
            Files.write(path, file.getBytes());

            // Definir o caminho da imagem no produto
            newPublication.setImage(path.toString());

        } catch (IOException e) {
            // Logar o erro para melhor depuração
            System.err.println("Erro ao salvar a imagem: " + e.getMessage());
            Path path = Paths.get(uploadDir + File.separator + "default.png");
            newPublication.setImage(path.toString());
        }
        System.out.println(newPublication.getImage());
        return repository.save(newPublication);
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
                    publication.setImage(newpublication.getImage());
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
