package com.nequi.demo.dto;

import com.nequi.demo.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper( BookMapper.class );

    @Mapping(source = "author", target = "authorName")
    BookDTO bookToBookDTO(Book book);

    @Mapping(source = "authorName", target = "author")
    Book bookDTOToBook(BookDTO bookDTO);
}
