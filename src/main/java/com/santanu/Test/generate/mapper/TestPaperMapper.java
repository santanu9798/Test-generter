package com.santanu.Test.generate.mapper;


import com.santanu.Test.generate.dto.PaperDTO;
import com.santanu.Test.generate.model.Paper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestPaperMapper {

    PaperDTO toDTO(Paper paper);

    Paper toEntity(PaperDTO paperDTO);

}
