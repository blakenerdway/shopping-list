package shoppinglist.beam.products.pojos.walmartjson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import javax.annotation.Nullable;

@DefaultSchema(JavaBeanSchema.class)
public class OthersItem{

    @SerializedName("values")
    @Nullable
    private List<ValuesItem> values = new ArrayList<>();

    @SerializedName("name")
    private String name;

    public void setValues(List<ValuesItem> values){
        this.values = values;
    }

    public List<ValuesItem> getValues(){
        return values;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OthersItem that = (OthersItem) o;
        return Objects.equals(values, that.values) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode () {
        return Objects.hash(values, name);
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}