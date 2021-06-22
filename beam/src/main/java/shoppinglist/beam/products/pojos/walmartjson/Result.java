package shoppinglist.beam.products.pojos.walmartjson;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

@DefaultSchema(JavaBeanSchema.class)
public class Result{

    @SerializedName("filters")
    private Filters filters;

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return totalCount == result.totalCount &&
                Objects.equals(filters, result.filters) &&
                Objects.equals(products, result.products);
    }

    @Override
    public int hashCode () {
        return Objects.hash(filters, totalCount, products);
    }

    @SerializedName("totalCount")
    private int totalCount;

    @SerializedName("products")
    private List<ProductsItem> products;

    public void setFilters(Filters filters){
        this.filters = filters;
    }

    public Filters getFilters(){
        return filters;
    }

    public void setTotalCount(int totalCount){
        this.totalCount = totalCount;
    }

    public int getTotalCount(){
        return totalCount;
    }

    public void setProducts(List<ProductsItem> products){
        this.products = products;
    }

    public List<ProductsItem> getProducts(){
        return products;
    }
}