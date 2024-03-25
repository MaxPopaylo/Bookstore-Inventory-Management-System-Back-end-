package managment.system.app.mapper;

import app.grpc.book_types.BookTypes;
import managment.system.app.dto.BookDto;
import managment.system.app.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class BookMapperTest {

    @Test
    void shouldProperlyMapEntityToDto() {

        Book book = new Book();
        book.setTitle("Correct Title");

        BookDto dto = BookMapper.mapper.toDto(book);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getTitle(), book.getTitle());

    }

    @Test
    void shouldProperlyMapDtoToEntity() {

        BookDto dto = new BookDto();
        dto.setTitle("Correct Title");

        Book book = BookMapper.mapper.toEntity(dto);

        Assertions.assertNotNull(book);
        Assertions.assertEquals(book.getTitle(), dto.getTitle());

    }

    @Test
    void shouldProperlyMapEntityToProtoEntity() {

        Book book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle("Correct Title");
        book.setAuthor("Correct Author");
        book.setIsbn("Isbn");
        book.setQuantity(1);

        BookTypes.Book protoBook = BookMapper.mapper.toProtoEntity(book);

        Assertions.assertNotNull(book);
        Assertions.assertEquals(book.getTitle(), protoBook.getTitle());
        Assertions.assertEquals(book.getAuthor(), protoBook.getAuthor());
        Assertions.assertEquals(book.getIsbn(), protoBook.getIsbn());
        Assertions.assertEquals(book.getQuantity(), protoBook.getQuantity());

    }

    @Test
    void shouldProperlyMapDtoToProtoDto() {

        BookDto dto = new BookDto();
        dto.setTitle("Correct Title");
        dto.setAuthor("Correct Author");
        dto.setIsbn("Isbn");
        dto.setQuantity(1);

        BookTypes.BookDTO protoDto = BookMapper.mapper.toProtoDto(dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getTitle(), protoDto.getTitle());
        Assertions.assertEquals(dto.getAuthor(), protoDto.getAuthor());
        Assertions.assertEquals(dto.getIsbn(), protoDto.getIsbn());
        Assertions.assertEquals(dto.getQuantity(), protoDto.getQuantity());

    }

}
