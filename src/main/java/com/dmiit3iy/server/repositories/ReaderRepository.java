package com.dmiit3iy.server.repositories;

import com.dmiit3iy.server.models.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

}
