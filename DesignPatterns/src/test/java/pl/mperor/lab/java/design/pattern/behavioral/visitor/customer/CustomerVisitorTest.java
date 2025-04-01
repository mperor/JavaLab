package pl.mperor.lab.java.design.pattern.behavioral.visitor.customer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerVisitorTest {

    @Test
    public void testGenerateInvitationLetterForDifferentTypeCustomer() {
        var generator = new InvitationLetterGenerator();
        Assertions.assertEquals("👨 Welcome normal customer!", generator.generate(new RegularCustomer()).content());
        Assertions.assertEquals("‍💼 Welcome business customer!", generator.generate(new CorporateCustomer()).content());
        Assertions.assertEquals("🧑‍💼 Welcome vip customer!", generator.generate(new PremiumCustomer()).content());
    }

    @Test
    public void testDiscountLevelForDifferentTypeCustomer() {
        var provider = new DiscountProvider();
        Assertions.assertEquals(DiscountLevel.BRONZE, provider.get(new RegularCustomer()));
        Assertions.assertEquals(DiscountLevel.SILVER, provider.get(new CorporateCustomer()));
        Assertions.assertEquals(DiscountLevel.GOLD, provider.get(new PremiumCustomer()));
    }
}