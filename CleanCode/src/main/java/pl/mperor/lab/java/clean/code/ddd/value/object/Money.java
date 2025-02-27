package pl.mperor.lab.java.clean.code.ddd.value.object;

import java.math.BigDecimal;
import java.math.RoundingMode;

record Money(BigDecimal amount) {

    Money(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount must not be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative!");
        }
        this.amount = setScale(amount);
    }

    private BigDecimal setScale(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money other) {
        return new Money(this.amount.subtract(other.amount));
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }
}
