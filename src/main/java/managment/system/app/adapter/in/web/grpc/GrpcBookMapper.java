package managment.system.app.adapter.in.web.grpc;

import app.grpc.book_types.BookTypes;
import managment.system.app.domain.Book;
import managment.system.app.application.BookDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GrpcBookMapper {

    GrpcBookMapper mapper = Mappers.getMapper( GrpcBookMapper.class );

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
