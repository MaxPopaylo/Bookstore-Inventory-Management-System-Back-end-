package managment.system.app.adapter.out.r2dbc;


import com.google.protobuf.Empty;
import lombok.RequiredArgsConstructor;
import managment.system.app.application.ports.BookRepository;
import managment.system.app.domain.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class R2dbcBookRepositoryAdapter implements BookRepository {

    private final R2dbcBookRepository repository;


    @Override
    public Flux<Book> findAll() {
        return repository.findAll().map(R2dbcBookMapper.mapper::toEntity);
    }

    @Override
    public Mono<Book> findById(UUID id) {
        return repository.findById(id)
                .map(R2dbcBookMapper.mapper::toEntity);
    }

    @Override
    @Transactional
    public Mono<Book> save(Book book) {
        return repository
                .save(R2dbcBookMapper.mapper.toDbo(book))
                .map(R2dbcBookMapper.mapper::toEntity);
    }

    @Override
    @Transactional
    public Mono<Empty> delete(Book book) {
        return repository.delete(R2dbcBookMapper.mapper.toDbo(book))
                .then(Mono.just(Empty.getDefaultInstance()));
    }


}
