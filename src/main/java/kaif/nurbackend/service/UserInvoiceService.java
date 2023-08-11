package kaif.nurbackend.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kaif.nurbackend.dto.BillDTO;
import kaif.nurbackend.repo.BillRepo;
import kaif.nurbackend.repo.BillcartRepo;
import kaif.nurbackend.repo.UserRepo;

@Service
public class UserInvoiceService {

    @Autowired
    private UserRepo userRepo;
   
    @Autowired
    private BillRepo billRepo;

    @Autowired
    private BillcartRepo billcartRepo;


    // fetch User Invoices by date only
    public Map<String, Object> fetchUserInvoiceByDate(Long userId, Date startDate, Date endDate, Pageable pageable) {
        Map<String, Object> map = new HashMap<>();
        var count = billRepo.countByUserIdAndDateBetween(userId, startDate, endDate);
        var userBillList = billRepo.findByUserIdAndDateBetween(userId, startDate, endDate, pageable);
        var res = userBillList.map(ele -> new BillDTO(ele.getId(), ele.getBillno(), ele.getDiscountKey(),
                ele.getDiscount(), ele.getAmount(), ele.getAmountPaid(), ele.getGrandTotal(), ele.getPendingAmount(),
                ele.getPaymentMode(), ele.getStatus(), ele.getTime(), ele.getDate()));
        map.put("count", count);
        map.put("invoices", res.toList());
        return map;
    }

    // fetch User Invoices by status only
    public Map<String, Object> fetchUserInvoiceByStatus(Long userId, String search, Pageable pageable) {
        Map<String, Object> map = new HashMap<>();
        var count = billRepo.countByUserIdAndStatusStartingWith(userId, search);
        var userBillList = billRepo.findByUserIdAndStatusStartingWith(userId, search, pageable);
        var res = userBillList.map(ele -> new BillDTO(ele.getId(), ele.getBillno(), ele.getDiscountKey(),
                ele.getDiscount(), ele.getAmount(), ele.getAmountPaid(), ele.getGrandTotal(), ele.getPendingAmount(),
                ele.getPaymentMode(), ele.getStatus(), ele.getTime(), ele.getDate()));
        map.put("count", count);
        map.put("invoices", res.toList());
        return map;
    }

    // fetch User Invoices by date and status
    public Map<String, Object> fetchUserInvoiceByStatusAndDate(Long userId, String search, Date startDate, Date endDate,
            Pageable pageable) {
        Map<String, Object> map = new HashMap<>();
        var count = billRepo.countByUserIdAndStatusStartingWithAndDateBetween(userId, search, startDate, endDate);
        var userBillList = billRepo.findByUserIdAndStatusStartingWithAndDateBetween(userId, search, startDate, endDate,
                pageable);
        var res = userBillList.map(ele -> new BillDTO(ele.getId(), ele.getBillno(), ele.getDiscountKey(),
                ele.getDiscount(), ele.getAmount(), ele.getAmountPaid(), ele.getGrandTotal(), ele.getPendingAmount(),
                ele.getPaymentMode(), ele.getStatus(), ele.getTime(), ele.getDate()));
        map.put("count", count);
        map.put("invoices", res.toList());
        return map;
    }


    // fetch user bills by UserID no dates
    // public Map<String, Object> fetchAllBillsByUserId(Long userId, Integer page,
    // Integer size) {
    // long count = this.billRepository.countByUserId(userId);
    // Pageable pageable = PageRequest.of(page, size);
    // Map<String, Object> map = new HashMap<>();
    // Page<Bill> userBills = this.billRepository.findByUserId(userId, pageable);

    // Page<Object> fetchedBills = userBills.map(ele -> new BillDTO(
    // ele.getId(),
    // ele.getBillno(),
    // ele.getDiscountKey(),
    // ele.getDiscountValue(),
    // ele.getDiscountAmount(),
    // ele.getStatus(),
    // ele.getGrandTotal(),
    // ele.getTotalAmount(),
    // ele.getCreatedAt(),
    // ele.getTime(),
    // ele.getUser()));
    // map.put("count", count);
    // map.put("bills", fetchedBills.toList());
    // return map;
    // }

    // fetch user bills by UserID, status and dates
    // public Map<String, Object> fetchAllBillsByUserIdAndStatusWithdDates(Long
    // userId,String searchVal, LocalDate fromDate, LocalDate toDate, Pageable
    // pageable) {
    // long count =
    // this.billRepository.countByUserIdAndStatusStartingWithAndCreatedAtBetween(userId,
    // searchVal, fromDate, toDate);

    // Map<String, Object> map = new HashMap<>();
    // Page<Bill> userBills =
    // this.billRepository.findByUserIdAndStatusStartingWithAndCreatedAtBetween(userId,
    // searchVal,fromDate, toDate, pageable);

    // Page<Object> fetchedBills = userBills.map(ele -> new BillDTO(
    // ele.getId(),
    // ele.getBillno(),
    // ele.getDiscountKey(),
    // ele.getDiscountValue(),
    // ele.getDiscountAmount(),
    // ele.getStatus(),
    // ele.getGrandTotal(),
    // ele.getTotalAmount(),
    // ele.getCreatedAt(),
    // ele.getTime(),
    // ele.getUser()));
    // map.put("count", count);
    // map.put("bills", fetchedBills.toList());
    // return map;
    // }

    // // fetch billcart
    // public Map<String, Object> fetchBillcart(Long billId) {
    // Optional<Bill> bill = this.billRepository.findById(billId);
    // BillDTO fetchedBill = new BillDTO(
    // bill.get().getId(),
    // bill.get().getBillno(),
    // bill.get().getDiscountKey(),
    // bill.get().getDiscountValue(),
    // bill.get().getDiscountAmount(),
    // bill.get().getStatus(),
    // bill.get().getGrandTotal(),
    // bill.get().getTotalAmount(),
    // bill.get().getCreatedAt(),
    // bill.get().getTime(),
    // bill.get().getUser());
    // List<Billcart> billcart = bill.get().getBillcart();
    // Map<String, Object> map = new HashMap<>();
    // map.put("bill", fetchedBill);
    // map.put("billcart", billcart);
    // return map;
    // }

    // public String deleteBill(Long billId) {
    // this.billRepository.deleteById(billId);
    // return "deleted";
    // }

    // public Map<String, Object> fetchUserBillsByStatus(Long userId, String status,
    // Pageable pageable) {
    // Long count = this.billRepository.countByUserIdAndStatusStartingWith(userId,
    // status);

    // List<Bill> fetchedBills =
    // this.billRepository.findByUserIdAndStatusStartingWith(userId, status,
    // pageable).toList();
    // System.out.println("*****************************************************************************************************");
    // System.out.println(fetchedBills);
    // System.out.println("*****************************************************************************************************");
    // Map<String, Object> map = new HashMap<>();
    // map.put("count", count);
    // map.put("bills", fetchedBills);

    // return map;
    // }

    // public Map<String, Object> fetchUserBillsByDateRange(Long userId, LocalDate
    // startDate, LocalDate endDate, Pageable pageable) {
    // // Long count =
    // this.billRepository.countByUserIdAndStatusStartingWith(userId, status);

    // List<Bill> fetchedBills =
    // this.billRepository.findByCreatedDateBetween(startDate, endDate).toList();
    // System.out.println("*****************************************************************************************************");
    // System.out.println(fetchedBills);
    // System.out.println("*****************************************************************************************************");
    // Map<String, Object> map = new HashMap<>();
    // map.put("count", 20);
    // // map.put("bills", fetchedBills);

    // return map;
    // }

    // admin service methods

    // fetch all Bills with no dates admin
    // public Map<String, Object> fetchAllBills(Integer page, Integer size) {
    // Long count = this.billRepository.count();
    // Pageable pageNumSize = PageRequest.of(page, size);
    // List<Bill> fetchedBills = this.billRepository.findAll(pageNumSize).toList();
    // Map<String, Object> map = new HashMap<>();
    // map.put("count", count);
    // map.put("bills", fetchedBills);
    // return map;
    // }

    // // fetch all Bills with dates admin
    // public Map<String, Object> fetchAllBillsCreatedBetween(LocalDate fromDate,
    // LocalDate toDate, Pageable pageable) {
    // Long count = this.billRepository.countByCreatedAtBetween(fromDate, toDate);
    // Page<Bill> fetchedBills =
    // this.billRepository.findByCreatedAtBetween(fromDate, toDate, pageable);
    // List<BillDTO> bills = new ArrayList<>();
    // for (Bill ele : fetchedBills) {
    // BillDTO filteredBills = new BillDTO(ele.getId(),
    // ele.getBillno(),
    // ele.getDiscountKey(),
    // ele.getDiscountValue(),
    // ele.getDiscountAmount(),
    // ele.getStatus(),
    // ele.getGrandTotal(),
    // ele.getTotalAmount(),
    // ele.getCreatedAt(),
    // ele.getTime(),
    // ele.getUser());
    // bills.add(filteredBills);
    // }
    // Map<String, Object> map = new HashMap<>();
    // map.put("count", count);
    // map.put("bills", bills);

    // return map;
    // }

    // // fetch all bills by status and no dates
    // public Map<String, Object> fetchBillsByStatus(String status, Pageable
    // pageable) {
    // Long count = this.billRepository.countByStatusStartingWith(status);
    // List<Bill> fetchedBills =
    // this.billRepository.findByStatusStartingWith(status, pageable).toList();
    // Map<String, Object> map = new HashMap<>();
    // map.put("count", count);
    // map.put("bills", fetchedBills);

    // return map;
    // }

    // // find By status with dates in paginated Manner
    // public Map<String, Object> fetchAllBillsWithStatusAndDates(String
    // searchVal,LocalDate fromDate, LocalDate toDate, Pageable pageable) {
    // Long count =
    // this.billRepository.countByStatusStartingWithAndCreatedAtBetween(searchVal,
    // fromDate, toDate);
    // List<Bill> fetchedBills =
    // this.billRepository.findByStatusStartingWithAndCreatedAtBetween(searchVal,
    // fromDate, toDate, pageable).toList();
    // Map<String, Object> map = new HashMap<>();
    // map.put("count", count);
    // map.put("bills", fetchedBills);

    // return map;
    // }

    // // find By userId with no dates in paginated Manner
    // public Map<String, Object> fetchAllBillsByUserId(Long userId, Pageable
    // pageable) {
    // Long count = this.billRepository.countByUserId(userId);
    // List<Bill> fetchedBills = this.billRepository.findByUserId(userId,
    // pageable).toList();
    // Map<String, Object> map = new HashMap<>();
    // map.put("count", count);
    // map.put("bills", fetchedBills);

    // return map;
    // }

    // // find By userId with dates in paginated Manner
    // public Map<String, Object> fetchAllBillsByUserIdAndDates(Long
    // userId,LocalDate fromDate, LocalDate toDate, Pageable pageable) {
    // Long count = this.billRepository.countByUserIdAndCreatedAtBetween(userId,
    // fromDate, toDate);
    // List<Bill> fetchedBills =
    // this.billRepository.findByUserIdAndCreatedAtBetween(userId, fromDate, toDate,
    // pageable).toList();
    // Map<String, Object> map = new HashMap<>();
    // map.put("count", count);
    // map.put("bills", fetchedBills);

    // return map;
    // }

}
