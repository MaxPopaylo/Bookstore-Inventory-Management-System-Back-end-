package managment.system.app.dao;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import lombok.RequiredArgsConstructor;
import managment.system.app.dto.BookDto;
import managment.system.app.entity.Book;
import managment.system.app.mapper.BookMapper;
import managment.system.app.reporitory.BookRepository;
import managment.system.app.utils.exceptions.BookNotFoundException;
import managment.system.app.utils.exceptions.BookNotInStockException;
import managment.system.app.utils.exceptions.BookNotSavedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookDao {

    private final BookRepository repository;

    public Flux<Book> getAll() {
        return repository.findAll();
    }

    public Mono<Book> getById(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new BookNotFoundException()));
    }

    @Transactional
    public Mono<Book> save(BookDto dto) {
        return saveEntityIntoDb(BookMapper.mapper.toEntity(dto));
    }

    @Transactional
    public Mono<TypeResolutionContext.Empty> delete(UUID id) {
        return getById(id)
                .flatMap(repository::delete)
                .then(Mono.empty());
    }

    @Transactional
    public Mono<Book> update(UUID id, BookDto dto) {
        return getById(id)
                .flatMap(val ->{
                    val.setTitle(StringUtils.defaultIfBlank(dto.getTitle(), val.getTitle()));
                    val.setAuthor(StringUtils.defaultIfBlank(dto.getAuthor(), val.getAuthor()));
                    val.setIsbn(StringUtils.defaultIfBlank(dto.getIsbn(), val.getIsbn()));

                    return saveEntityIntoDb(val);
                });
    }

    @Transactional
    public Mono<Book> sell(UUID id, int quantity) {
        return getById(id)
                .flatMap(val -> {
                    if (val.getQuantity() < quantity) return Mono.error(new BookNotInStockException(quantity));

                    val.setQuantity(val.getQuantity() - quantity);
                    return saveEntityIntoDb(val);
                });
    }

    @Transactional
    public Mono<Book> receive(UUID id, int quantity) {
        return getById(id)
                .flatMap(val -> {
                    val.setQuantity(val.getQuantity() + quantity);
                    return saveEntityIntoDb(val);
                });
    }


    @Transactional
    protected Mono<Book> saveEntityIntoDb(Book entity) {
       return repository.save(entity)
               .switchIfEmpty(Mono.error(new BookNotSavedException()));
    }

}
