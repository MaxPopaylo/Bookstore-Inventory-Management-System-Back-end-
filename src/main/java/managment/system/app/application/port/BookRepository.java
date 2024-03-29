package managment.system.app.application.port;

import com.google.protobuf.Empty;
import managment.system.app.domain.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BookRepository {

    Flux<Book> findAll();
    Mono<Book> findById(UUID id);
    Mono<Book> save(Book dto);
    Mono<Empty> delete(Book book);

}
