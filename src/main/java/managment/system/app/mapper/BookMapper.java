package managment.system.app.mapper;

import managment.system.app.dto.BookRequestDto;
import managment.system.app.dto.BookResponseDto;
import managment.system.app.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper mapper = Mappers.getMapper( BookMapper.class );

    BookRequestDto toRequestDto(Book book);
    BookResponseDto toResponseDto(Book book);

    @Mapping(target = "id", ignore = true)
    Book toEntityFromRequest(BookRequestDto dto);
    Book toEntityFromResponse(BookResponseDto dto);
}
