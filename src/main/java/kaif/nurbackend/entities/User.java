package kaif.nurbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.OneToMany;
import kaif.nurbackend.utilities.TrimStringDeserializer;
import jakarta.persistence.CascadeType;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonDeserialize(using = TrimStringDeserializer.class)
	private String fullname;

	@JsonDeserialize(using = TrimStringDeserializer.class)
	private String username;

	@JsonDeserialize(using = TrimStringDeserializer.class)
	private String email;

	private Long mobile;

	@JsonDeserialize(using = TrimStringDeserializer.class)
	private String password;

	@JsonDeserialize(using = TrimStringDeserializer.class)
	private String role;

	private Boolean isAuthorized;

	@JsonDeserialize(using = TrimStringDeserializer.class)
	private String gender;

	@JsonDeserialize(using = TrimStringDeserializer.class)
	private String additionalFields;

	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	@JsonIgnore
	private List<Size> size = new ArrayList<>();

	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	@JsonIgnore
	private List<Category> category = new ArrayList<>();

	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	@JsonIgnore
	private List<Stock> stock = new ArrayList<>();

	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	@JsonIgnore
	private List<Bill> bill = new ArrayList<>();

}
