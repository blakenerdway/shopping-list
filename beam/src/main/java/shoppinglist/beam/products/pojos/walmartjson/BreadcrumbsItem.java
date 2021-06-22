package shoppinglist.beam.products.pojos.walmartjson;

import com.google.gson.annotations.SerializedName;

public class BreadcrumbsItem{

    @SerializedName("cat_level")
    private int catLevel;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    @SerializedName("queryParam")
    private String queryParam;

    public void setCatLevel(int catLevel){
        this.catLevel = catLevel;
    }

    public int getCatLevel(){
        return catLevel;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setQueryParam(String queryParam){
        this.queryParam = queryParam;
    }

    public String getQueryParam(){
        return queryParam;
    }
}