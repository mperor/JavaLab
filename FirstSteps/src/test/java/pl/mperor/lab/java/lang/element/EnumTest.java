package pl.mperor.lab.java.lang.element;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class EnumTest {

    @Test
    public void testEnumeration() {
        Assertions.assertEquals(0, Season.SPRING.ordinal());
        var hottestSeasons = Season.SUMMER;
        Assertions.assertTrue(hottestSeasons == Season.SUMMER);
        Assertions.assertEquals("FALL", Season.FALL.name());
        Assertions.assertEquals(Season.WINTER, Season.valueOf("WINTER"));
        Assertions.assertArrayEquals(new Month[]{Month.DECEMBER, Month.JANUARY, Month.FEBRUARY},
                Season.WINTER.getMonths());
        Assertions.assertEquals(4, Season.values().length);
    }

    enum Season {
        SPRING(Month.MARCH, Month.APRIL, Month.MAY),
        SUMMER(Month.JUNE, Month.JULY, Month.AUGUST),
        FALL(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER),
        WINTER(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY);

        private final Month[] months;

        Season(Month... months) {
            this.months = months;
        }

        Month[] getMonths() {
            return Arrays.copyOf(months, months.length);
        }
    }

    enum Month {
        JANUARY,
        FEBRUARY,
        MARCH,
        APRIL,
        MAY,
        JUNE,
        JULY,
        AUGUST,
        SEPTEMBER,
        OCTOBER,
        NOVEMBER,
        DECEMBER
    }

}
