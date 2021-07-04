package com.example.realtimestatisticsapi.transactions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RequestMapping("/transactions")
@RestController
public class TransactionsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsController.class);

    @Autowired
    TransactionsService transactionService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createTransaction(@RequestBody @Valid Transaction transaction) throws TransactionInPastException {
        transactionService.saveTransaction(transaction);
        if (transactionService.isTransactionOld(transaction)){
            throw new TransactionInPastException(String.format("%s is old", transaction));
        }
        LOGGER.info("{} is saved", transaction.toString());
    }

    @ResponseStatus(NO_CONTENT)
    @ResponseBody
    @ExceptionHandler(TransactionInPastException.class)
    public void handleTransactionInPastException(TransactionInPastException ex) {
        LOGGER.info(ex.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        LOGGER.error("Exception!", ex);
    }

    @ResponseStatus(FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void HandleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        LOGGER.error("Exception!", ex);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void deleteTransactions() {
        transactionService.deleteAllTransactions();
        LOGGER.info("All transactions deleted");
    }
}
