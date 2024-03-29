package managment.system.app.domain;

import lombok.Data;
import java.util.UUID;

@Data
public class Book {

    private UUID id;
    private String title;
    private String author;
    private String isbn;
    private Integer quantity;

}
