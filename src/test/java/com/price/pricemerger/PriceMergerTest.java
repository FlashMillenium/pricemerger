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
        List<Price> oldPrices = new ArrayList<Price>(){{
            add(Price.of(1, "122856", 1, 1,
                    LocalDateTime.of(2013, 1, 1, 0, 0),
                    LocalDateTime.of(2013, 1, 31, 23, 59, 59),
                    11000));
            add(Price.of(2, "122856", 2, 1,
                    LocalDateTime.of(2013, 1, 10, 0, 0),
                    LocalDateTime.of(2013, 1, 20, 23, 59, 59),
                    99000));
            add(Price.of(3, "6654", 1,2,
                    LocalDateTime.of(2013,1,1,0,0),
                    LocalDateTime.of(2013,1,31,0,0),
                    5000));
        }};
        List<Price> newPrices = new ArrayList<Price>(){{
            add(Price.of(4, "122856", 1,1,
                    LocalDateTime.of(2013,1,20,0,0),
                    LocalDateTime.of(2013,2,20,23,59,59),
                    11000));
            add(Price.of(5, "122856", 2,1,
                    LocalDateTime.of(2013,1,15,0,0),
                    LocalDateTime.of(2013,1,25,23,59,59),
                    92000));
            add(Price.of(6, "6654", 1, 2,
                    LocalDateTime.of(2013, 1, 12, 0, 0),
                    LocalDateTime.of(2013, 1, 13, 0, 0),
                    4000));
        }};
        List<Price> expectedValue = new ArrayList<Price>(){{
            add(Price.of(1, "122856", 1, 1,
                    LocalDateTime.of(2013, 1, 1, 0, 0),
                    LocalDateTime.of(2013, 2, 20, 23, 59, 59),
                    11000));
            add(Price.of(2, "122856", 2, 1,
                    LocalDateTime.of(2013, 1, 10, 0, 0),
                    LocalDateTime.of(2013, 1, 15, 0, 0),
                    99000));
            add(Price.of(3, "122856", 2, 1,
                    LocalDateTime.of(2013, 1, 15, 0, 0),
                    LocalDateTime.of(2013, 1, 25, 23, 59, 59),
                    92000));
            add(Price.of(4, "6654", 1, 2,
                    LocalDateTime.of(2013, 1, 1, 0, 0),
                    LocalDateTime.of(2013, 1, 12, 0, 0),
                    5000));
            add(Price.of(5, "6654", 1, 2,
                    LocalDateTime.of(2013, 1, 12, 0, 0),
                    LocalDateTime.of(2013, 1, 13, 0, 0),
                    4000));
            add(Price.of(6, "6654", 1, 2,
                    LocalDateTime.of(2013, 1, 13, 0, 0),
                    LocalDateTime.of(2013, 1, 31, 0, 0),
                    5000));
        }};

        List<Price> actualValue = PriceMerger.mergePrices(oldPrices,newPrices);
//        assertEquals(expectedValue,actualValue);
        assertArrayEquals(expectedValue.toArray(),actualValue.toArray());
    }

}