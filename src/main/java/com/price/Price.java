package com.price;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.LocalDateTime;


//@ToString(exclude = {"id", "product_code", "number", "depart", "begin", "end", "value"})
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(exclude = {"id"})
@Data public class Price {

    @NonNull private long id; //id in BD
    @NonNull private String product_code;
    @NonNull private int number;
    @NonNull private int depart;
    @NonNull private LocalDateTime begin;
    @NonNull private LocalDateTime end;
    @NonNull private long value;


    public Price(Price price){
        this.id = price.id;
        this.product_code = price.product_code;
        this.number = price.number;
        this.depart = price.depart;
        this.begin = price.begin;
        this.end = price.end;
        this.value = price.value;
    }



    @EqualsAndHashCode
    public static class ProductNumDep {
        private String product_code;
        private int number;
        private int depart;

        public static int comparePND(ProductNumDep p1, ProductNumDep p2){

            if(p1.product_code.compareTo(p2.product_code) >0
                    || p1.number > p2.number
                    || p1.depart > p2.depart) return 1;
            else if(p1.product_code.compareTo(p2.product_code) < 0
                    || p1.number < p2.number
                    || p1.depart < p2.depart) return -1;
            else return 0;
        }

        ProductNumDep(String product_code, int number, int depart){
            this.product_code = product_code;
            this.number = number;
            this.depart = depart;
        }
}

    public ProductNumDep getProductNumDep()
    {
        return new ProductNumDep(product_code, number, depart);
    }


}
