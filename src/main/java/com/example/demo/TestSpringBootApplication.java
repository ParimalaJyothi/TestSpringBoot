package com.example.demo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@SpringBootApplication
public class TestSpringBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestSpringBootApplication.class, args);
	}
}

@RestController
@RequestMapping("/test")
class TestController {

	@GetMapping("/hello")
	public String hello() {
		return "Hello World " + new Date();
	}
}

@RestController
@RequestMapping("/address")
class AddressCocntroller {
	@Autowired
	private AddressRepository addressRepository;
	
	@GetMapping("")
	public List<Address> getAddress() {
		ModelMapper mapper = new ModelMapper();
		
		List<Address> list = mapper.map(addressRepository.findAll(), new TypeToken<List<Address>>() {}.getType());
		return list;
	}
	
	@PostMapping("")
	public void saveAddress(@RequestBody Address address) {
		AddressEntity addressEntity = new ModelMapper().map(address, AddressEntity.class);
		addressRepository.save(addressEntity);
	}
}

@Entity
@Table(name = "address")
@Data
class AddressEntity {
	@Id
	@GeneratedValue
	private long id;
	
	private String city;
}

@Data
class Address {
	private long id;
	private String city;
}

interface AddressRepository extends CrudRepository<AddressEntity, Long>{
	//@Query("select a from AddressEntity where a.cityt = :city")
	Optional<AddressEntity> findByCityAndId(String city, long id);
}

