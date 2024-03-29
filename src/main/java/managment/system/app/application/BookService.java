package managment.system.app.application;

import com.google.protobuf.Empty;
import managment.system.app.domain.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BookService {

     Flux<Book> getAll();
     Mono<Book> getById(UUID id);
     Mono<Book> save(BookDto dto);
     Mono<Empty> delete(UUID id);
     Mono<Book> update(UUID id, BookDto dto);
     Mono<Book> sell(UUID id, int quantity);
     Mono<Book> receive(UUID id, int quantity);

}
