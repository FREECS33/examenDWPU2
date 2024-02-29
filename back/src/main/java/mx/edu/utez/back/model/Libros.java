package mx.edu.utez.back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "books")
@Data
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name_book",nullable = false)
    private String nameBook;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @Column(name = "image")
    private String image;
}
