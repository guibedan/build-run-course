package com.guibedan.customer.connect.repository;

import com.guibedan.customer.connect.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    boolean existsByEmailOrCpfOrPhone(String email, String cpf, String phone);

    Page<CustomerEntity> findByCpf(String cpf, PageRequest pageRequest);
    Page<CustomerEntity> findByEmail(String email, PageRequest pageRequest);
    Page<CustomerEntity> findByCpfAndEmail(String cpf, String email, PageRequest pageRequest);

}
