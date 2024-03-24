package managment.system.app.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BookDto {

    private String title;
    private String author;
    private String isbn;
    private Integer quantity;
}
