
package shoppinglist.beam.products.pojos.targetjson;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Price {

    @SerializedName("current_retail")
    private Double currentRetail;
    @SerializedName("formatted_current_price")
    private String formattedCurrentPrice;
    @SerializedName("formatted_current_price_type")
    private String formattedCurrentPriceType;

    public Double getCurrentRetail() {
        return currentRetail;
    }

    public void setCurrentRetail(Double currentRetail) {
        this.currentRetail = currentRetail;
    }

    public String getFormattedCurrentPrice() {
        return formattedCurrentPrice;
    }

    public void setFormattedCurrentPrice(String formattedCurrentPrice) {
        this.formattedCurrentPrice = formattedCurrentPrice;
    }

    public String getFormattedCurrentPriceType() {
        return formattedCurrentPriceType;
    }

    public void setFormattedCurrentPriceType(String formattedCurrentPriceType) {
        this.formattedCurrentPriceType = formattedCurrentPriceType;
    }

}
