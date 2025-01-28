package com.santanu.Test.generate.mapper;


import com.santanu.Test.generate.dto.QuestionDTO;
import com.santanu.Test.generate.model.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionBankMapper {

    @Mapping(target = "options", expression = "java(mapOptionsToList(question.getOptions()))")
    QuestionDTO toDTO(Question question);

    @Mapping(target = "options", expression = "java(mapOptionsToString(questionDTO.getOptions()))")
    Question toEntity(QuestionDTO questionDTO);

    default List<String> mapOptionsToList(String options) {
        if (options == null || options.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(options.split(","));
    }

    default String mapOptionsToString(List<String> options) {
        if (options == null || options.isEmpty()) {
            return null;
        }
        return String.join(",", options);
    }

}
