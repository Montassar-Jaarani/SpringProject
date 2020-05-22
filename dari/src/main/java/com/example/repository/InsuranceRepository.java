package com.example.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Insurance;


@Repository
public interface InsuranceRepository extends CrudRepository<Insurance, Integer> {
	List<Insurance> findByName(String name);
}
