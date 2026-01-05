package com.guibedan.smart.stock.service;

import com.guibedan.smart.stock.controller.dto.StartDto;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
