
package shoppinglist.beam.products.pojos.targetjson;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
@DefaultSchema(JavaBeanSchema.class)
public class SoftBullets implements Serializable {

    @Expose
    private List<String> bullets;

    public List<String> getBullets() {
        return bullets;
    }

    public void setBullets(List<String> bullets) {
        this.bullets = bullets;
    }

}
