
package shoppinglist.beam.products.pojos.targetjson;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Product {

    @SerializedName("__typename")
    private String typename;
    @Expose
    private Item item;
    @SerializedName("original_tcin")
    private String originalTcin;
    @Expose
    private Price price;
    @Expose
    private List<Object> promotions;
    @SerializedName("ratings_and_reviews")
    private RatingsAndReviews ratingsAndReviews;
    @Expose
    private String tcin;

    public String get_Typename() {
        return typename;
    }

    public void set_Typename(String _Typename) {
        this.typename = _Typename;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getOriginalTcin() {
        return originalTcin;
    }

    public void setOriginalTcin(String originalTcin) {
        this.originalTcin = originalTcin;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public List<Object> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Object> promotions) {
        this.promotions = promotions;
    }

    public RatingsAndReviews getRatingsAndReviews() {
        return ratingsAndReviews;
    }

    public void setRatingsAndReviews(RatingsAndReviews ratingsAndReviews) {
        this.ratingsAndReviews = ratingsAndReviews;
    }

    public String getTcin() {
        return tcin;
    }

    public void setTcin(String tcin) {
        this.tcin = tcin;
    }

}
