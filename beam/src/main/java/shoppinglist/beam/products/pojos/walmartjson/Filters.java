package shoppinglist.beam.products.pojos.walmartjson;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

@DefaultSchema(JavaBeanSchema.class)
public class Filters{

    @SerializedName("categories")
    private List<CategoriesItem> categories;

    @SerializedName("others")
    private List<OthersItem> others;

    public void setCategories(List<CategoriesItem> categories){
        this.categories = categories;
    }

    public List<CategoriesItem> getCategories(){
        return categories;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filters filters = (Filters) o;
        return Objects.equals(categories, filters.categories) &&
                Objects.equals(others, filters.others);
    }

    @Override
    public int hashCode () {
        return Objects.hash(categories, others);
    }

    public void setOthers(List<OthersItem> others){
        this.others = others;
    }

    public List<OthersItem> getOthers(){
        return others;
    }
}