package managment.system.app.adapter.out.r2dbc;

import managment.system.app.domain.Book;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("unit")
public class R2dbcBookMapperTest {

    private final R2dbcBookMapper mapper = R2dbcBookMapper.mapper;
    private final BookDbo defaultDbo = new BookDbo();
    private final Book defaultBook = new Book();

    @BeforeEach
    public void initializeBooks() {
        defaultDbo.setId(UUID.randomUUID());
        defaultDbo.setTitle("Default Title");
        defaultDbo.setAuthor("Default Author");
        defaultDbo.setIsbn("Default Isbn");

        defaultBook.setId(UUID.randomUUID());
        defaultBook.setTitle("Default Title");
        defaultBook.setAuthor("Default Author");
        defaultBook.setIsbn("Default Isbn");
    }

    @Test
    @DisplayName("Junit test for mapping BookDbo to Book")
    void shouldMapDboToBook() {
        Book book = mapper.toEntity(defaultDbo);
        assertNotNull(book);
        assertEquals(defaultDbo.getId(), book.getId());
        assertEquals(defaultDbo.getTitle(), book.getTitle());
        assertEquals(defaultDbo.getAuthor(), book.getAuthor());
        assertEquals(defaultDbo.getIsbn(), book.getIsbn());
    }

    @Test
    @DisplayName("Junit test for mapping Book to BookDbo")
    void shouldMapBookToDbo() {
        BookDbo dbo = mapper.toDbo(defaultBook);
        assertNotNull(dbo);
        assertEquals(defaultBook.getId(), dbo.getId());
        assertEquals(defaultBook.getTitle(), dbo.getTitle());
        assertEquals(defaultBook.getAuthor(), dbo.getAuthor());
        assertEquals(defaultBook.getIsbn(), dbo.getIsbn());
    }
}