package kaif.nurbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kaif.nurbackend.entities.Stock;
import kaif.nurbackend.repo.StockRepo;

@Service
public class UserStockService {

    @Autowired
    private StockRepo stockRepo;

    public Stock getStockByItemcode(String itemcode) {
        return this.stockRepo.findByItemcode(itemcode.trim().toUpperCase());
    }

}
