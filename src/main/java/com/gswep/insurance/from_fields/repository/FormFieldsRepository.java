package com.gswep.insurance.from_fields.repository;

import com.gswep.insurance.from_fields.entity.FormFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormFieldsRepository extends JpaRepository<FormFields, Integer> {
    List<FormFields> findByForm_formId(Integer formId);

    @Query("SELECT MAX(f.fieldId) FROM FormFields f WHERE f.form.formId = :formId")
    Integer findByFormFormId(@Param("formId") Long formId);
}
