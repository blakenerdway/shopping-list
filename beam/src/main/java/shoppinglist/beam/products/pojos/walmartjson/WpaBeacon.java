package shoppinglist.beam.products.pojos.walmartjson;

import com.google.gson.annotations.SerializedName;

public class WpaBeacon{

    @SerializedName("moduleData")
    private ModuleData moduleData;

    public void setModuleData(ModuleData moduleData){
        this.moduleData = moduleData;
    }

    public ModuleData getModuleData(){
        return moduleData;
    }
}