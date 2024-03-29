package managment.system.app.adapter.in.grpc;


import app.grpc.book_types.BookTypes;
import managment.system.app.application.BookDto;
import managment.system.app.domain.Book;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("unit")
public class GrpcBookMapperTest {

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
    @DisplayName("Junit test for mapping Book into BookTypes.Book")
    void shouldProperlyMapEntityToProtoEntity() {

        BookTypes.Book protoBook = GrpcBookMapper.mapper.toProtoEntity(defaultBook);

        assertNotNull(protoBook);
        assertEquals(protoBook.getTitle(), defaultBook.getTitle());
        assertEquals(protoBook.getAuthor(), defaultBook.getAuthor());
        assertEquals(protoBook.getIsbn(), defaultBook.getIsbn());

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

        BookDto dto = GrpcBookMapper.mapper.toDtoFromProto(protoDto);

        assertNotNull(protoDto);
        assertEquals(dto.getTitle(), protoDto.getTitle());
        assertEquals(dto.getAuthor(), protoDto.getAuthor());
        assertEquals(dto.getIsbn(), protoDto.getIsbn());

    }

}
