package managment.system.app.entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Data
@NoArgsConstructor
@Table(name = "books")
public class Book {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private UUID id;

    private String title;
    private String author;
    private String isbn;
    private Integer quantity;


}
