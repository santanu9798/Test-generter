package com.santanu.Test.generate.repository;

import com.santanu.Test.generate.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionBankRepository extends JpaRepository<Question, Long> {
    @Query(value = """
        SELECT q.id, q.text, q.type, q.topic, q.difficulty, q.options, q.CORRECT_ANSWER, q.marks 
        FROM Question q
        WHERE q.type = :type
        ORDER BY RAND()
        LIMIT :count
        """, nativeQuery = true)
    List<Object[]> findRandomQuestionsByType(@Param("type") String type, @Param("count") int count);
}
