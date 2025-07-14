package com.gswep.insurance.form.repository;

import com.gswep.insurance.form.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    List<Form> findByContractEntity_ProductCode(String productCode);

    @Query("SELECT MAX(f.formId) FROM Form f WHERE f.contractEntity.contractId = :contractId")
    Integer findLatestId(@Param("contractId") Long contractId);

    @Query("SELECT f.formId FROM Form f WHERE f.contractEntity.contractId = :contractId")
    Integer findFormIdByContractId(@Param("contractId") Long contractId);
}
