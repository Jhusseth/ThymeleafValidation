package co.edu.icesi.ci.thymeval.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.GroupSequence;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	
	@Size(min=3,groups=ValidationGroup1.class)
	@NotEmpty(message="the User name cannot be empty",groups=ValidationGroup1.class)
	private String userName;
	
	@Size(min=8,groups=ValidationGroup1.class)
	@NotEmpty(message="the date cannot be empty",groups=ValidationGroup1.class)
	private String password;
	
	@NotNull(message="the birthDate cannot be empty",groups=ValidationGroup1.class)
	@Past(message="you must put a date less than the current one",groups=ValidationGroup1.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	
	@Size(min=2,groups=ValidationGroup2.class)
	@NotEmpty(message="the date cannot be empty",groups=ValidationGroup2.class)
	private String name;
	
	@NotEmpty(message="the email cannot be empty",groups=ValidationGroup2.class)
	@Email(message="it must be a valid email",groups=ValidationGroup2.class)
	private String email;
	
	@NotNull(message="the type cannot be empty",groups=ValidationGroup2.class)
	private UserType type;
	
	@NotNull(message="the gender cannot be empty",groups=ValidationGroup2.class)
	private UserGender gender;
	
}
