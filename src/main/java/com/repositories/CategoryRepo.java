package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entites.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
