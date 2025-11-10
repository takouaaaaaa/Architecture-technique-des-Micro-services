package com.example.Billing_service.Controller;
import com.example.Billing_service.entities.Bill;
import com.example.Billing_service.Repository.BillRepository;
import com.example.Billing_service.service.CustomerService;
import com.example.Billing_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillRestController {

    @Autowired
    private BillRepository billRepository;


    @Autowired
    private CustomerService customerService;

    @Autowired
    private InventoryService inventoryService;


    @GetMapping("/fullBill/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id) {

        Bill bill = billRepository.findById(id).get();

        bill.setCustomer(customerService.findCustomerById(bill.getCustomerID()));


        bill.getProductItems().forEach(pi -> {
            pi.setProduct(inventoryService.findProductById(pi.getProductID()));
        });


        return bill;
    }
}