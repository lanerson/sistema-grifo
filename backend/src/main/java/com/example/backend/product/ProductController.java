package com.example.backend.product;

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
@RequestMapping(path = "/products")
public class ProductController {
    private final ProductRepository repository;

    private final ProductModelAssembler assembler;

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
    Product newProduct(@RequestParam("name") String name, @RequestParam("price") float price) {
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setPrice(price);
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
