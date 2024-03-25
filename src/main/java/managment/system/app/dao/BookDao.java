package managment.system.app.dao;

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

import java.util.List;
import java.util.Objects;
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
    public Book save(BookDto dto) {
        return saveEntityIntoDb(BookMapper.mapper.toEntity(dto));
    }

    @Transactional
    public void delete(UUID id) {
        Book book = getById(id);
        repository.delete(book);
    }

    @Transactional
    public Book update(UUID id, BookDto dto) {

        Book oldBook = getById(id);
        oldBook.setTitle(StringUtils.defaultIfBlank(dto.getTitle(), oldBook.getTitle()));
        oldBook.setAuthor(StringUtils.defaultIfBlank(dto.getAuthor(), oldBook.getAuthor()));
        oldBook.setIsbn(StringUtils.defaultIfBlank(dto.getIsbn(), oldBook.getIsbn()));

        return saveEntityIntoDb(oldBook);
    }

    @Transactional
    public Book sell(UUID id, int quantity) {

        Book oldBook = getById(id);
        if (oldBook.getQuantity() < quantity) {
            throw new BookNotInStockException(quantity);
        }

        oldBook.setQuantity(oldBook.getQuantity() - quantity);

        return saveEntityIntoDb(oldBook);
    }

    @Transactional
    public Book receive(UUID id, int quantity) {
        Book oldBook = getById(id);
        oldBook.setQuantity(oldBook.getQuantity() + quantity);

        return saveEntityIntoDb(oldBook);
    }


    @Transactional
    protected Book saveEntityIntoDb(Book entity) {
        Book book = repository.save(entity);
        if (book == null) throw new BookNotSavedException();

        return book;
    }

}
