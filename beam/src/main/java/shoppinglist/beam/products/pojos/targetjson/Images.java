
package shoppinglist.beam.products.pojos.targetjson;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Images {

    @SerializedName("alternate_image_urls")
    private List<String> alternateImageUrls;
    @SerializedName("primary_image_url")
    private String primaryImageUrl;

    public List<String> getAlternateImageUrls() {
        return alternateImageUrls;
    }

    public void setAlternateImageUrls(List<String> alternateImageUrls) {
        this.alternateImageUrls = alternateImageUrls;
    }

    public String getPrimaryImageUrl() {
        return primaryImageUrl;
    }

    public void setPrimaryImageUrl(String primaryImageUrl) {
        this.primaryImageUrl = primaryImageUrl;
    }

}
