package shoppinglist.beam.products.pojos.walmartjson;

import com.google.gson.annotations.SerializedName;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.util.Objects;

@DefaultSchema(JavaBeanSchema.class)
public class WalmartProduct
{
    @SerializedName("result")
    private Result result;

    @SerializedName("product")
    private String product;

    @SerializedName("store")
    private String store;

    public void setResult(Result result){
        this.result = result;
    }

    public Result getResult(){
        return result;
    }

    public void setProduct(String product){
        this.product = product;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalmartProduct that = (WalmartProduct) o;
        return Objects.equals(result, that.result) &&
                Objects.equals(product, that.product) &&
                Objects.equals(store, that.store);
    }

    @Override
    public int hashCode () {
        return Objects.hash(result, product, store);
    }

    public String getProduct(){
        return product;
    }

    public void setStore(String store){
        this.store = store;
    }

    public String getStore(){
        return store;
    }
}