package managment.system.app.adapter.out.r2dbc;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@NoArgsConstructor
@Table(name = "books")
public class BookDbo {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private UUID id;

    private String title;
    private String author;
    private String isbn;
    private Integer quantity;

}