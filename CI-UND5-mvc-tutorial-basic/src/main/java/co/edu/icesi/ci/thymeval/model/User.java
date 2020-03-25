package co.edu.icesi.ci.thymeval.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	
	@Size(min=2)
	@NotEmpty(message="the date cannot be empty")
	private String name;
	
	@NotEmpty(message="the email cannot be empty")
	@Email(message="it must be a valid email")
	private String email;
	
	@NotNull(message="the type cannot be empty")
	private UserType type;
	
	@NotNull(message="the birthDate cannot be empty")
	@Past(message="you must put a date less than the current one")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	
	@NotNull(message="the gender cannot be empty")
	private UserGender gender;
	
}
