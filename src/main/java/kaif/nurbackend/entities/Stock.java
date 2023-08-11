package kaif.nurbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import kaif.nurbackend.utilities.TrimStringDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonDeserialize(using = TrimStringDeserializer.class)
	private String category;

	@JsonDeserialize(using = TrimStringDeserializer.class)
	private String itemcode;

	@JsonDeserialize(using = TrimStringDeserializer.class)
	private String size;

	@JsonDeserialize(using = TrimStringDeserializer.class)
	private String imageUrl;

	private Long purchasedPrice;
	private Long purchasedQuantity;
	private Long availableQuantity;
	private Long mrp;
	private Long soldPrice;

	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

}
