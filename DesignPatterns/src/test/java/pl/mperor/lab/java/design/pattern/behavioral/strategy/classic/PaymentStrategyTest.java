package pl.mperor.lab.java.design.pattern.behavioral.strategy.classic;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.common.TestUtils;

import java.time.LocalDate;

public class PaymentStrategyTest {

    @Test
    public void shouldShoppingAllowToPayWithDifferentPaymentStrategy() {
        Shopping shopping = new Shopping(new PaypalStrategy("mypaypall@example.com", "1234"));
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(new Item("b1", "Smart TV", 999_00));
        shoppingCart.addItem(new Item("b2", "Raspberry Pi", 99_99));
        shoppingCart.addItem(new Item("b3", "DVD Player", 44_95));

        var out = TestUtils.setTempSystemOut();
        shopping.pay(shoppingCart);
        shopping.setStrategy(new CreditCardStrategy("John Doe", "1234 1111 2222 1010", "123", LocalDate.parse("2025-12-31")));
        shopping.pay(shoppingCart);
        shopping.pay(shoppingCart, _ -> System.out.println("Fake payment 💰, do not use in production!"));

        var outLines = out.lines();
        Assertions.assertTrue(outLines.getFirst().endsWith("paid with 📱 paypal!"));
        Assertions.assertTrue(outLines.getSecond().endsWith("paid by 💳 credit card!"));
        Assertions.assertEquals("Fake payment 💰, do not use in production!", outLines.getThird());
        TestUtils.resetSystemOut();
    }

    @Test
    public void shouldReturnCorrectTotalPriceWhenItemsAdded() {
        var cart = new ShoppingCart();
        cart.addItem(new Item("A1", "iPhone X", 2000 * 100));
        cart.addItem(new Item("A2", "Laptop", 2500 * 100), 2);
        cart.addItem(new Item("A3", "Tablet Neo", 199 * 100), 3);
        Assertions.assertEquals(7597d, cart.computeTotal());
    }
}