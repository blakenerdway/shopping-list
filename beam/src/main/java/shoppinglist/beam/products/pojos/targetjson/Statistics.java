
package shoppinglist.beam.products.pojos.targetjson;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.io.Serializable;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
@DefaultSchema(JavaBeanSchema.class)
public class Statistics implements Serializable {

    @Expose
    private Rating rating;

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

}
