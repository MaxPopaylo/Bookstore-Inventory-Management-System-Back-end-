package managment.system.app.application;

import lombok.Data;

@Data
public class BookDto {

    private String title;
    private String author;
    private String isbn;
    private Integer quantity;
}
