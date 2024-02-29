package mx.edu.utez.back.service;


import mx.edu.utez.back.model.Libros;
import mx.edu.utez.back.model.LibrosRepository;
import mx.edu.utez.back.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LibrosService {
    @Autowired
    LibrosRepository repository;

    public List<Libros> buscarPorNombre(String nombre) {
        return repository.findByNameBookContainingIgnoreCase(nombre);
    }
    public List<Libros> buscarLibrosConImagen() {
        return repository.findAllByImageNotNull();
    }

    public List<Libros> buscarConImagenOrdenAlfabetico() {
        return repository.findAllByOrderByNameBookDesc();
    }

    public List<Libros> buscarOrdenFechaPublicacionDesc() {
        return repository.findAllByOrderByIssueDateDesc();
    }

    public List<Libros> buscarOrdenAutor() {
        return repository.findAllByOrderByAuthorDesc();
    }

    @Transactional(readOnly = true)
    public CustomResponse<List<Libros>> getAll(){
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    //servicio para obtener un libro por id
    public Optional<Libros> findBookById(Long id) {
        return repository.findById(id);
    }

    public CustomResponse<Libros> update(Libros libro){
        return new CustomResponse<>(
                this.repository.save(libro),
                false,
                200,
                "ok"
        );
    }



    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Libros> insert(Libros libro){
        return new CustomResponse<>(
                this.repository.save(libro),
                false,
                200,
                "ok"
        );
    }
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Libros> delete(Long id){
        this.repository.deleteById(id);
        return new CustomResponse<>(
                null,
                false,
                200,
                "ok"
        );
    }






}
