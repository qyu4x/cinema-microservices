package com.neko.orderservice.repository;

import com.neko.orderservice.model.entity.Invoice;

import java.util.List;

public interface InvoiceRepository {

    List<Invoice> get();


}
