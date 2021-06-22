package shoppinglist.beam.products.pojos.walmartjson;

import com.google.gson.annotations.SerializedName;

public class SortOptionsItem{

    @SerializedName("isDefault")
    private boolean isDefault;

    @SerializedName("sortName")
    private String sortName;

    @SerializedName("sortId")
    private String sortId;

    public void setIsDefault(boolean isDefault){
        this.isDefault = isDefault;
    }

    public boolean isIsDefault(){
        return isDefault;
    }

    public void setSortName(String sortName){
        this.sortName = sortName;
    }

    public String getSortName(){
        return sortName;
    }

    public void setSortId(String sortId){
        this.sortId = sortId;
    }

    public String getSortId(){
        return sortId;
    }
}