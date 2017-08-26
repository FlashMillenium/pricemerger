package com.price.pricemerger;

import com.price.Price;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PriceMergerTest {
    @Test
    public void mergePricesGivenDataTest() throws Exception {
        Price test = Price.of(12345, "hzchto", 1, 2, LocalDateTime.now(),
                LocalDateTime.of(2017, Month.DECEMBER, 20, 21, 00), 9999);
        Price test2 = Price.of(112, "car", 1, 1, LocalDateTime.now(), LocalDateTime.now(), 100);

        List<Price> prices = new ArrayList<>();
        prices.add(test);
        prices.add(test2);
        List<Price> expectedValue = new ArrayList<>();
        expectedValue.add(test);
        expectedValue.add(test2);
        List<Price> acualValue = PriceMerger.mergePrices(prices,prices);
//        assertEquals(expectedValue,acualValue);
        assertSame(expectedValue,acualValue);

    }

}