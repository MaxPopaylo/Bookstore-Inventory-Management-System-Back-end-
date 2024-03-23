package managment.system.app.mapper;

import managment.system.app.dto.BookDto;
import managment.system.app.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class BookMapperTest {

    @Autowired
    private BookMapper mapper;


    @Test
    void shouldProperlyMapEntityToDto() {

        Book book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle("Correct Title");

        BookDto dto = mapper.toDto(book);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getId(), book.getId());
        Assertions.assertEquals(dto.getTitle(), book.getTitle());

    }

    @Test
    void shouldProperlyMapDtoToEntity() {

        BookDto dto = new BookDto();
        dto.setId(UUID.randomUUID());
        dto.setTitle("Correct Title");

        Book book = mapper.toEntity(dto);

        Assertions.assertNotNull(book);
        Assertions.assertEquals(book.getId(), dto.getId());
        Assertions.assertEquals(book.getTitle(), dto.getTitle());

    }

}
