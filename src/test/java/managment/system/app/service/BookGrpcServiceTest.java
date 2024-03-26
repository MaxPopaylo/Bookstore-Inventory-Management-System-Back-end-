package managment.system.app.service;


import app.grpc.book.BookServiceGrpc;
import app.grpc.book_types.BookTypes;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import managment.system.app.dao.BookDao;
import managment.system.app.dto.BookDto;
import managment.system.app.entity.Book;
import managment.system.app.mapper.BookMapper;
import managment.system.app.utils.exceptions.BookNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.nio.channels.Channel;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@Testcontainers
@SpringBootTest
public class BookGrpcServiceTest {

    //INITIALISE DB SERVICE
    @Autowired
    private BookDao dao;

    //INITIALISE DB TEST CONTAINER
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    //INITIALISE GRPC VARIABLES
    private final ManagedChannel channel = ManagedChannelBuilder
            .forAddress("localhost", 9080)
            .usePlaintext()
            .build();

    private final BookServiceGrpc.BookServiceBlockingStub stub =
            BookServiceGrpc.newBlockingStub(channel);


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

        defaultBook = dao.save(defaultDto);
        id = defaultBook.getId();
    }


    @Test
    @DisplayName("Junit test for getting book by id from grpc server")
    void shouldProperlyGetBookById() {

        BookTypes.getByIdRequest request = BookTypes.getByIdRequest.newBuilder()
                .setId(convertToProtoUUID(id))
                .build();

        BookTypes.getByIdResponse expected = BookTypes.getByIdResponse.newBuilder()
                .setBook(BookMapper.mapper.toProtoEntity(defaultBook))
                .build();

        var response = stub.getById(request);
        Assertions.assertNotNull(response.getBook());
        Assertions.assertEquals(response, expected);
    }


    @Test
    @DisplayName("Junit test for saving entity into grpc server")
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
        var response = stub.save(request);

        var book = dao.getById(convertToUUID(response.getBook().getId()));
        BookTypes.saveResponse expected = BookTypes.saveResponse.newBuilder()
                .setBook(BookMapper.mapper.toProtoEntity(book))
                .build();

        Assertions.assertNotNull(book);
        Assertions.assertEquals(response, expected);
    }

    @Test
    @DisplayName("Junit test for updating entity into grpc server")
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
        var response = stub.update(request);

        var expectedBook = dao.getById(id);
        BookTypes.updateResponse expected = BookTypes.updateResponse.newBuilder()
                .setBook(BookMapper.mapper.toProtoEntity(expectedBook))
                .build();

        Assertions.assertNotNull(defaultBook);
        Assertions.assertEquals(response.getBook().getTitle(), dto.getTitle());
        Assertions.assertEquals(response, expected);

    }

    @Test
    @DisplayName("Junit test for subtracting to the quantity into entity by ID from grpc server")
    void shouldProperlyDeductFromQuantityFromBook() {

        var oldQuantity = defaultBook.getQuantity();
        BookTypes.sellRequest request = BookTypes.sellRequest.newBuilder()
                .setId(convertToProtoUUID(id))
                .setQuantity(2)
                .build();

        var response = stub.sell(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getQuantity(), oldQuantity - 2);

    }

    @Test
    @DisplayName("Junit test for adding to the quantity into entity by ID grpc server")
    void shouldProperlyAddIntoQuantityFromBook() {

        var oldQuantity = defaultBook.getQuantity();
        BookTypes.receiveRequest request = BookTypes.receiveRequest.newBuilder()
                .setId(convertToProtoUUID(id))
                .setQuantity(2)
                .build();

        var response = stub.receive(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getQuantity(), oldQuantity + 2);

    }


    @Test
    @DisplayName("Junit test for deleting book by id from grpc server")
    void shouldProperlyDeleteBookById() {
        BookTypes.deleteRequest request = BookTypes.deleteRequest.newBuilder()
                .setId(convertToProtoUUID(id))
                .build();
        stub.delete(request);

        Assertions.assertThrows(BookNotFoundException.class, () -> {
            dao.getById(id);
        });
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
