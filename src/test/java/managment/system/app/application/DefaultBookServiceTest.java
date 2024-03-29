package managment.system.app.application;

import com.google.protobuf.Empty;
import managment.system.app.application.port.BookRepository;
import managment.system.app.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@Tag("unit")
public class DefaultBookServiceTest {

    @Mock
    public BookRepository repository;

    @InjectMocks
    public DefaultBookService service;

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

        StepVerifier.create(service.getAll())
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

        StepVerifier.create(service.getById(bookUUID))
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

        StepVerifier.create(service.save(defaultDto))
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

        StepVerifier.create(service.delete(bookUUID))
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


        StepVerifier.create(service.update(bookUUID, dto))
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

        StepVerifier.create(service.sell(bookUUID, 2))
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

        StepVerifier.create(service.receive(bookUUID, 2))
                .expectNextMatches(book ->
                        book.getQuantity().equals(oldQuantity + 2))
                .expectComplete()
                .verify();

        verify(repository, times(1)).save(any(Book.class));
    }
    
}
