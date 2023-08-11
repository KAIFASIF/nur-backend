package kaif.nurbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import kaif.nurbackend.entities.LastItemcode;
import kaif.nurbackend.entities.Stock;
import kaif.nurbackend.exceptions.FieldAlreadyExist;
import kaif.nurbackend.repo.LastItemcodeRepo;
import kaif.nurbackend.repo.StockRepo;
import kaif.nurbackend.repo.UserRepo;

@Service
public class StockService {

    @Autowired
    private StockRepo stockRepo;

    @Autowired
    private LastItemcodeRepo lastItemcodeRepo;

    @Autowired
    private UserRepo userRepo;

    @Transactional
    public Stock saveStock(Long userId, Stock stock) {
        var user = userRepo.findById(userId).get();
        var itemCodeList = stockRepo.findAll();
        var itemCodeExists = false;
        if (stock.getId() != null) {
            var filteredList = itemCodeList.stream().filter(ele -> !ele.getId().equals(stock.getId()))
                    .collect(Collectors.toList());
            itemCodeExists = filteredList.stream()
                    .anyMatch(ele -> ele.getItemcode().equalsIgnoreCase(stock.getItemcode()));
            stock.setId(stock.getId());
        } else {
            itemCodeExists = itemCodeList.stream()
                    .anyMatch(ele -> ele.getItemcode().equalsIgnoreCase(stock.getItemcode()));
            LastItemcode lastItemcode = new LastItemcode();
            lastItemcode.setId(1L);
            lastItemcode.setItemcode(stock.getItemcode());
            lastItemcodeRepo.save(lastItemcode);
        }

        if (itemCodeExists) {
            throw new FieldAlreadyExist("Itemcode already exists");
        }

        stock.setUser(user);
        return stockRepo.save(stock);
    }

    public Map<String, Object> fetchStocks(Pageable pageable) {
        Map<String, Object> map = new HashMap<>();
        Long count = stockRepo.count();
        var stocks = stockRepo.findAll(pageable).getContent();
        map.put("count", count);
        map.put("stocks", stocks);
        return map;
    }

    public String deleteStock(Long id) {
        try {
            stockRepo.deleteById(id);
            return "Stock Deleted";
        } catch (Exception e) {
            return "Stock not deleted";
        }
    }

    public String deleteAllStocks() {
        try {
            stockRepo.deleteAll();
            return "All stocks Deleted";
        } catch (Exception e) {
            return "Stocks not deleted";
        }
    }

}
