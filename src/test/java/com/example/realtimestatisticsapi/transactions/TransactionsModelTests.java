package com.example.realtimestatisticsapi.transactions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionsModelTests {

    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void transactionInPastWithAmount_shouldHaveNoViolations() {
        Transaction transaction = new Transaction(new Date(), BigDecimal.ZERO);
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void transactionInFuture_shouldHaveViolationInTimestampAttribute() {
        Date futureDate = Date.from(
                LocalDate.now().
                        plusDays(1).
                        atStartOfDay().
                        atZone(ZoneId.systemDefault()).toInstant()
        );
        Transaction transaction = new Transaction(futureDate, BigDecimal.ZERO);
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        assertEquals(1, violations.size());
        ConstraintViolation<Transaction> violation = violations.iterator().next();
        assertEquals("must be a date in the past or in the present",
                violation.getMessage());
        assertEquals("timestamp", violation.getPropertyPath().toString());
    }

    @Test
    public void transactionWithNullAmount_shouldHaveViolationInAmountAttribute() {
        Transaction transaction = new Transaction(new Date(), null);
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        assertEquals(1, violations.size());
        ConstraintViolation<Transaction> violation = violations.iterator().next();
        assertEquals("must not be null",
                violation.getMessage());
        assertEquals("amount", violation.getPropertyPath().toString());
    }

    @Test
    public void transactionWithNullTimestamp_shouldHaveViolationInTimestampAttribute() {
        Transaction transaction = new Transaction(null, BigDecimal.ZERO);
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        assertEquals(1, violations.size());
        ConstraintViolation<Transaction> violation = violations.iterator().next();
        assertEquals("must not be null",
                violation.getMessage());
        assertEquals("timestamp", violation.getPropertyPath().toString());
    }
}
