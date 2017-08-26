package com.price;


public class ProductNumDepComparator {


    public static int compare(Price price1, Price price2) {
        if(price1.getProduct_code().compareTo(price2.getProduct_code()) > 0
                || price1.getNumber() > price2.getNumber() ||
                price1.getDepart() > price2.getDepart() ||
                price1.getBegin().compareTo(price2.getBegin()) > 0) return 1;
        else if(price1.getProduct_code().compareTo(price2.getProduct_code()) < 0
                || price1.getNumber() < price2.getNumber() ||
                price1.getDepart() < price2.getDepart() ||
                price1.getBegin().compareTo(price2.getBegin()) < 0) return -1;
        else return 0;
    }

}

/*public class PriceComparator implements Comparator<Price> {

    @Override
    public int compare(Price price1, Price price2) {
        if(price1.getProduct_code().compareTo(price2.getProduct_code()) > 0
                || price1.getNumber() > price2.getNumber() ||
                price1.getDepart() > price2.getDepart() ||
                price1.getBegin().compareTo(price2.getBegin()) > 0) return 1;
        else if(price1.getProduct_code().compareTo(price2.getProduct_code()) < 0
                || price1.getNumber() < price2.getNumber() ||
                price1.getDepart() < price2.getDepart() ||
                price1.getBegin().compareTo(price2.getBegin()) < 0) return -1;
        else return 0;
    }
}*/
