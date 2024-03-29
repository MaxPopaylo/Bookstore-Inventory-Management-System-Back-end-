package managment.system.app.adapter.in.grpc;

import app.grpc.book.ReactorBookServiceGrpc;
import app.grpc.book_types.BookTypes;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import managment.system.app.application.BookDto;
import managment.system.app.application.BookService;
import managment.system.app.configuration.AbstractTestcontainersIntegrationTest;
import managment.system.app.domain.Book;
import managment.system.app.domain.exceptions.BookNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import reactor.test.StepVerifier;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest

@DirtiesContext
@Tag("integration")
public class GrpcBookServiceTest extends AbstractTestcontainersIntegrationTest {

    //INITIALISE DB SERVICE
    @Autowired
    private BookService service;

    //INITIALISE GRPC VARIABLES
    private final ManagedChannel channel = ManagedChannelBuilder
            .forAddress("localhost", 9080)
            .usePlaintext()
            .build();

    private final ReactorBookServiceGrpc.ReactorBookServiceStub stub =
            ReactorBookServiceGrpc.newReactorStub(channel);


    //INITIALISE DEFAULT VARIABLES
    private UUID id;
    private Book defaultBook;
    private final BookDto defaultDto = new BookDto();

    @BeforeEach
    public void initializeBooks() {
        defaultDto.setTitle("Correct Title");
        defaultDto.setAuthor("Correct Author");
        defaultDto.setIsbn("Isbn");
        defaultDto.setQuantity(5);


        defaultBook = service.save(defaultDto).block();
        id = Objects.requireNonNull(defaultBook).getId();
    }

    @Test
    @DisplayName("Integration  test for getting book by id from grpc server")
    void shouldProperlyGetBookById() {

        BookTypes.getByIdRequest request = BookTypes.getByIdRequest.newBuilder()
                .setId(convertToProtoUUID(id))
                .build();

        StepVerifier
                .create(stub.getById(request))
                .assertNext(response -> {
                    assertNotNull(response.getBook());
                    assertEquals(response.getBook().getTitle(), defaultBook.getTitle());
                })
                .verifyComplete();
    }


    @Test
    @DisplayName("Integration  test for saving entity into grpc server")
    void shouldProperlySaveBook() {

        BookTypes.BookDTO dto = BookTypes.BookDTO.newBuilder()
                .setTitle(defaultDto.getTitle())
                .setAuthor(defaultDto.getAuthor())
                .setIsbn(defaultDto.getIsbn())
                .setQuantity(defaultDto.getQuantity())
                .build();

        BookTypes.saveRequest request =  BookTypes.saveRequest.newBuilder()
                .setDto(dto)
                .build();

        StepVerifier
                .create(stub.save(request))
                .assertNext(response ->
                        StepVerifier
                                .create(service.getById(convertToUUID(response.getBook().getId())))
                                .assertNext(val -> {
                                    assertNotNull(val);
                                    assertEquals(val.getTitle(), dto.getTitle());
                                    assertEquals(val.getAuthor(), dto.getAuthor());
                                })
                                .verifyComplete()
                )
                .verifyComplete();

    }

    @Test
    @DisplayName("Integration  test for updating entity into grpc server")
    void shouldProperlyUpdateBook() {

        BookTypes.BookDTO dto = BookTypes.BookDTO.newBuilder()
                .setTitle("New Title")
                .setAuthor("New Author")
                .setIsbn(defaultDto.getIsbn())
                .setQuantity(defaultDto.getQuantity())
                .build();

        BookTypes.updateRequest request =  BookTypes.updateRequest.newBuilder()
                .setDto(dto)
                .setId(convertToProtoUUID(id))
                .build();

        StepVerifier
                .create(stub.update(request))
                .assertNext(response ->
                        StepVerifier
                                .create(service.getById(convertToUUID(response.getBook().getId())))
                                .assertNext(val -> {
                                    assertNotNull(val);
                                    assertEquals(val.getTitle(), dto.getTitle());
                                    assertEquals(val.getAuthor(), dto.getAuthor());
                                })
                                .verifyComplete()
                )
                .verifyComplete();


    }

    @Test
    @DisplayName("Integration  test for subtracting to the quantity into entity by ID from grpc server")
    void shouldProperlyDeductFromQuantityFromBook() {

        var oldQuantity = defaultBook.getQuantity();
        BookTypes.sellRequest request = BookTypes.sellRequest.newBuilder()
                .setId(convertToProtoUUID(id))
                .setQuantity(2)
                .build();

        StepVerifier
                .create(stub.sell(request))
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(response.getQuantity(), oldQuantity - 2);
                })
                .verifyComplete();

    }

    @Test
    @DisplayName("Integration  test for adding to the quantity into entity by ID grpc server")
    void shouldProperlyAddIntoQuantityFromBook() {

        var oldQuantity = defaultBook.getQuantity();
        BookTypes.receiveRequest request = BookTypes.receiveRequest.newBuilder()
                .setId(convertToProtoUUID(id))
                .setQuantity(2)
                .build();

        StepVerifier
                .create(stub.receive(request))
                .assertNext(response -> {
                    assertNotNull(response);
                    assertEquals(response.getQuantity(), oldQuantity + 2);
                })
                .verifyComplete();

    }

    @Test
    @DisplayName("Integration test for getting all books from grpc server")
    void shouldProperlyGetAllBooks() {

        var responseBook = stub.getAll(Empty.newBuilder().build()).blockFirst();
        Book book = service.getAll().blockFirst();

        assert book != null;

        assertNotNull(responseBook);
        assertEquals(convertToUUID(responseBook.getId()), book.getId());
        assertEquals(responseBook.getTitle(), book.getTitle());
    }



    @Test
    @DisplayName("Integration  test for deleting book by id from grpc server")
    void shouldProperlyDeleteBookById() {
        BookTypes.deleteRequest request = BookTypes.deleteRequest.newBuilder()
                .setId(convertToProtoUUID(id))
                .build();

        StepVerifier
                .create(stub.delete(request))
                .expectNextMatches(response -> response.equals(Empty.getDefaultInstance()))
                .expectComplete()
                .verify();


        StepVerifier
                .create(service.getById(id))
                .expectError(BookNotFoundException.class)
                .verify();

    }

    private BookTypes.UUID convertToProtoUUID(UUID id) {
        return BookTypes.UUID.newBuilder()
                .setValue(id.toString())
                .build();
    }

    private UUID convertToUUID(BookTypes.UUID id) {
        return UUID.fromString(id.getValue());
    }

}
