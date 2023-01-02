package com.example.DBInteg.models;



import org.springframework.validation.annotation.Validated;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
@Validated
@Entity
public class TransactionModel {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(unique=true)
	private Integer transactionId;
	@NotBlank(message="Transaction type should not be blank")
	private String transaction_Type;
	@NotBlank(message="Watch list name should not be blank")
	private String watchListName;
	@Min(value=1, message="Limit is too low")
	@Max(value=100, message="Limit is too high")
	private Integer _limit;
	@Positive(message="From timestamp should be greater than zero")
	private Long timestampFrom;
	@Positive(message="To timestamp should be greater than zero")
	private Long timestampTo;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransaction_Type() {
		return transaction_Type;
	}
	public void setTransaction_Type(String transaction_Type) {
		this.transaction_Type = transaction_Type;
	}
	public String getWatchListName() {
		return watchListName;
	}
	public void setWatchListName(String watchListName) {
		this.watchListName = watchListName;
	}
	public Integer get_limit() {
		return _limit;
	}
	public void set_limit(Integer _limit) {
		this._limit = _limit;
	}
	public Long getTimestampFrom() {
		return timestampFrom;
	}
	public void setTimestampFrom(Long timestampFrom) {
		this.timestampFrom = timestampFrom;
	}
	public Long getTimestampTo() {
		return timestampTo;
	}
	public void setTimestampTo(Long timestampTo) {
		this.timestampTo = timestampTo;
	}
}
