package managment.system.app.application;


import managment.system.app.domain.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper mapper = Mappers.getMapper( BookMapper.class );

    BookDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    Book toEntity(BookDto dto);


}
