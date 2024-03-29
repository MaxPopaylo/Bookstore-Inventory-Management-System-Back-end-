package managment.system.app.adapter.out.r2dbc;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface R2dbcBookRepository extends ReactiveCrudRepository<BookDbo, UUID> {
}
