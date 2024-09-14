package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer>{

}
