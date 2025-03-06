package com.imposto.repository;

import com.imposto.model.TaxModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepository extends JpaRepository<TaxModel, Long> {
}
