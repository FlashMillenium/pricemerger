package com.price.pricemerger;

import com.price.Price;
import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class PriceMerger {

    public static List<Price> mergePrices(@NonNull List<Price> oldPrice, @NonNull List<Price> newPrice) {

        TreeMap<Price.ProductNumDep, TreeSet<Price>> newMap = getMapList(newPrice);
        TreeMap<Price.ProductNumDep, TreeSet<Price>> oldMap = getMapList(oldPrice);
        newMap.values().forEach(PriceMerger::checkDateCollisionException);
        List<Price> result = mergeSubMaps(oldMap, newMap);

        return result;
    }


    private static TreeMap<Price.ProductNumDep, TreeSet<Price>> getMapList(List<Price> prices){
        return prices.stream()
                .collect(groupingBy(Price::getProductNumDep,
                        () -> new TreeMap<>(Price.ProductNumDep::comparePND), //group by product_code, number and department as key for treemap
                        Collectors.toCollection(() // collect in treeset(on begin date) for sorting purpose as value of Treemap
                                -> new TreeSet<>(Comparator.comparing(Price::getBegin)))));

    }

    private static List<Price> mergeSubMaps(TreeMap<Price.ProductNumDep, TreeSet<Price>>  oldPrices,
                                            TreeMap<Price.ProductNumDep, TreeSet<Price>>  newPrices){
        List<Price> result = new ArrayList<>();
        Iterator<Map.Entry<Price.ProductNumDep, TreeSet<Price>>> oldPriceIter = oldPrices.entrySet().iterator();
        Iterator<Map.Entry<Price.ProductNumDep, TreeSet<Price>>> newPriceIter = newPrices.entrySet().iterator();
        Map.Entry<Price.ProductNumDep, TreeSet<Price>> oldPrice =  getNextMapPrice(oldPriceIter);
        Map.Entry<Price.ProductNumDep, TreeSet<Price>> newPrice =  getNextMapPrice(newPriceIter);

        while ( oldPrice != null || newPrice != null) {
            if(newPrice == null){
                result.addAll(oldPrice.getValue());
                oldPrice =  getNextMapPrice(oldPriceIter);
            } else if (oldPrice == null) {
                result.addAll(newPrice.getValue());
                newPrice =  getNextMapPrice(newPriceIter);
            } else {
                switch (Price.ProductNumDep.comparePND(oldPrice.getKey(), newPrice.getKey())) {
                    case 1:
                        result.addAll(newPrice.getValue());
                        newPrice =  getNextMapPrice(newPriceIter);
                        break;
                    case -1:
                        result.addAll(oldPrice.getValue());
                        oldPrice =  getNextMapPrice(oldPriceIter);
                        break;
                    default:
                        result.addAll(resolveConflict(oldPrice.getValue(), newPrice.getValue()));
                        oldPrice =  getNextMapPrice(oldPriceIter);
                        newPrice =  getNextMapPrice(newPriceIter);
                }
            }
        }
        return result;
    }
    private static Map.Entry<Price.ProductNumDep, TreeSet<Price>> getNextMapPrice(Iterator<Map.Entry<Price.ProductNumDep, TreeSet<Price>>> priceIterator){
        if(priceIterator.hasNext()) return priceIterator.next();
        else return null;
    }

    private static List<Price> resolveConflict(TreeSet<Price> oldPrices, TreeSet<Price> newPrices){
        List<Price> result = new ArrayList<>();

        Iterator<Price> oldPriceIter = oldPrices.iterator(); // use pollFirst is better for reading
        Iterator<Price> newPriceIter = newPrices.iterator(); // but bad for perfomance because tree need
        Price oldPrice = getNextPrice(oldPriceIter);          // resource for rebalancing
        Price newPrice = getNextPrice(newPriceIter);

        while (oldPrice != null || newPrice != null){
            if (oldPrice==null) {
                result.add(newPrice);
                newPrice = getNextPrice(newPriceIter);
            } else if (newPrice==null) {
                result.add(oldPrice);
                oldPrice = getNextPrice(oldPriceIter);
            } else if (oldPrice.getBegin().compareTo(newPrice.getBegin()) > 0   //      |   |  old time  1var
                    && oldPrice.getBegin().compareTo(newPrice.getEnd()) > 0) {  // |   |       new time
                result.add(newPrice);
                newPrice = getNextPrice(newPriceIter);
            } else if (oldPrice.getBegin().compareTo(newPrice.getBegin()) >= 0 //   |   |    old time   2var
                    && oldPrice.getBegin().compareTo(newPrice.getEnd()) <= 0   // |   |      new time
                    && oldPrice.getEnd().compareTo(newPrice.getEnd()) > 0) {
                if (oldPrice.getValue() == newPrice.getValue()) {
                    oldPrice.setBegin(newPrice.getBegin());
                    newPrice = getNextPrice(newPriceIter);
                } else {
                    oldPrice.setBegin(newPrice.getEnd());
                    result.add(newPrice);
                    newPrice = getNextPrice(newPriceIter);
                }
            } else if (oldPrice.getBegin().compareTo(newPrice.getBegin()) >= 0 //   |   |    old time 3var
                    && oldPrice.getEnd().compareTo(newPrice.getEnd()) <= 0) {  // |       |  new time
                oldPrice = getNextPrice(oldPriceIter);  //reduce to 1 or 2 var

            } else if (oldPrice.getBegin().compareTo(newPrice.getBegin()) < 0 //   |        |  old time  4var
                    && oldPrice.getEnd().compareTo(newPrice.getEnd()) > 0) {  //     |    |    new time
                if (oldPrice.getValue() == newPrice.getValue()) {
                    newPrice = getNextPrice(newPriceIter);
                } else {
                    Price oldResultPrice = new Price(oldPrice);
                    oldResultPrice.setEnd(newPrice.getBegin());
                    result.add(oldResultPrice);
                    oldPrice.setBegin(newPrice.getEnd()); //reduce to 2 var
                }
            } else if (oldPrice.getBegin().compareTo(newPrice.getBegin()) < 0 //   |   |     old time  5var
                    && oldPrice.getEnd().compareTo(newPrice.getBegin()) >= 0  //     |   |   new time
                    && oldPrice.getEnd().compareTo(newPrice.getEnd()) <= 0) {
                if (oldPrice.getValue() == newPrice.getValue()) {
                    newPrice.setBegin(oldPrice.getBegin());
                } else {
                    oldPrice.setEnd(newPrice.getBegin());
                    result.add(oldPrice);
                    oldPrice = getNextPrice(oldPriceIter);
                }
            } else {                                                        //   |  |       old time 6var
                result.add(oldPrice);                                       //       |   |  new time
                oldPrice = getNextPrice(oldPriceIter);
            }
        }
        return result;
    }

    private static Price getNextPrice(Iterator<Price> priceIterator){
        if(priceIterator.hasNext()) return priceIterator.next();
        else return null;
    }

    private static void checkDateCollisionException(TreeSet<Price> newPrices){
        if (newPrices.size()<2) return;

        Iterator<Price> PriceIter = newPrices.iterator();
        Price firstPrice = PriceIter.next();
        Price secondPrice;

        do {
            secondPrice = PriceIter.next();
            if(firstPrice.getEnd().compareTo(secondPrice.getBegin()) > 0)
                throw new PriceDateCollisionException("In one time can be only one price on this number and department");
            firstPrice = secondPrice;
        }while (PriceIter.hasNext());
    }


}
