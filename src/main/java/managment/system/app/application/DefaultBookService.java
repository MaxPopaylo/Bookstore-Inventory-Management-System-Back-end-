package managment.system.app.application;

import com.google.protobuf.Empty;
import lombok.RequiredArgsConstructor;
import managment.system.app.application.port.BookRepository;
import managment.system.app.domain.Book;
import managment.system.app.domain.exception.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultBookService implements BookService {

    private final BookRepository repository;

    @Override
    public Flux<Book> getAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Book> getById(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new BookNotFoundException()));
    }

    @Override
    public Mono<Book> save(BookDto dto) {
        return saveEntityIntoDb(BookMapper.mapper.toEntity(dto));
    }

    @Override
    public Mono<Empty> delete(UUID id) {
        return getById(id)
                .flatMap(repository::delete)
                .then(Mono.just(Empty.getDefaultInstance()));
    }

    @Override
    public Mono<Book> update(UUID id, BookDto dto) {
        return getById(id)
                .flatMap(val ->{
                    val.setTitle(StringUtils.defaultIfBlank(dto.getTitle(), val.getTitle()));
                    val.setAuthor(StringUtils.defaultIfBlank(dto.getAuthor(), val.getAuthor()));
                    val.setIsbn(StringUtils.defaultIfBlank(dto.getIsbn(), val.getIsbn()));

                    return saveEntityIntoDb(val);
                });
    }

    @Override
    public Mono<Book> sell(UUID id, int quantity) {
        return getById(id)
                .flatMap(val -> {
                    if (val.getQuantity() < quantity) return Mono.error(new BookNotInStockException(quantity));

                    val.setQuantity(val.getQuantity() - quantity);
                    return saveEntityIntoDb(val);
                });
    }

    @Override
    public Mono<Book> receive(UUID id, int quantity) {
        return getById(id)
                .flatMap(val -> {
                    val.setQuantity(val.getQuantity() + quantity);
                    return saveEntityIntoDb(val);
                });
    }

    protected Mono<Book> saveEntityIntoDb(Book entity) {
        return repository.save(entity)
                .switchIfEmpty(Mono.error(new BookNotSavedException()));
    }

}
