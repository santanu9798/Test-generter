package com.santanu.Test.generate.repository;

import com.santanu.Test.generate.model.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestPaperRepository extends JpaRepository<Paper, Long> {
}
