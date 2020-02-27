package com.dt.deliveryorder.controller;

import com.dt.deliveryorder.dto.BOL;
import com.dt.deliveryorder.dto.Invoice;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestController {
    private Integer restCount=0;
    @PostMapping(value="/getData")
    public String getData(HttpServletRequest httpServletRequest) throws  Exception{
        String req= IOUtils.toString(httpServletRequest.getReader());
        System.out.println(req);
        ObjectMapper mapper = new ObjectMapper();
        try {
        List<BOL> mappingList = mapper.readValue(req,   new TypeReference<List<BOL>>(){});
            System.out.println(mappingList);
            System.out.println("DONE");

        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return "hello";
    }
    @PostMapping(value="/errorURL")
    @ResponseStatus(HttpStatus.OK)
    public void processError(HttpServletRequest httpServletRequest) throws  Exception{
        String req= IOUtils.toString(httpServletRequest.getReader());
        System.out.println(req);

    }
    @PostMapping(value="/updateInvoice")
    public Invoice updateInvoice(HttpServletRequest httpServletRequest) throws  Exception{
        String req= IOUtils.toString(httpServletRequest.getReader());
        System.out.println(req);
        Invoice invoice = new Invoice();
        invoice.setInvoiceValue("hello");
//        if (this.restCount < 5){
//            this.restCount = this.restCount +1;
//            throw new RuntimeException("PDF ERROR");
//        }

        return invoice;
    }
}
