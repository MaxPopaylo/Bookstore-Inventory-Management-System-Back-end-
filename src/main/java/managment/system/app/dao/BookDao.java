package managment.system.app.dao;

import lombok.RequiredArgsConstructor;
import managment.system.app.dto.BookRequestDto;
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
public class BookDao {

    private final BookRepository repository;

    public List<Book> getAll() {
        return repository.findAll();
    }

    public Book getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @Transactional
    public Book save(BookRequestDto dto) {
        Book book = repository.save(BookMapper.mapper.toEntityFromRequest(dto));
        if (book == null) throw new BookNotSavedException();

        return book;
    }

    @Transactional
    public void delete(UUID id) {
        Book book = getById(id);
        repository.delete(book);
    }

    @Transactional
    public Book update(BookRequestDto dto, UUID id) {
        Book oldBook = getById(id);
        oldBook.setTitle(dto.getTitle() != null ? dto.getTitle() : oldBook.getTitle());
        oldBook.setAuthor(dto.getAuthor() != null ? dto.getAuthor() : oldBook.getAuthor());
        oldBook.setIsbn(dto.getIsbn() != null ? dto.getIsbn() : oldBook.getIsbn());
        oldBook.setQuantity(dto.getQuantity() != null ? dto.getQuantity() : oldBook.getQuantity());

        return save(BookMapper.mapper.toRequestDto(oldBook));
    }

}
