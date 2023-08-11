package kaif.nurbackend.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import kaif.nurbackend.utilities.TrimStringDeserializer;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Long id;

	@JsonDeserialize(using = TrimStringDeserializer.class)
	private String fullname;

	@JsonDeserialize(using = TrimStringDeserializer.class)
	private String username;

	@JsonDeserialize(using = TrimStringDeserializer.class)
	private String email;

	private Long mobile;

	@JsonDeserialize(using = TrimStringDeserializer.class)
	private String role;

	private Boolean isAuthorized;

	@JsonDeserialize(using = TrimStringDeserializer.class)
	private String gender;
}
