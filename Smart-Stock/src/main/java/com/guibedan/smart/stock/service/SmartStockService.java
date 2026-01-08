package com.guibedan.smart.stock.service;

import com.guibedan.smart.stock.controller.dto.StartDto;
import com.guibedan.smart.stock.domain.CsvStockItem;
import com.guibedan.smart.stock.entity.PurchaseRequestEntity;
import com.guibedan.smart.stock.repository.PurchaseRequestRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class SmartStockService {

    private final ReportService reportService;
    private final PurchaseSectorService purchaseSectorService;
    private final PurchaseRequestRepository purchaseRequestRepository;

    public SmartStockService(ReportService reportService, PurchaseSectorService purchaseSectorService,
                             PurchaseRequestRepository purchaseRequestRepository) {
        this.reportService = reportService;
        this.purchaseSectorService = purchaseSectorService;
        this.purchaseRequestRepository = purchaseRequestRepository;
    }

    public void processReport(StartDto startDto) {

        try {
            var items = reportService.readStockReport(startDto.reportPath());

            items.forEach(item -> {

                if (item.getQuantity() < item.getReorderThreshold()) {
                    var reorderQuantity = calculateReorderQuantity(item);
                    var purchaseWithSuccess = purchaseSectorService.sendPurchaseRequest(item, reorderQuantity);
                    persist(item, reorderQuantity, purchaseWithSuccess);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void persist(CsvStockItem item, Integer reorderQuantity, boolean purchaseWithSuccess) {
        var entity = PurchaseRequestEntity.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .supplierName(item.getSupplierName())
                .supplierEmail(item.getSupplierEmail())
                .quantityOnStock(item.getQuantity())
                .reorderThreshold(item.getReorderThreshold())
                .lastStockUpdateTime(LocalDateTime.parse(item.getLastStockUpdateTime()))
                .purchaseQuantity(reorderQuantity)
                .purchaseWithSuccess(purchaseWithSuccess)
                .purchaseDateTime(LocalDateTime.now())
                .build();

        purchaseRequestRepository.save(entity);
    }

    private Integer calculateReorderQuantity(CsvStockItem item) {
        return item.getReorderThreshold() + ((int) Math.ceil(item.getReorderThreshold() * 0.2));
    }

}
