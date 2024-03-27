package managment.system.app.reporitory;

import managment.system.app.entity.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, UUID> {
}
