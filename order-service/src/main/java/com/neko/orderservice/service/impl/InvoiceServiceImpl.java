package com.neko.orderservice.service.impl;

import com.neko.orderservice.model.entity.Invoice;
import com.neko.orderservice.repository.InvoiceRepository;
import com.neko.orderservice.repository.OrderRespository;
import com.neko.orderservice.service.InvoiceService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InvoiceServiceImpl implements InvoiceService {
    private final static Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private OrderRespository orderRepository;

    private InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceServiceImpl(OrderRespository orderRepository, InvoiceRepository invoiceRepository) {
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public byte[] getInvoice() {
        log.info("get invoice report");
        return generateInvoice();
    }

    private byte[] generateInvoice() {
        log.info("Generating pdf invoice report");
        List<Invoice> invoices = invoiceRepository.get();
        try {
            File design = ResourceUtils.getFile("classpath:jasper/invoice.jrxml");
            JasperReport report = JasperCompileManager.compileReport(design.getAbsolutePath());
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, buildParametersMap(), new JRBeanCollectionDataSource(invoices));

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (IOException | JRException exception) {
            log.error("Error has occured {}", exception.getMessage());
        }

        return null;
    }

    private Map<String, Object> buildParametersMap() {
        Map<String, Object> pdfInvoiceParams = new HashMap<>();
        pdfInvoiceParams.put("poweredby", "Toho Cinemas Roppongi Hills");
        return pdfInvoiceParams;
    }
}
