package managment.system.app.adapter.out.r2dbc;

import com.google.protobuf.Empty;
import managment.system.app.domain.Book;
import org.junit.jupiter.api.*;
import org.mockito.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;

@Tag("unit")
public class R2dbcBookRepositoryAdapterTest {

    @Mock
    private R2dbcBookRepository repository;

    @InjectMocks
    private R2dbcBookRepositoryAdapter adapter;

    private final BookDbo defaultDbo = new BookDbo();
    private final Book defaultBook = new Book();
    private final UUID id = UUID.randomUUID();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void initializeBooks() {
        defaultDbo.setId(id);
        defaultDbo.setTitle("Default Title");
        defaultDbo.setAuthor("Default Author");
        defaultDbo.setIsbn("Default Isbn");

        defaultBook.setId(id);
        defaultBook.setTitle("Default Title");
        defaultBook.setAuthor("Default Author");
        defaultBook.setIsbn("Default Isbn");
    }

    @Test
    @DisplayName("Junit test for getting all books from database")
    void shouldProperlyGetAllBooks() {
        when(repository.findAll()).thenReturn(Flux.just(defaultDbo));

        StepVerifier.create(adapter.findAll())
                 .expectNext(defaultBook)
                 .expectComplete()
                 .verify();

        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Junit test for getting book by ID from database")
    void shouldProperlyGetBookById() {
        when(repository.findById(id)).thenReturn(Mono.just(defaultDbo));

        StepVerifier.create(adapter.findById(id))
                .expectNext(defaultBook)
                .expectComplete()
                .verify();

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Junit test for saving book into database")
    void shouldProperlySaveBook() {
        when(repository.save(any())).thenReturn(Mono.just(defaultDbo));

        StepVerifier.create(adapter.save(defaultBook))
                .expectNext(defaultBook)
                .expectComplete()
                .verify();

        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Junit test for deleting book from database")
    void shouldProperlyDeleteBook() {
        when(repository.delete(any())).thenReturn(Mono.empty());

        StepVerifier.create(adapter.delete(defaultBook))
                .expectNextMatches(response -> response.equals(Empty.getDefaultInstance()))
                .expectComplete()
                .verify();

        verify(repository, times(1)).delete(any(BookDbo.class));
    }

}