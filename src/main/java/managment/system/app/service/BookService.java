package managment.system.app.service;

import lombok.RequiredArgsConstructor;
import managment.system.app.reporitory.BookRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

}
