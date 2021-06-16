
package shoppinglist.beam.products.pojos.targetjson;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ProductDescription {

    @SerializedName("bullet_descriptions")
    private List<String> bulletDescriptions;
    @SerializedName("soft_bullets")
    private SoftBullets softBullets;
    @Expose
    private String title;

    public List<String> getBulletDescriptions() {
        return bulletDescriptions;
    }

    public void setBulletDescriptions(List<String> bulletDescriptions) {
        this.bulletDescriptions = bulletDescriptions;
    }

    public SoftBullets getSoftBullets() {
        return softBullets;
    }

    public void setSoftBullets(SoftBullets softBullets) {
        this.softBullets = softBullets;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
