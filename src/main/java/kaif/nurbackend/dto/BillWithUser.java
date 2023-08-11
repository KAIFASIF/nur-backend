package kaif.nurbackend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillWithUser {
    private String user;
    private BillDTO billDTO;
}
