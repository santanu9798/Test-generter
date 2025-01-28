package com.santanu.Test.generate.repository;

import com.santanu.Test.generate.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionBankRepository extends JpaRepository<Question, Long> {
}
