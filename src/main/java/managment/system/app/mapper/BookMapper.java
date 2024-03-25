package managment.system.app.mapper;

import app.grpc.book_types.BookTypes;
import managment.system.app.dto.BookDto;
import managment.system.app.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper mapper = Mappers.getMapper( BookMapper.class );

    BookDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    Book toEntity(BookDto dto);

    @Mapping(source = "id", target = "id", qualifiedByName = "convertId")
    BookTypes.Book toProtoEntity(Book book);

    BookDto toDtoFromProto(BookTypes.BookDTO dto);

    @Named("convertId")
    default BookTypes.UUID convertId(UUID id) {
        return BookTypes.UUID.newBuilder()
                .setValue(id.toString())
                .build();
    }

}
