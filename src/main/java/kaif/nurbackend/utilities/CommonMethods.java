package kaif.nurbackend.utilities;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kaif.nurbackend.dto.BillDTO;
import kaif.nurbackend.repo.BillRepo;

@Service
public class CommonMethods {

    @Autowired
    private BillRepo billRepo;

    // fetch bill With cart
    public Map<String, Object> fetchBillWithCart(Long id) {
        Map<String, Object> map = new HashMap<>();
        var bill = billRepo.findById(id).get();
        var invoice = new BillDTO(bill.getId(), bill.getBillno(), bill.getDiscountKey(),
                bill.getDiscount(), bill.getAmount(), bill.getAmountPaid(), bill.getGrandTotal(),
                bill.getPendingAmount(),
                bill.getPaymentMode(), bill.getStatus(), bill.getTime(), bill.getDate());
        map.put("Invoice", invoice);
        map.put("billcart", bill.getBillcart());
        return map;
    }
}
