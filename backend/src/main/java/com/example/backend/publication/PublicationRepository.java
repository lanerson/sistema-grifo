package com.example.backend.publication;

import org.springframework.data.jpa.repository.JpaRepository;

interface PublicationRepository extends JpaRepository<Publication, Long> {
}
