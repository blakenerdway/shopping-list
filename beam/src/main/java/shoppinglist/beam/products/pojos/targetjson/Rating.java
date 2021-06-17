
package shoppinglist.beam.products.pojos.targetjson;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.io.Serializable;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
@DefaultSchema(JavaBeanSchema.class)
public class Rating implements Serializable {

    @Expose
    private Double average;
    @Expose
    private Long count;

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
