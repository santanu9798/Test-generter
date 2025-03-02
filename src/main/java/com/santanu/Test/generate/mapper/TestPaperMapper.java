package com.santanu.Test.generate.mapper;


import com.santanu.Test.generate.dto.PaperDTO;
import com.santanu.Test.generate.model.Paper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TestPaperMapper {
    @Mapping(source = "distributions", target = "distributions")
    PaperDTO toDTO(Paper paper);

    List<PaperDTO> toDtoList(List<Paper> testPapers);

    Paper toEntity(PaperDTO paperDTO);

}
