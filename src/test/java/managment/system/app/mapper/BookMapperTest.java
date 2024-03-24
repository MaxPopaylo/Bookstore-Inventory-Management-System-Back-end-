package managment.system.app.mapper;

import managment.system.app.dto.BookResponseDto;
import managment.system.app.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class BookMapperTest {

    private final UUID testUUID = UUID.randomUUID();

    @Test
    void shouldProperlyMapEntityToResponseDto() {

        Book book = new Book();
        book.setId(testUUID);
        book.setTitle("Correct Title");

        BookResponseDto dto = BookMapper.mapper.toResponseDto(book);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getId(), book.getId());
        Assertions.assertEquals(dto.getTitle(), book.getTitle());

    }

    @Test
    void shouldProperlyMapResponseDtoToEntity() {

        BookResponseDto dto = new BookResponseDto();
        dto.setId(testUUID);
        dto.setTitle("Correct Title");

        Book book = BookMapper.mapper.toEntityFromResponse(dto);

        Assertions.assertNotNull(book);
        Assertions.assertEquals(book.getId(), dto.getId());
        Assertions.assertEquals(book.getTitle(), dto.getTitle());

    }

}
