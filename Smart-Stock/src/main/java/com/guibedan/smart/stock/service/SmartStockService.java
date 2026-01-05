package com.guibedan.smart.stock.service;

import com.guibedan.smart.stock.controller.dto.StartDto;
import com.guibedan.smart.stock.domain.CsvStockItem;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SmartStockService {

    private final ReportService reportService;

    public SmartStockService(ReportService reportService) {
        this.reportService = reportService;
    }

    public void processReport(StartDto startDto) {

        try {
            var items = reportService.readStockReport(startDto.reportPath());

            items.forEach(item -> {

                if (item.getQuantity() < item.getReorderThreshold()) {
                    var reorderQuantity = calculateReorderQuantity(item);
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
