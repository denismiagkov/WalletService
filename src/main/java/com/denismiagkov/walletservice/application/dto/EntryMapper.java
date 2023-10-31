package com.denismiagkov.walletservice.application.dto;

import com.denismiagkov.walletservice.domain.model.Entry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EntryMapper {
    EntryMapper INSTANCE = Mappers.getMapper(EntryMapper.class);

    @Mapping(source = "login", target = "login")
    @Mapping(source = "password", target = "password")
    EntryDto toEntryDto(Entry entry);

    List<EntryDto> toEntryDtoList(List<Entry> entries);

}
