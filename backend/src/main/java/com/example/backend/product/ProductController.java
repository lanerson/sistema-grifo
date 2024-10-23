package com.example.backend.product;

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
@RequestMapping(path = "/products")
public class ProductController {
    private final ProductRepository repository;

    private final ProductModelAssembler assembler;

    @Value("${file.upload-dir}")
    private String uploadDir;

    ProductController(ProductRepository repository, ProductModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // todos os produtos
    @GetMapping(path = "/all")
    CollectionModel<EntityModel<Product>> all() {
        List<EntityModel<Product>> products = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(products,
                linkTo(methodOn(ProductController.class).all()).withSelfRel());
    }

    @PostMapping(path = "/create")
    Product newProduct(@RequestParam("name") String name, @RequestParam("price") float price,
            @RequestParam("image") MultipartFile file) {
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setPrice(price);
        try {
            // Verifica se o arquivo é vazio e salva a imagem padrão
            if (file.isEmpty()) {
                Path path = Paths.get(uploadDir + File.separator + "default.png");
                newProduct.setImage(path.toString());
                return repository.save(newProduct); // Retorna aqui se o arquivo é vazio
            }

            // Caminho onde a imagem será salva
            Path path = Paths.get(uploadDir + File.separator + name + "_"
                    + file.getOriginalFilename());


            // Criar os diretórios se não existirem
            Files.createDirectories(path.getParent());

            // Copiar o arquivo para o diretório de destino
            Files.write(path, file.getBytes());

            // Definir o caminho da imagem no produto
            newProduct.setImage(path.toString());

        } catch (IOException e) {
            // Logar o erro para melhor depuração
            System.err.println("Erro ao salvar a imagem: " + e.getMessage());
            Path path = Paths.get(uploadDir + File.separator + "default.png");
            newProduct.setImage(path.toString());
        }
        System.out.println(newProduct.getImage());
        return repository.save(newProduct);
    }

    // para um único item
    @GetMapping(path = "/search/{id}")
    EntityModel<Product> one(@PathVariable Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return assembler.toModel(product);
    }

    @PutMapping(path = "/update/{id}")
    Product replaceProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        return repository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setPrice(newProduct.getPrice());
                    product.setPrice(newProduct.getPrice());
                    return repository.save(product);
                })
                .orElseGet(() -> {
                    return repository.save(newProduct);
                });
    }

    @DeleteMapping(path = "/delete/{id}")
    void deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
