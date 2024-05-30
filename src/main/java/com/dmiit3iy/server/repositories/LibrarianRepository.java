package com.dmiit3iy.server.repositories;

import com.dmiit3iy.server.models.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
}
