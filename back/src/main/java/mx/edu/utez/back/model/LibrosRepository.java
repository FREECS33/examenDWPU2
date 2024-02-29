package mx.edu.utez.back.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LibrosRepository extends JpaRepository<Libros,Long>{
    List<Libros> findByNameBookContainingIgnoreCase(String title);
    List<Libros> findAllByOrderByNameBookDesc();

    List<Libros> findAllByOrderByAuthorDesc();

    List<Libros> findAllByOrderByIssueDateDesc();

    List<Libros> findAllByImageNotNull();
}
