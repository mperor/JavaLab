package pl.mperor.lab.java.clean.code.grasp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.java.clean.code.ddd.value.object.Money;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * 🤓 Information Expert
 *
 * <p>The principle states that a class should be responsible for behavior
 * that is directly related to its data.
 *
 * This test verifies that:
 * <ul>
 *     <li>{@link Order} calculates its total price.</li>
 *     <li>{@link Cart} aggregates the total cost of multiple orders.</li>
 * </ul>
 * Each class encapsulates its own logic, following the principle of expert knowledge.
 */
class InformationExpertTest {

    record Cart(List<Order> order) {
        Cart(Order... orders) {
            this(Arrays.asList(orders));
        }

        Cart(List<Order> order) {
            this.order = List.copyOf(order);
        }

        Money totalCost() {
            return order.stream()
                    .map(Order::totalPrice)
                    .reduce(Money.ZERO, Money::add);
        }
    }

    record Order(OrderItem item, int quantity) {
        Order {
            if (quantity < 1) {
                throw new IllegalArgumentException("Quantity must be greater then 0!");
            }
        }

        Money totalPrice() {
            return item.price.multiply(BigDecimal.valueOf(quantity));
        }
    }

    record OrderItem(String name, Money price) {}

    @Test
    void cartShouldFollowInformationExpertPrinciple () {
        var pencil = new OrderItem("Pencil", Money.of(BigDecimal.ONE));
        var rubber = new OrderItem("Rubber", Money.of(new BigDecimal("1.5")));
        var cart = new Cart(
                new Order(pencil, 2),
                new Order(rubber, 1)
        );

        Assertions.assertEquals(new Money(new BigDecimal("3.50")), cart.totalCost());
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Order(pencil, 0));
    }
}
