package managment.system.app.mapper;

import managment.system.app.dto.BookDto;
import managment.system.app.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper mapper = Mappers.getMapper( BookMapper.class );

    BookDto toDto(Book book);
    Book toEntity(BookDto dto);

}
