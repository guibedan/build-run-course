package com.guibedan.customer.connect.controller;

import com.guibedan.customer.connect.controller.dto.CreateCustomerDto;
import com.guibedan.customer.connect.controller.dto.CustomerDetailDto;
import com.guibedan.customer.connect.controller.dto.UpdateCustomerDto;
import com.guibedan.customer.connect.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/v1/customers")
public class CustomerControllerV1 {

    private final CustomerService customerService;

    public CustomerControllerV1(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody CreateCustomerDto createCustomerDto) {
        var customerId = customerService.createCustomer(createCustomerDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customerId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDetailDto> getCustomer(@PathVariable UUID customerId) {
        var customer = customerService.getCustomer(customerId);
        return ResponseEntity.ok().body(customer);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerDetailDto>> listCustomers(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                 @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                                                                 @RequestParam(name = "orderBy", defaultValue = "desc") String orderBy,
                                                                 @RequestParam(name = "cpf", required = false) String cpf,
                                                                 @RequestParam(name = "email", required = false) String email) {
        Page<CustomerDetailDto> customers = customerService.listCustomers(page, pageSize, orderBy, cpf, email);
        return ResponseEntity.ok().body(customers);
    }

    @PutMapping("{customerId}")
    public ResponseEntity<Void> updateCustomer(@Valid @RequestBody UpdateCustomerDto updateCustomerDto, @PathVariable UUID customerId) {
        customerService.updateCustomer(updateCustomerDto, customerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

}
