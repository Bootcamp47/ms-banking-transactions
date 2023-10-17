package com.bc47.msbankingtransactions.service;

import com.bc47.msbankingtransactions.api.TransactionsApiDelegate;
import com.bc47.msbankingtransactions.entity.Transaction;
import com.bc47.msbankingtransactions.model.TransactionDTO;
import com.bc47.msbankingtransactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService implements TransactionsApiDelegate {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<List<TransactionDTO>> retrieveAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        List<TransactionDTO> transactionDTOList =
                transactions
                        .stream()
                        .map(this::createDTO)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(transactionDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionDTO> retrieveTransaction(String id) {
        List<Transaction> transactions = transactionRepository.findAll();
        Optional<TransactionDTO> transactionFound =
                transactions
                        .stream()
                        .filter(t -> Objects.equals(t.getId(), id))
                        .map(this::createDTO)
                        .findFirst();
        return transactionFound.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(new TransactionDTO()));
    }

    @Override
    public ResponseEntity<TransactionDTO> saveTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = Transaction
                .builder()
                .id(transactionDTO.getId())
                .purchaseId(transactionDTO.getPurchaseId())
                .source(transactionDTO.getSource())
                .transactionType(transactionDTO.getTransactionType())
                .createdAt(new Date().toString())
                .amount(transactionDTO.getAmount())
                .state(transactionDTO.getState())
                .build();
        transactionRepository.save(transaction);
        transactionDTO.setCreatedAt(new Date().toString());
        return ResponseEntity.ok(transactionDTO);
    }

    @Override
    public ResponseEntity<TransactionDTO> updateTransaction(TransactionDTO transactionDTO) {
        return saveTransaction(transactionDTO);
    }

    @Override
    public ResponseEntity<TransactionDTO> deleteTransaction(String id) {
        List<Transaction> transactions = transactionRepository.findAll();
        Optional<TransactionDTO> transactionFound =
                transactions
                        .stream()
                        .filter(t -> Objects.equals(t.getId(), id))
                        .map(transaction -> {
                            transactionRepository.deleteById(transaction.getId());
                            return createDTO(transaction);
                        })
                        .findFirst();
        return transactionFound.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(new TransactionDTO()));
    }

    private TransactionDTO createDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setPurchaseId(transaction.getPurchaseId());
        transactionDTO.setSource(transaction.getSource());
        transactionDTO.setTransactionType(transaction.getTransactionType());
        transactionDTO.setCreatedAt(transaction.getCreatedAt());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setState(transaction.getState());
        return transactionDTO;
    }
}
