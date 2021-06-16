
package shoppinglist.beam.products.pojos.targetjson;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Search {

    @Expose
    private List<Product> products;
    @SerializedName("search_recommendations")
    private SearchRecommendations searchRecommendations;
    @SerializedName("search_response")
    private SearchResponse searchResponse;
    @SerializedName("search_suggestions")
    private List<String> searchSuggestions;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public SearchRecommendations getSearchRecommendations() {
        return searchRecommendations;
    }

    public void setSearchRecommendations(SearchRecommendations searchRecommendations) {
        this.searchRecommendations = searchRecommendations;
    }

    public SearchResponse getSearchResponse() {
        return searchResponse;
    }

    public void setSearchResponse(SearchResponse searchResponse) {
        this.searchResponse = searchResponse;
    }

    public List<String> getSearchSuggestions() {
        return searchSuggestions;
    }

    public void setSearchSuggestions(List<String> searchSuggestions) {
        this.searchSuggestions = searchSuggestions;
    }

}
