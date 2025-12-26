package com.guibedan.customer.connect.service;

import com.guibedan.customer.connect.controller.dto.CreateCustomerDto;
import com.guibedan.customer.connect.controller.dto.CustomerDetailDto;
import com.guibedan.customer.connect.controller.dto.UpdateCustomerDto;
import com.guibedan.customer.connect.entity.CustomerEntity;
import com.guibedan.customer.connect.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public UUID createCustomer(@Valid CreateCustomerDto createCustomerDto) {
        if (customerRepository.existsByEmailOrCpfOrPhone(createCustomerDto.email(), createCustomerDto.cpf(), createCustomerDto.phone())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email or CPF or PHONE already exists");
        }

        var customer = CustomerEntity.builder()
                .fullName(createCustomerDto.fullName())
                .cpf(createCustomerDto.cpf().replaceAll("[^0-9]", ""))
                .email(createCustomerDto.email())
                .phone(createCustomerDto.phone())
                .build();

        return customerRepository.save(customer).getId();
    }

    public void updateCustomer(@Valid UpdateCustomerDto updateCustomerDto, UUID customerId) {
        var customer = getCustomerEntity(customerId);

        if (StringUtils.hasText(updateCustomerDto.email())) {
            customer.setEmail(updateCustomerDto.email());
        }

        if (StringUtils.hasText(updateCustomerDto.phone())) {
            customer.setPhone(updateCustomerDto.phone());
        }

        customerRepository.save(customer);
    }

    public void deleteCustomer(UUID customerId) {
        if (!customerRepository.existsById(customerId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");

        customerRepository.deleteById(customerId);
    }

    public CustomerDetailDto getCustomer(UUID customerId) {
        return CustomerDetailDto.fromEntity(getCustomerEntity(customerId));
    }

    public Page<CustomerDetailDto> listCustomers(int page, int pageSize, String orderBy, String cpf, String email) {
        var pageRequest = getPageRequest(page, pageSize, orderBy);
        return findWithFilter(cpf, email, pageRequest);
    }

    private Page<CustomerDetailDto> findWithFilter(String cpf, String email, PageRequest pageRequest) {
        if (StringUtils.hasText(cpf) && StringUtils.hasText(email)) {
            return customerRepository.findByCpfAndEmail(cpf, email, pageRequest).map(CustomerDetailDto::fromEntity);
        }

        if (StringUtils.hasText(cpf)) {
            return customerRepository.findByCpf(cpf, pageRequest).map(CustomerDetailDto::fromEntity);
        }

        if (StringUtils.hasText(email)) {
            return customerRepository.findByEmail(email, pageRequest).map(CustomerDetailDto::fromEntity);
        }

        return customerRepository.findAll(pageRequest).map(CustomerDetailDto::fromEntity);
    }

    private PageRequest getPageRequest(int page, int pageSize, String orderBy) {
        var direction = orderBy.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return PageRequest.of(page, pageSize, direction, "createdAt");
    }

    private CustomerEntity getCustomerEntity(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
