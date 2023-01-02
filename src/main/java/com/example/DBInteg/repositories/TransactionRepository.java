package com.example.DBInteg.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.DBInteg.models.TransactionModel;

public interface TransactionRepository extends CrudRepository<TransactionModel, Integer> {

}
