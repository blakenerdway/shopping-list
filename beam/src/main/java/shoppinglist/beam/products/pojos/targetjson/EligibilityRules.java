
package shoppinglist.beam.products.pojos.targetjson;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.io.Serializable;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
@DefaultSchema(JavaBeanSchema.class)
public class EligibilityRules implements Serializable {

    @SerializedName("add_on")
    private AddOn addOn;
    @Expose
    private Grocery grocery;
    @SerializedName("scheduled_delivery")
    private ScheduledDelivery scheduledDelivery;

    public AddOn getAddOn() {
        return addOn;
    }

    public void setAddOn(AddOn addOn) {
        this.addOn = addOn;
    }

    public Grocery getGrocery() {
        return grocery;
    }

    public void setGrocery(Grocery grocery) {
        this.grocery = grocery;
    }

    public ScheduledDelivery getScheduledDelivery() {
        return scheduledDelivery;
    }

    public void setScheduledDelivery(ScheduledDelivery scheduledDelivery) {
        this.scheduledDelivery = scheduledDelivery;
    }

}
