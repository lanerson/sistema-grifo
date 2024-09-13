package com.example.backend.publication;

public class PublicationNotFoundException extends RuntimeException {
    PublicationNotFoundException(Long id) {
        super("Could not find post " + id);
    }
}
