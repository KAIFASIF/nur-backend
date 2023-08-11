package kaif.nurbackend.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long billno;
    private String discountKey;
    private Long discount;
    private Long amount;
    private Long amountPaid;
    private Long grandTotal;
    private Long pendingAmount;
    private String paymentMode;
    private String status;
    private String time;

    @Temporal(TemporalType.DATE)
	private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @ToString.Exclude
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Billcart> billcart = new ArrayList<>();


    Bill(Long amount, User user) {
        this.amount = amount;
        this.user = user;
    }

    public void addChild(Billcart child) {
        billcart.add(child);
        child.setBill(this);
    }

    public void removeChild(Billcart child) {
        billcart.remove(child);
        child.setBill(null);
    }

}
