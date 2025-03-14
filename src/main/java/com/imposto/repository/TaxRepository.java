package com.imposto.repository;

import com.imposto.model.TaxModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<TaxModel, Long> {
    void deleteById(Long id);
    boolean existsByName(String name);
}
