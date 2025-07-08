package com.gswep.insurance.contract.service;

import com.gswep.insurance.contract.entity.ContractEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CsvReader {

    @Autowired
    private ResourceLoader resourceLoader;

    public List<ContractEntity> insuranceCsv(String tech) throws Exception {
        List<ContractEntity> insuranceList = new ArrayList<>();

        Resource resource = resourceLoader.getResource(tech);

        try (InputStream inputStream = resource.getInputStream();
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)){
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(reader);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

            for(CSVRecord record : records){
                ContractEntity contractEntity = new ContractEntity();
                contractEntity.setProduct_code(record.get("상품코드"));
                contractEntity.setProduct_name(record.get("상품명"));
                contractEntity.setContract_no(record.get("계약번호"));
                contractEntity.setCo(record.get("성명/법인명"));
                contractEntity.setStart_date(parseDate(record.get("개시일"), dateFormat));
                contractEntity.setEnd_date(parseDate(record.get("만기일"), dateFormat));
                contractEntity.setContract_date(parseDate(record.get("계약일"), dateFormat));
                contractEntity.setCo_insurance(record.get("간사사"));
                contractEntity.setTotal_insurance_pay(parseInt(record.get("전체보험료").replace(",","").trim()));
                contractEntity.setOur_insurance_premium(parseInt(record.get("당사보험료").replace(",","").trim()));
                contractEntity.setInsurance_state(record.get("계약상태"));
                contractEntity.setUser_id(parseInt(record.get("계약담당")));
                contractEntity.setUser_name(record.get("계약담당명"));
                contractEntity.setMember_id(parseInt(record.get("사용인")));
                contractEntity.setMember_name(record.get("사용인명"));
                contractEntity.setStore(record.get("계약점포"));
                contractEntity.setPre_contract_no(parseInt(record.get("전계약번호")));
                contractEntity.setShared(record.get("실적배분여부"));
                contractEntity.setShared(record.get("비고"));
                insuranceList.add(contractEntity);
            }
        }
        return insuranceList;
    }
    // 문자열을 Date로 변환하는 메서드
    private Date parseDate(String dateString, SimpleDateFormat dateFormat) {
        try {
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            return null; // 날짜 형식 오류 시 null 반환
        }
    }

    // 문자열을 정수로 변환하는 메서드
    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0; // 숫자 형식 오류 시 0 반환
        }
    }
}

