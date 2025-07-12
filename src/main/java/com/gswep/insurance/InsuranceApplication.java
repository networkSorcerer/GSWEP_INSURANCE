package com.gswep.insurance;

import com.gswep.insurance.contract.entity.ContractEntity;
import com.gswep.insurance.contract.repository.ContractRepository;
import com.gswep.insurance.contract.service.ContractService;
import com.gswep.insurance.contract.service.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class  InsuranceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceApplication.class, args);
	}

	@Autowired
	private CsvReader csvReader;
	@Autowired
	private ContractService contractService;
	@Autowired
	private ContractRepository contractRepository;

	@Bean
	public CommandLineRunner run() {
		return args -> {
			String filePath = "classpath:/static/csv/insurances.csv";

			List<ContractEntity> insuranceData = csvReader.insuranceCsv(filePath);

			for (ContractEntity contractEntity : insuranceData) {
				Optional<ContractEntity> existingData = Optional.empty();
				if (contractEntity.getContract_id() != null) {
					existingData = contractRepository.findById(contractEntity.getContract_id());
				}
				if (!existingData.isPresent()) {
					contractRepository.save(contractEntity);
				} else {
					System.out.println("contractEntity news found, skipping");

				}
			}
		};
	}
}
