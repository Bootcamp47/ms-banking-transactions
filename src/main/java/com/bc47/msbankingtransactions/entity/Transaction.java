package com.bc47.msbankingtransactions.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transactions")
public class Transaction {

    @Id
    private Long id;
    private Long purchaseId;
    private String source;
    private String transactionType;
    private String createdAt;
    private Double amount;
    private String state;
}
