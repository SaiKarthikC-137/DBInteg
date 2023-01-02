package com.example.DBInteg.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.DBInteg.models.TransactionResponseModel;

public interface TransactionResponseRepository extends CrudRepository<TransactionResponseModel, Integer> {

}
