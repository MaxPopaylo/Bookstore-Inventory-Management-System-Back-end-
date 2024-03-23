package managment.system.app.mapper;

import managment.system.app.dto.BookDto;
import managment.system.app.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto(Book book);
    Book toEntity(BookDto dto);

}
