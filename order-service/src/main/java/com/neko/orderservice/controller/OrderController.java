package com.neko.orderservice.controller;

import com.neko.orderservice.model.request.OrderRequest;
import com.neko.orderservice.model.response.OrderResponse;
import com.neko.orderservice.model.response.WebResponse;
import com.neko.orderservice.service.InvoiceService;
import com.neko.orderservice.service.OrderDetailService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/order/")
public class OrderController {

    private final static Logger log = LoggerFactory.getLogger(OrderController.class);

    private OrderDetailService orderDetailService;

    private InvoiceService invoiceService;

    @Autowired
    public OrderController(OrderDetailService orderDetailService, InvoiceService invoiceService) {
        this.orderDetailService = orderDetailService;
        this.invoiceService = invoiceService;
    }


    @PostMapping(value = "/ticket")
    @ResponseBody
    public ResponseEntity<WebResponse<OrderResponse>> postRequestOrder(@Valid @RequestBody OrderRequest orderRequest) {
        log.info("#calling controller postRequestRegister");
        try {
            OrderResponse orderResponse = orderDetailService.createOrderDetail(orderRequest);
            log.info("#successfully place an order with order id {}, with user id {} ", orderResponse.getOrderId(), orderRequest.getUserId());
            WebResponse webResponse = new WebResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    orderResponse
            );
            return new ResponseEntity<>(webResponse, HttpStatus.OK);
        }catch (RuntimeException exception) {
            log.error("failed to place order for user id {}", orderRequest.getUserId());
            WebResponse webResponse = new WebResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    null
            );
            return new ResponseEntity<>(webResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/pdf",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public void reportInvoice(HttpServletResponse response) {
        log.info("#calling controller reportInvoice");
        try {
            ByteArrayInputStream invoice = new ByteArrayInputStream(invoiceService.getInvoice());
            response.addHeader("Content-Disposition", "attachment; filename=" + UUID.randomUUID().toString() +".pdf");
            response.setContentType("application/octet-stream");

            IOUtils.copy(invoice, response.getOutputStream());
            response.flushBuffer();
            log.info("success create file pdf for report");
        }catch (Exception exception) {
            log.error("failed to generate pdf file report, error {} ", exception.getMessage());
        }
    }

}
