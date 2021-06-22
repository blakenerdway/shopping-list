package shoppinglist.beam.products.pojos.walmartjson;

import com.google.gson.annotations.SerializedName;

public class ModuleData{

    @SerializedName("adUuid")
    private String adUuid;

    @SerializedName("moduleInfo")
    private String moduleInfo;

    @SerializedName("pageBeacons")
    private PageBeacons pageBeacons;

    public void setAdUuid(String adUuid){
        this.adUuid = adUuid;
    }

    public String getAdUuid(){
        return adUuid;
    }

    public void setModuleInfo(String moduleInfo){
        this.moduleInfo = moduleInfo;
    }

    public String getModuleInfo(){
        return moduleInfo;
    }

    public void setPageBeacons(PageBeacons pageBeacons){
        this.pageBeacons = pageBeacons;
    }

    public PageBeacons getPageBeacons(){
        return pageBeacons;
    }
}