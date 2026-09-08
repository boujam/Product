package com.example.myproject.demo.repository;

import com.example.myproject.demo.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    /*
     * JpaRepository hérite lui-même de plusieurs interfaces
     * 
     * productRepository.save(product);
     * 
     * productRepository.saveAll(products);
     * 
     * productRepository.findById(1L);
     * 
     * productRepository.existsById(1L);
     * 
     * productRepository.findAll();
     * 
     * productRepository.findAllById(ids);
     * 
     * productRepository.count();
     * 
     * productRepository.deleteById(1L);
     * 
     * productRepository.delete(product);
     * 
     * productRepository.deleteAll();
     * 
     * productRepository.findAll(Sort.by("name"));
     * 
     * productRepository.findAll(PageRequest.of(0, 10));
     * 
     * productRepository.flush();
     * 
     * productRepository.saveAndFlush(product);
     * 
     * productRepository.deleteAllInBatch();
     * 
     * productRepository.getReferenceById(1L);
     * 
     */

}