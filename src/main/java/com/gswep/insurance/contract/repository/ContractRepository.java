package com.gswep.insurance.contract.repository;

import com.gswep.insurance.contract.entity.ContractEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, Long> {
    @Query("SELECT c FROM ContractEntity c WHERE c.contract_no LIKE %:no%")
    List<ContractEntity> findByContract_no(@Param("no") String contractNo, Pageable pageable);
    @Query("SELECT c FROM ContractEntity c WHERE c.member_name LIKE %:name%")
    List<ContractEntity> findByMemberName(@Param("name") String memberName, Pageable pageable);

}
