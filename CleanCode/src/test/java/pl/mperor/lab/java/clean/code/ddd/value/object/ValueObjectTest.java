package pl.mperor.lab.java.clean.code.ddd.value.object;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * 🧩 Value Object
 *
 * <p>A small simple object, like money or a date range, whose equality isn't based on identity.
 *
 * <p><b>Key Traits:</b>
 * - Immutable 🚫
 * - Equality by values ✅
 * - Reusable & shareable ♻️
 */
class ValueObjectTest {

    @Test
    void shouldAllowToUseMoneyAsValueObject() {
        var money = Money.of(BigDecimal.ZERO);
        Assertions.assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN), money.amount());

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Money(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Money(new BigDecimal("-1")));

        var three = Money.of(BigDecimal.ONE).add(Money.of(BigDecimal.TWO));
        Assertions.assertEquals(new BigDecimal("3.00"), three.amount());
        Assertions.assertEquals(new BigDecimal("2.00"), three.subtract(Money.of(BigDecimal.ONE)).amount());
    }

    @Test
    void shouldAllowToUseEmailAddressAsValueObject() {
        var email = new EmailAddress("john.doe@example.com");
        Assertions.assertEquals("john.doe@example.com", email.email());
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EmailAddress(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EmailAddress("not an email @.wtf"));
    }

    @Test
    void shouldAllowToUseDateTimeRangeAsValueObject() {
        Assertions.assertTrue(new DateRange(LocalDate.MIN, LocalDate.MAX)
                .isWithinRange(LocalDate.now()));
        Assertions.assertTrue(new DateRange(LocalDate.MIN, LocalDate.MAX)
                .isWithinRange(LocalDate.MIN));
        Assertions.assertTrue(new DateRange(LocalDate.MIN, LocalDate.MAX)
                .isWithinRange(LocalDate.MAX));
    }
}