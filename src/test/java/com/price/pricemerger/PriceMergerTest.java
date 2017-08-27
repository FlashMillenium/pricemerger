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
        assertArrayEquals(expectedValue.toArray(),actualValue.toArray());
    }

    @Test
    public void mergePricesWithEmptyNewPrice() throws Exception {
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
        List<Price> newPrices = new ArrayList<Price>();
        List<Price> expectedValue = new ArrayList<Price>(){{
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
        }};;

        List<Price> actualValue = PriceMerger.mergePrices(oldPrices,newPrices);
        assertArrayEquals(expectedValue.toArray(),actualValue.toArray());
    }

    @Test
    public void mergePricesWithEmptyOldPrice() throws Exception {
        List<Price> oldPrices = new ArrayList<>();
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

        List<Price> actualValue = PriceMerger.mergePrices(oldPrices,newPrices);
        assertArrayEquals(expectedValue.toArray(),actualValue.toArray());
    }

    @Test(expected = PriceDateCollisionException.class)
    public void getExceptionWithTimeCollision() throws Exception {
        List<Price> oldPrices = new ArrayList<>();
        List<Price> newPrices = new ArrayList<Price>(){{
            add(Price.of(4, "122856", 1,1,
                    LocalDateTime.of(2013,1,20,0,0),
                    LocalDateTime.of(2014,2,20,23,59,59),
                    11000));
            add(Price.of(5, "122856", 1,1,
                    LocalDateTime.of(2013,1,15,0,0),
                    LocalDateTime.of(2013,1,25,23,59,59),
                    92000));
        }};
        List<Price> actualValue = PriceMerger.mergePrices(oldPrices,newPrices);
    }

    @Test
    public void mergeNewPriceBeforeOldPrice() throws Exception {
        List<Price> oldPrices = new ArrayList<Price>(){{
            add(Price.of(1, "1111", 3,5,
                    LocalDateTime.of(2010,6,1,0,0),
                    LocalDateTime.of(2010,6,20,12,0,0),
                    100));
            add(Price.of(2, "1111", 3,5,
                    LocalDateTime.of(2010,9,11,0,0),
                    LocalDateTime.of(2010,9,11,23,59,59),
                    5));
        }};
        List<Price> newPrices = new ArrayList<Price>(){{
            add(Price.of(3, "1111", 3,5,
                    LocalDateTime.of(2009,1,1,0,0),
                    LocalDateTime.of(2009,4,1,23,59,59),
                    100));
            add(Price.of(4, "1111", 3,5,
                    LocalDateTime.of(2010,9,1,0,0),
                    LocalDateTime.of(2010,9,10,0,0),
                    10));
        }};
        List<Price> expectedValue = new ArrayList<Price>(){{
            add(Price.of(3, "1111", 3,5,
                    LocalDateTime.of(2009,1,1,0,0),
                    LocalDateTime.of(2009,4,1,23,59,59),
                    100));
            add(Price.of(1, "1111", 3,5,
                    LocalDateTime.of(2010,6,1,0,0),
                    LocalDateTime.of(2010,6,20,12,0,0),
                    100));
            add(Price.of(4, "1111", 3,5,
                    LocalDateTime.of(2010,9,1,0,0),
                    LocalDateTime.of(2010,9,10,0,0),
                    10));
            add(Price.of(2, "1111", 3,5,
                    LocalDateTime.of(2010,9,11,0,0),
                    LocalDateTime.of(2010,9,11,23,59,59),
                    5));
        }};
        List<Price> actualValue = PriceMerger.mergePrices(oldPrices,newPrices);
        assertArrayEquals(expectedValue.toArray(),actualValue.toArray());
    }

    @Test
    public void mergeCollisionNewPriceBeforeOldPrice() throws Exception {
        List<Price> oldPrices = new ArrayList<Price>(){{
            add(Price.of(5, "2222", 2,8,
                    LocalDateTime.of(2011,2,1,0,0),
                    LocalDateTime.of(2011,5,5,0,0),
                    42));
            add(Price.of(6, "2222", 2,8,
                    LocalDateTime.of(2011,9,1,0,0),
                    LocalDateTime.of(2011,12,11,0,0,0),
                    55));
        }};
        List<Price> newPrices = new ArrayList<Price>(){{
            add(Price.of(7, "2222", 2,8,
                    LocalDateTime.of(2011,1,1,0,0),
                    LocalDateTime.of(2011,4,15,23,59,59),
                    42));
            add(Price.of(8, "2222", 2,8,
                    LocalDateTime.of(2011,8,1,0,0),
                    LocalDateTime.of(2011,10,10,0,0),
                    1011));
        }};
        List<Price> expectedValue = new ArrayList<Price>(){{
            add(Price.of(5, "2222", 2,8,
                    LocalDateTime.of(2011,1,1,0,0),
                    LocalDateTime.of(2011,5,5,0,0),
                    42));
            add(Price.of(8, "2222", 2,8,
                    LocalDateTime.of(2011,8,1,0,0),
                    LocalDateTime.of(2011,10,10,0,0),
                    1011));
            add(Price.of(6, "2222", 2,8,
                    LocalDateTime.of(2011,10,10,0,0),
                    LocalDateTime.of(2011,12,11,0,0,0),
                    55));

        }};
        List<Price> actualValue = PriceMerger.mergePrices(oldPrices,newPrices);
        assertArrayEquals(expectedValue.toArray(),actualValue.toArray());
    }

    @Test
    public void mergeCollisionDeleteAllOldPrice() throws Exception {
        List<Price> oldPrices = new ArrayList<Price>(){{
            add(Price.of(80, "3333", 2,8,
                    LocalDateTime.of(2012,4,1,0,0),
                    LocalDateTime.of(2012,9,5,0,0),
                    100));
        }};
        List<Price> newPrices = new ArrayList<Price>(){{
            add(Price.of(7, "3333", 2,8,
                    LocalDateTime.of(2012,2,6,0,0),
                    LocalDateTime.of(2012,6,1,0,0),
                    100));
            add(Price.of(8, "3333", 2,8,
                    LocalDateTime.of(2012,6,1,0,0),
                    LocalDateTime.of(2012,11,10,0,0),
                    11));
        }};
        List<Price> expectedValue = new ArrayList<Price>(){{
            add(Price.of(7, "3333", 2,8,
                    LocalDateTime.of(2012,2,6,0,0),
                    LocalDateTime.of(2012,6,1,0,0),
                    100));
            add(Price.of(8, "3333", 2,8,
                    LocalDateTime.of(2012,6,1,0,0),
                    LocalDateTime.of(2012,11,10,0,0),
                    11));
        }};
        List<Price> actualValue = PriceMerger.mergePrices(oldPrices,newPrices);
        assertArrayEquals(expectedValue.toArray(),actualValue.toArray());
    }

}