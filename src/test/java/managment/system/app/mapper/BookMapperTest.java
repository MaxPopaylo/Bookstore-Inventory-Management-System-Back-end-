package managment.system.app.mapper;

import app.grpc.book_types.BookTypes;
import managment.system.app.dto.BookDto;
import managment.system.app.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class BookMapperTest {

    private final Book defaultBook = new Book();
    private final BookDto defaultDto = new BookDto();

    @BeforeEach
    public void initializeBooks() {
        defaultBook.setId(UUID.randomUUID());
        defaultBook.setTitle("Correct Title");
        defaultBook.setAuthor("Correct Author");
        defaultBook.setIsbn("Isbn");
        defaultBook.setQuantity(5);

        defaultDto.setTitle("Correct Title");
        defaultDto.setAuthor("Correct Author");
        defaultDto.setIsbn("Isbn");
        defaultDto.setQuantity(5);
    }

    @Test
    @DisplayName("Junit test for mapping Book into BookDto")
    void shouldProperlyMapEntityToDto() {

        BookDto dto = BookMapper.mapper.toDto(defaultBook);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getTitle(), defaultBook.getTitle());
        Assertions.assertEquals(dto.getAuthor(), defaultBook.getAuthor());

    }

    @Test
    @DisplayName("Junit test for mapping BookDto into Book")
    void shouldProperlyMapDtoToEntity() {

        Book book = BookMapper.mapper.toEntity(defaultDto);

        Assertions.assertNotNull(book);
        Assertions.assertEquals(book.getTitle(), defaultDto.getTitle());
        Assertions.assertEquals(book.getAuthor(), defaultDto.getAuthor());

    }

    @Test
    @DisplayName("Junit test for mapping Book into BookTypes.Book")
    void shouldProperlyMapEntityToProtoEntity() {

        BookTypes.Book protoBook = BookMapper.mapper.toProtoEntity(defaultBook);

        Assertions.assertNotNull(protoBook);
        Assertions.assertEquals(protoBook.getTitle(), defaultBook.getTitle());
        Assertions.assertEquals(protoBook.getAuthor(), defaultBook.getAuthor());
        Assertions.assertEquals(protoBook.getIsbn(), defaultBook.getIsbn());

    }

    @Test
    @DisplayName("Junit test for mapping BookTypes.BookDTO into BookDto")
    void shouldProperlyMapProtoDtoToDto() {

        var protoDto = BookTypes.BookDTO.newBuilder()
                .setTitle(defaultDto.getTitle())
                .setAuthor(defaultDto.getAuthor())
                .setIsbn(defaultDto.getIsbn())
                .setQuantity(defaultDto.getQuantity())
                .build();

        BookDto dto = BookMapper.mapper.toDtoFromProto(protoDto);

        Assertions.assertNotNull(protoDto);
        Assertions.assertEquals(dto.getTitle(), protoDto.getTitle());
        Assertions.assertEquals(dto.getAuthor(), protoDto.getAuthor());
        Assertions.assertEquals(dto.getIsbn(), protoDto.getIsbn());

    }

}
