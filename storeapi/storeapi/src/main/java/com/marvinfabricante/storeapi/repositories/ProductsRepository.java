package com.marvinfabricante.storeapi.repositories;

import com.marvinfabricante.storeapi.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product, Integer> {
}
