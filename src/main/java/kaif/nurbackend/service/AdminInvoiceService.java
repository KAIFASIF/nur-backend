package kaif.nurbackend.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import kaif.nurbackend.dto.BillDTO;
import kaif.nurbackend.dto.BillWithUser;
import kaif.nurbackend.exceptions.ResourceNotFound;
import kaif.nurbackend.repo.BillRepo;
import kaif.nurbackend.repo.UserRepo;

@Service
public class AdminInvoiceService {

        @Autowired
        private UserRepo userRepo;

        @Autowired
        private BillRepo billRepo;


        // fetch Invoices by status or status and date
        public Map<String, Object> fetchInvoicesByStatusAndDate(String status, Date startDate, Date endDate,
                        Pageable pageable) {
                Map<String, Object> map = new HashMap<>();
                if (status.equalsIgnoreCase("paid") || status.equalsIgnoreCase("pending")) {
                        var count = billRepo.countByStatusAndDateBetween(status, startDate, endDate);
                        var bilList = billRepo.findByStatusAndDateBetween(status, startDate, endDate, pageable);
                        var res = bilList.stream().map(ele -> new BillWithUser(ele.getUser().getFullname(),
                                        new BillDTO(ele.getId(), ele.getBillno(), ele.getDiscountKey(),
                                                        ele.getDiscount(), ele.getAmount(), ele.getAmountPaid(),
                                                        ele.getGrandTotal(), ele.getPendingAmount(),
                                                        ele.getPaymentMode(), ele.getStatus(), ele.getTime(),
                                                        ele.getDate())));

                        map.put("count", count);
                        map.put("invoices", res);
                        return map;
                }
                var user = userRepo.findByFullnameIgnoreCase(status);
                if (user == null) {
                        throw new ResourceNotFound("Invalid search");
                }
                Long count = billRepo.countByUserIdAndDateBetween(user.getId(), startDate, endDate);
                var bilList = billRepo.findByUserIdAndDateBetween(user.getId(), startDate, endDate, pageable);
                var res = bilList.stream().map(ele -> new BillWithUser(ele.getUser().getFullname(),
                                new BillDTO(ele.getId(), ele.getBillno(), ele.getDiscountKey(),
                                                ele.getDiscount(), ele.getAmount(), ele.getAmountPaid(),
                                                ele.getGrandTotal(), ele.getPendingAmount(),
                                                ele.getPaymentMode(), ele.getStatus(),
                                                ele.getTime(), ele.getDate())));

                map.put("count", count);
                map.put("invoices", res);
                return map;
        }

        // fetch Invoices by date only
        public Map<String, Object> fetchInvoicesByDate(Date startDate, Date endDate, Pageable pageable) {
                Map<String, Object> map = new HashMap<>();
                var count = billRepo.countByDateBetween(startDate, endDate);
                var bilList = billRepo.findByDateBetween(startDate, endDate, pageable);

                var res = bilList.stream().map(ele -> new BillWithUser(ele.getUser().getFullname(),
                                new BillDTO(ele.getId(), ele.getBillno(), ele.getDiscountKey(), ele.getDiscount(),
                                                ele.getAmount(), ele.getAmountPaid(), ele.getGrandTotal(),
                                                ele.getPendingAmount(), ele.getPaymentMode(), ele.getStatus(),
                                                ele.getTime(), ele.getDate())));

                map.put("count", count);
                map.put("invoices", res);
                return map;
        }

        // fetch Invoices by search status only or user only
        public Map<String, Object> fetchInvoicesByStatus(String status, Pageable pageable) {
                Map<String, Object> map = new HashMap<>();
                if (status.equalsIgnoreCase("paid") || status.equalsIgnoreCase("pending")) {
                        Long count = billRepo.countByStatus(status);
                        var bilList = billRepo.findByStatus(status, pageable);
                        var res = bilList.stream().map(ele -> new BillWithUser(ele.getUser().getFullname(),
                                        new BillDTO(ele.getId(), ele.getBillno(), ele.getDiscountKey(),
                                                        ele.getDiscount(), ele.getAmount(), ele.getAmountPaid(),
                                                        ele.getGrandTotal(), ele.getPendingAmount(),
                                                        ele.getPaymentMode(), ele.getStatus(),
                                                        ele.getTime(), ele.getDate())));

                        map.put("count", count);
                        map.put("invoices", res);
                        return map;
                }

                var user = userRepo.findByFullnameIgnoreCase(status);
                if (user == null) {
                        throw new ResourceNotFound("Invalid search");
                }
                Long count = billRepo.countByUserId(user.getId());
                var bilList = billRepo.findByUserId(user.getId(), pageable);
                var res = bilList.stream().map(ele -> new BillWithUser(ele.getUser().getFullname(),
                                new BillDTO(ele.getId(), ele.getBillno(), ele.getDiscountKey(),
                                                ele.getDiscount(), ele.getAmount(), ele.getAmountPaid(),
                                                ele.getGrandTotal(), ele.getPendingAmount(),
                                                ele.getPaymentMode(), ele.getStatus(),
                                                ele.getTime(), ele.getDate())));

                map.put("count", count);
                map.put("invoices", res);
                return map;
        }

}
