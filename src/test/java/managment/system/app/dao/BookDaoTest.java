package managment.system.app.dao;

import com.google.protobuf.Empty;
import managment.system.app.dto.BookDto;
import managment.system.app.entity.Book;
import managment.system.app.reporitory.BookRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("unit")
public class BookDaoTest {

    @Mock
    public BookRepository repository;

    @InjectMocks
    public BookDao dao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private final Book defaultBook = new Book();
    private final BookDto defaultDto = new BookDto();
    private final UUID bookUUID = UUID.randomUUID();

    @BeforeEach
    public void initializeBooks() {
        defaultBook.setId(bookUUID);
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
    @DisplayName("Junit test for getting all entities from database")
    void shouldProperlyGetAllEntity() {

        when(repository.findAll()).thenReturn(Flux.just(defaultBook, defaultBook));

        StepVerifier.create(dao.getAll())
                .expectNextCount(1)
                .expectNextMatches(book ->
                        book.getId().equals(defaultBook.getId()) &&
                        book.getTitle().equals(defaultBook.getTitle()))
                .expectComplete()
                .verify();

    }

    @Test
    @DisplayName("Junit test for getting entity by ID from database")
    void shouldProperlyGetEntityById() {

        when(repository.findById(bookUUID)).thenReturn(Mono.just(defaultBook));

        StepVerifier.create(dao.getById(bookUUID))
                .expectNextMatches(book ->
                        book.getId().equals(defaultBook.getId()) &&
                        book.getTitle().equals(defaultBook.getTitle()))
                .expectComplete()
                .verify();

    }

    @Test
    @DisplayName("Junit test for saving entity into database")
    void shouldProperlySaveEntity() {

        when(repository.save(any(Book.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(dao.save(defaultDto))
                .expectNextMatches(book ->
                        book.getTitle().equals(defaultBook.getTitle()) &&
                        book.getAuthor().equals(defaultBook.getAuthor()))
                .expectComplete()
                .verify();

        verify(repository, times(1)).save(any(Book.class));

    }

    @Test
    @DisplayName("Junit test for deleting entity by ID from database")
    void shouldProperlyDeleteEntity() {

        when(repository.findById(bookUUID)).thenReturn(Mono.just(defaultBook));
        when(repository.delete(any(Book.class))).thenReturn(Mono.empty());

        StepVerifier.create(dao.delete(bookUUID))
                .expectNextMatches(response -> response.equals(Empty.getDefaultInstance()))
                .expectComplete()
                .verify();

        verify(repository, times(1)).delete(defaultBook);

    }

    @Test
    @DisplayName("Junit test for updating entity by ID from database")
    void shouldProperlyUpdateEntity() {

        BookDto dto = new BookDto();
        dto.setTitle("New Title");
        dto.setAuthor("New Author");

        when(repository.findById(bookUUID)).thenReturn(Mono.just(defaultBook));
        when(repository.save(any(Book.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));


        StepVerifier.create(dao.update(bookUUID, dto))
                .expectNextMatches(book ->
                        book.getTitle().equals(dto.getTitle()) &&
                        book.getAuthor().equals(dto.getAuthor()))
                .expectComplete()
                .verify();

        verify(repository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Junit test for subtracting to the quantity into entity by ID from database")
    void shouldProperlyDeductFromQuantityFromEntity() {

        when(repository.findById(bookUUID)).thenReturn(Mono.just(defaultBook));
        when(repository.save(any(Book.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        int oldQuantity = defaultBook.getQuantity();

        StepVerifier.create(dao.sell(bookUUID, 2))
                .expectNextMatches(book ->
                        book.getQuantity().equals(oldQuantity - 2))
                .expectComplete()
                .verify();

        verify(repository, times(1)).save(any(Book.class));

    }

    @Test
    @DisplayName("Junit test for adding to the quantity into entity by ID from database")
    void shouldProperlyAddIntoQuantityFromEntity() {

        when(repository.findById(bookUUID)).thenReturn(Mono.just(defaultBook));
        when(repository.save(any(Book.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        int oldQuantity = defaultBook.getQuantity();

        StepVerifier.create(dao.receive(bookUUID, 2))
                .expectNextMatches(book ->
                        book.getQuantity().equals(oldQuantity + 2))
                .expectComplete()
                .verify();

        verify(repository, times(1)).save(any(Book.class));
    }

}
