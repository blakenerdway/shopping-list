package shoppinglist.beam.products.pojos.walmartjson;

import com.google.gson.annotations.SerializedName;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.util.Objects;

@DefaultSchema(JavaBeanSchema.class)
public class ProductsItem{

    @SerializedName("USItemId")
    private String usItemId = "";

    @SerializedName("detailed")
    private Detailed detailed;

    @SerializedName("offerId")
    private String offerId = "";

    @SerializedName("store")
    private Store store;

    @SerializedName("sku")
    private String sku = "";

    @SerializedName("basic")
    private Basic basic;

    public void setUSItemId(String uSItemId){
        this.usItemId = uSItemId;
    }

    public String getUSItemId(){
        return usItemId;
    }

    public void setDetailed(Detailed detailed){
        this.detailed = detailed;
    }

    public Detailed getDetailed(){
        return detailed;
    }

    public void setOfferId(String offerId){
        this.offerId = offerId;
    }

    public String getOfferId(){
        return offerId;
    }

    public void setStore(Store store){
        this.store = store;
    }

    public Store getStore(){
        return store;
    }

    public void setSku(String sku){
        this.sku = sku;
    }

    public String getSku(){
        return sku;
    }

    public void setBasic(Basic basic){
        this.basic = basic;
    }

    public Basic getBasic(){
        return basic;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsItem that = (ProductsItem) o;
        return Objects.equals(usItemId, that.usItemId) &&
                Objects.equals(detailed, that.detailed) &&
                Objects.equals(offerId, that.offerId) &&
                Objects.equals(store, that.store) &&
                Objects.equals(sku, that.sku) &&
                Objects.equals(basic, that.basic);
    }

    @Override
    public int hashCode () {
        return Objects.hash(usItemId, detailed, offerId, store, sku, basic);
    }
}