package com.guibedan.smart.stock.service;

import com.guibedan.smart.stock.controller.dto.StartDto;
import com.guibedan.smart.stock.domain.CsvStockItem;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SmartStockService {

    private final ReportService reportService;
    private final PurchaseSectorService purchaseSectorService;

    public SmartStockService(ReportService reportService, PurchaseSectorService purchaseSectorService) {
        this.purchaseSectorService = purchaseSectorService;
        this.reportService = reportService;
    }

    public void processReport(StartDto startDto) {

        try {
            var items = reportService.readStockReport(startDto.reportPath());

            items.forEach(item -> {

                if (item.getQuantity() < item.getReorderThreshold()) {
                    var reorderQuantity = calculateReorderQuantity(item);
                    purchaseSectorService.sendPurchaseRequest(item, reorderQuantity);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Integer calculateReorderQuantity(CsvStockItem item) {
        return item.getReorderThreshold() + ((int) Math.ceil(item.getReorderThreshold() * 0.2));
    }

}
