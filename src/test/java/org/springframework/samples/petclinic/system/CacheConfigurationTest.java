package org.springframework.samples.petclinic.system;

import org.apache.geode.internal.cache.entries.VersionedThinDiskLRURegionEntry;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.samples.petclinic.vet.Vets;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.transaction.BeforeTransaction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class CacheConfigurationTest {

	@Autowired
	protected VetRepository vets;

	@BeforeEach
	void generateCache(){
		this.vets.findAll().forEach(vet -> System.out.println(vet.getLastName()));
	}

	@Test
	//@Sql(statements = "update vets set LAST_NAME='Hoshino' where ID=1")
	void vetRegionShouldbeCache(){
		this.vets.findAll().forEach(vet ->
			assertNotEquals(vet.getLastName().contains("Hoshino"));
	}


}
