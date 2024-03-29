package managment.system.app.adapter.out.r2dbc;

import managment.system.app.domain.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface R2dbcBookMapper {

    R2dbcBookMapper mapper = Mappers.getMapper( R2dbcBookMapper.class );

    Book toEntity(BookDbo dbo);
    BookDbo toDbo(Book book);

}
