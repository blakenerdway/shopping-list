package shoppinglist.beam.products.pojos.walmartjson;

import com.google.gson.annotations.SerializedName;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.util.Objects;

@DefaultSchema(JavaBeanSchema.class)
public class Basic{

    @SerializedName("image")
    private Image image;

    @SerializedName("maxAllowed")
    private int maxAllowed;

    @SerializedName("salesUnit")
    private String salesUnit = "";

    @SerializedName("name")
    private String name = "";

    @SerializedName("weightIncrement")
    private int weightIncrement;

    @SerializedName("isSnapEligible")
    private boolean isSnapEligible;

    @SerializedName("averageWeight")
    private Double averageWeight = -1.0;

    @SerializedName("productUrl")
    private String productUrl = "";

    @SerializedName("type")
    private String type = "";

    public void setImage(Image image){
        this.image = image;
    }

    public Image getImage(){
        return image;
    }

    public void setMaxAllowed(int maxAllowed){
        this.maxAllowed = maxAllowed;
    }

    public int getMaxAllowed(){
        return maxAllowed;
    }

    public void setSalesUnit(String salesUnit){
        this.salesUnit = salesUnit;
    }

    public String getSalesUnit(){
        return salesUnit;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setWeightIncrement(int weightIncrement){
        this.weightIncrement = weightIncrement;
    }

    public int getWeightIncrement(){
        return weightIncrement;
    }

    public void setIsSnapEligible(boolean isSnapEligible){
        this.isSnapEligible = isSnapEligible;
    }

    public boolean isIsSnapEligible(){
        return isSnapEligible;
    }

    public void setAverageWeight(Double averageWeight){
        this.averageWeight = averageWeight;
    }

    public Double getAverageWeight(){
        return averageWeight;
    }

    public void setProductUrl(String productUrl){
        this.productUrl = productUrl;
    }

    public String getProductUrl(){
        return productUrl;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basic basic = (Basic) o;
        return maxAllowed == basic.maxAllowed &&
                weightIncrement == basic.weightIncrement &&
                isSnapEligible == basic.isSnapEligible &&
                Objects.equals(image, basic.image) &&
                Objects.equals(salesUnit, basic.salesUnit) &&
                Objects.equals(name, basic.name) &&
                Objects.equals(averageWeight, basic.averageWeight) &&
                Objects.equals(productUrl, basic.productUrl) &&
                Objects.equals(type, basic.type);
    }

    @Override
    public int hashCode () {
        return Objects.hash(image, maxAllowed, salesUnit, name, weightIncrement, isSnapEligible, averageWeight, productUrl, type);
    }
}