package managment.system.app.service;

import lombok.RequiredArgsConstructor;
import managment.system.app.dto.BookDto;
import managment.system.app.entity.Book;
import managment.system.app.mapper.BookMapper;
import managment.system.app.reporitory.BookRepository;
import managment.system.app.utils.exceptions.BookNotFoundException;
import managment.system.app.utils.exceptions.BookNotSavedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository repository;
    public final BookMapper mapper;

    public List<Book> getAll() {
        return repository.findAll();
    }

    public Book getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @Transactional
    public Book save(BookDto dto) {
        Book book = repository.save(mapper.toEntity(dto));
        if (book == null) throw new BookNotSavedException();

        return book;
    }

    @Transactional
    public void delete(UUID id) {
        Book book = getById(id);
        repository.delete(book);
    }

    @Transactional
    public Book update(BookDto dto, UUID id) {
        Book oldBook = getById(id);

        Book updatedBook = Book.builder()
                .title(dto.getTitle() != null ? dto.getTitle() : oldBook.getTitle())
                .author(dto.getAuthor() != null ? dto.getAuthor() : oldBook.getAuthor())
                .isbn(dto.getIsbn() != null ? dto.getIsbn() : oldBook.getIsbn())
                .quantity(dto.getQuantity() != null ? dto.getQuantity() : oldBook.getQuantity())
                .build();


        return save(mapper.toDto(updatedBook));
    }

}
