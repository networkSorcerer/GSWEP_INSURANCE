package com.gswep.insurance.field_answers.repository;

import com.gswep.insurance.field_answers.entity.FieldAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldsAnswersRepository extends JpaRepository<FieldAnswers, Integer> {
}
