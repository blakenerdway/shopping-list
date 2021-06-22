package shoppinglist.beam.products.pojos.walmartjson;

import com.google.gson.annotations.SerializedName;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.util.Objects;


@DefaultSchema(JavaBeanSchema.class)
public class ValuesItem{

    @SerializedName("name")
    private String name;

    @SerializedName("count")
    private Integer count;

    @SerializedName("queryParam")
    private String queryParam;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValuesItem that = (ValuesItem) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(count, that.count) &&
                Objects.equals(queryParam, that.queryParam);
    }

    @Override
    public int hashCode () {
        return Objects.hash(name, count, queryParam);
    }

    public void setCount(int count){
        this.count = count;
    }

    public Integer getCount(){
        return count;
    }

    public void setQueryParam(String queryParam){
        this.queryParam = queryParam;
    }

    public String getQueryParam(){
        return queryParam;
    }
}