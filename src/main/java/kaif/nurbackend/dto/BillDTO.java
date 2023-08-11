package kaif.nurbackend.dto;

import java.util.Date;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {
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
    private Date date;

}
