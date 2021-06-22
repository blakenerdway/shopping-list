package shoppinglist.beam.products.pojos.walmartjson;

import com.google.gson.annotations.SerializedName;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.util.Objects;

@DefaultSchema(JavaBeanSchema.class)
public class Price{

    @SerializedName("displayCondition")
    private String displayCondition = "";

    @SerializedName("isClearance")
    private boolean isClearance;

    @SerializedName("isRollback")
    private boolean isRollback;

    @SerializedName("unit")
    private double unit;

    @SerializedName("displayPrice")
    private double displayPrice;

    @SerializedName("salesQuantity")
    private int salesQuantity;

    @SerializedName("priceUnitOfMeasure")
    private String priceUnitOfMeasure = "";

    @SerializedName("displayUnitPrice")
    private String displayUnitPrice = "";

    @SerializedName("previousPrice")
    private int previousPrice;

    @SerializedName("list")
    private double list;

    @SerializedName("salesUnitOfMeasure")
    private String salesUnitOfMeasure = "";

    public void setDisplayCondition(String displayCondition){
        this.displayCondition = displayCondition;
    }

    public String getDisplayCondition(){
        return displayCondition;
    }

    public void setIsClearance(boolean isClearance){
        this.isClearance = isClearance;
    }

    public boolean isIsClearance(){
        return isClearance;
    }

    public void setIsRollback(boolean isRollback){
        this.isRollback = isRollback;
    }

    public boolean isIsRollback(){
        return isRollback;
    }

    public void setUnit(double unit){
        this.unit = unit;
    }

    public double getUnit(){
        return unit;
    }

    public void setDisplayPrice(double displayPrice){
        this.displayPrice = displayPrice;
    }

    public double getDisplayPrice(){
        return displayPrice;
    }

    public void setSalesQuantity(int salesQuantity){
        this.salesQuantity = salesQuantity;
    }

    public int getSalesQuantity(){
        return salesQuantity;
    }

    public void setPriceUnitOfMeasure(String priceUnitOfMeasure){
        this.priceUnitOfMeasure = priceUnitOfMeasure;
    }

    public String getPriceUnitOfMeasure(){
        return priceUnitOfMeasure;
    }

    public void setDisplayUnitPrice(String displayUnitPrice){
        this.displayUnitPrice = displayUnitPrice;
    }

    public String getDisplayUnitPrice(){
        return displayUnitPrice;
    }

    public void setPreviousPrice(int previousPrice){
        this.previousPrice = previousPrice;
    }

    public int getPreviousPrice(){
        return previousPrice;
    }

    public void setList(double list){
        this.list = list;
    }

    public double getList(){
        return list;
    }

    public void setSalesUnitOfMeasure(String salesUnitOfMeasure){
        this.salesUnitOfMeasure = salesUnitOfMeasure;
    }

    public String getSalesUnitOfMeasure(){
        return salesUnitOfMeasure;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return isClearance == price.isClearance &&
                isRollback == price.isRollback &&
                Double.compare(price.unit, unit) == 0 &&
                Double.compare(price.displayPrice, displayPrice) == 0 &&
                salesQuantity == price.salesQuantity &&
                previousPrice == price.previousPrice &&
                Double.compare(price.list, list) == 0 &&
                Objects.equals(displayCondition, price.displayCondition) &&
                Objects.equals(priceUnitOfMeasure, price.priceUnitOfMeasure) &&
                Objects.equals(displayUnitPrice, price.displayUnitPrice) &&
                Objects.equals(salesUnitOfMeasure, price.salesUnitOfMeasure);
    }

    @Override
    public int hashCode () {
        return Objects.hash(displayCondition, isClearance, isRollback, unit, displayPrice, salesQuantity, priceUnitOfMeasure, displayUnitPrice, previousPrice, list, salesUnitOfMeasure);
    }
}