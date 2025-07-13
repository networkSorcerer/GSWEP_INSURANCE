package com.gswep.insurance.from_fields.repository;

import com.gswep.insurance.from_fields.entity.Form_Fields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormFieldsRepository extends JpaRepository<Form_Fields, Integer> {
    List<Form_Fields> findByForm_formId(Integer formId);
}
