package managment.system.app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private UUID id;

    private String title;
    private String author;
    private String isbn;
    private Integer quantity;


}
