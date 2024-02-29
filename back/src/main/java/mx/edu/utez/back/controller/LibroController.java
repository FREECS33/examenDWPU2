package mx.edu.utez.back.controller;


import mx.edu.utez.back.model.Libros;
import mx.edu.utez.back.service.LibrosService;
import mx.edu.utez.back.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:5173")
public class LibroController {
    @Autowired
    private LibrosService service;

    @GetMapping("/buscarPorNombre")
    public List<Libros> buscarPorNombre(@RequestParam String nombre) {
        return service.buscarPorNombre(nombre);
    }

    @GetMapping("/alfabetico")
    public List<Libros> buscarConImagenOrdenAlfabetico() {
        return service.buscarConImagenOrdenAlfabetico();
    }

    @GetMapping("/fecha-desc")
    public List<Libros> buscarConImagenOrdenFechaPublicacionDesc() {
        return service.buscarOrdenFechaPublicacionDesc();
    }

    @GetMapping("/autor")
    public List<Libros> buscarConImagenOrdenAutor() {
        return service.buscarOrdenAutor();
    }
    @GetMapping("/con-imagen")
    public List<Libros> buscarLibrosConImagen() {
        return service.buscarLibrosConImagen();
    }

    @GetMapping("/")
    public CustomResponse<List<Libros>> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libros> getBookById(@PathVariable Long id) {
        return service.findBookById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public CustomResponse<Libros> addNewProcedureType(@RequestBody Libros libros) {
        return service.insert(libros);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    // servicio para actualizar libro por id
    @PutMapping("/{id}")
    public ResponseEntity<Libros> update(@PathVariable Long id, @Validated @RequestBody Libros libros) {
        if (!service.findBookById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        libros.setId(id);
        return ResponseEntity.ok(service.update(libros).getData());
    }
}
