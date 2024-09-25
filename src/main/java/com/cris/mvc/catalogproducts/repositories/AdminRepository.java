package com.cris.mvc.catalogproducts.repositories;

import com.cris.mvc.catalogproducts.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<List<Admin>> findByJobPosition(String jobPosition);
    Optional<List<Admin>> findByName(String name);
    Optional<List<Admin>> findByLastName(String lastName);
    Optional<List<Admin>> findByEmail(String email);
    Optional<List<Admin>> findByDocumentNumber(String documentNumber);
}
