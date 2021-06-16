
package shoppinglist.beam.products.pojos.targetjson;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SearchRecommendations {

    @SerializedName("related_categories")
    private List<Object> relatedCategories;
    @SerializedName("related_queries")
    private List<Object> relatedQueries;

    public List<Object> getRelatedCategories() {
        return relatedCategories;
    }

    public void setRelatedCategories(List<Object> relatedCategories) {
        this.relatedCategories = relatedCategories;
    }

    public List<Object> getRelatedQueries() {
        return relatedQueries;
    }

    public void setRelatedQueries(List<Object> relatedQueries) {
        this.relatedQueries = relatedQueries;
    }

}
