package managment.system.app.dto;

import lombok.Data;

@Data
public class BookRequestDto {

    private String title;
    private String author;
    private String isbn;
    private Integer quantity;

}
