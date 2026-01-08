package com.guibedan.smart.stock.repository;

import com.guibedan.smart.stock.entity.PurchaseRequestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRequestRepository extends MongoRepository<PurchaseRequestEntity, String> {
}
