package com.example.backend.publication;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PublicationModelAssembler implements
        RepresentationModelAssembler<Publication, EntityModel<Publication>> {

    @Override
    public EntityModel<Publication> toModel(Publication publication) {
        return EntityModel.of(publication,
                linkTo(methodOn(PublicationController.class).one(publication.getId())).withSelfRel(),
                linkTo(methodOn(PublicationController.class).all()).withRel("publications"));
    }

}
