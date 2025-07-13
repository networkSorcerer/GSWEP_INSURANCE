package com.gswep.insurance.field_answers.repository;

import com.gswep.insurance.field_answers.entity.Field_Answers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldsAnswersRepository extends JpaRepository<Field_Answers, Integer> {
}
