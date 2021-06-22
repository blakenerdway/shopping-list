package shoppinglist.beam.products.pojos.walmartjson;

import com.google.gson.annotations.SerializedName;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.util.Objects;

public class Store{

    @SerializedName("price")
    private Price price;

    @SerializedName("isOutOfStock")
    private boolean isOutOfStock;

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return isOutOfStock == store.isOutOfStock &&
                Objects.equals(price, store.price);
    }

    @Override
    public int hashCode () {
        return Objects.hash(price, isOutOfStock);
    }

    public void setPrice(Price price){
        this.price = price;
    }

    public Price getPrice(){
        return price;
    }

    public void setIsOutOfStock(boolean isOutOfStock){
        this.isOutOfStock = isOutOfStock;
    }

    public boolean isIsOutOfStock(){
        return isOutOfStock;
    }
}