package com.example.DBInteg.controllers;



import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.example.DBInteg.models.TransactionModel;
import com.example.DBInteg.models.TransactionResponseModel;
import com.example.DBInteg.repositories.TransactionRepository;
import com.example.DBInteg.repositories.TransactionResponseRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

@Validated
@Controller
@RequestMapping(path="/demo")
public class UserController {
	@Autowired
	private Validator validator;
  @Autowired
  private TransactionRepository transactionRepository;
  @Autowired
  private TransactionResponseRepository transactionResponseRepository;

  
  @PostMapping(path="/transac")
  public ResponseEntity<TransactionResponseModel> saveTransaction(@RequestBody TransactionModel data){
	  TransactionResponseModel response=new TransactionResponseModel();
	  response.setTransactionId(data.getTransactionId());
	  Set<ConstraintViolation<TransactionModel>> violations = validator.validate(data);
	  if (!violations.isEmpty()) {
          StringBuilder sb = new StringBuilder();
          for (ConstraintViolation<TransactionModel> constraintViolation : violations) {
              sb.append(constraintViolation.getMessage());
          }
          response.setErrors(sb.toString());
          response.setResponse("failure");
      }
	  else {
		  response.setResponse("success");
		  response.setErrors("none");
	  }
	  transactionResponseRepository.save(response);
	  transactionRepository.save(data);
	  return new ResponseEntity<>(response,HttpStatus.CREATED);
  }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, WebRequest webRequest) {
      Map<String, Object> objectBody = new LinkedHashMap<>();
      objectBody.put("Current Timestamp", new Date());
      List<String> exceptionalErrors = exception.getBindingResult()
              .getFieldErrors()
              .stream()
              .map(x -> x.getDefaultMessage())
              .collect(Collectors.toList());

      objectBody.put("Errors", exceptionalErrors);

      return new ResponseEntity<>(objectBody, HttpStatus.BAD_REQUEST);
  }
   @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
   public ResponseEntity<?> SQLconstraintViolationException(SQLIntegrityConstraintViolationException ex, WebRequest request) {
     List<String> errors = new ArrayList<>();
     Iterator<Throwable> exceptions=ex.iterator();
     while(exceptions.hasNext()) {
  	   errors.add(exceptions.next().getMessage());
     }
     Map<String, List<String>> result = new HashMap<>();
     result.put("errors", errors);

     return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(ConstraintViolationException.class)
   public ResponseEntity<?> constraintViolationException(ConstraintViolationException ex, WebRequest request) {
     List<String> errors = new ArrayList<>();

     ex.getConstraintViolations().forEach(cv -> errors.add(cv.getMessage()));

     Map<String, List<String>> result = new HashMap<>();
     result.put("errors", errors);

     return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
   }
}