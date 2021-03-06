package com.swim.controller;

import com.swim.model.Asn;
import com.swim.service.AsnService;
import com.swim.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AsnController {

    @Autowired
    AsnService asnService;

    @Autowired
    ProductService productService;

    @PostMapping("/api/receive/asn")
    public void receiveAsn(@RequestBody Asn asn) {
        asn.setStatus("in-transit");
        asn.setDockDoor("Not assigned");
        asn.setExpectedArrivalDate(new StringBuilder(asn.getExpectedArrivalDate()).reverse().toString());
        asnService.insertAsn(asn);
        asn.getSerials().stream()
                        .forEach(product -> {
                            product.getSerial();
                            product.setAsn(asn.getAsn());
                            product.setDelivered(false);
                            product.setReceived(false);
                            productService.insertProduct(product);
                        });
    }

    @GetMapping("/api/asns")
    public List<Asn> getAllAsns() {
        return asnService.getAllAsns();
    }

}
