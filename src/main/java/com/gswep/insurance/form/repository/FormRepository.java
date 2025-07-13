package com.gswep.insurance.form.repository;

import com.gswep.insurance.form.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    List<Form> findByContractEntity_ProductCode(String productCode);
}
