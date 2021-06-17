
package shoppinglist.beam.products.pojos.targetjson;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.io.Serializable;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
@DefaultSchema(JavaBeanSchema.class)
public class RatingsAndReviews implements Serializable {

    @Expose
    private Statistics statistics;

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

}
