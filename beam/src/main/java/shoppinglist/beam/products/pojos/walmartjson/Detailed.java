package shoppinglist.beam.products.pojos.walmartjson;

import com.google.gson.annotations.SerializedName;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.util.Objects;

public class Detailed{

    @SerializedName("rating")
    private double rating;

    @SerializedName("reviewsCount")
    private int reviewsCount;

    public void setRating(double rating){
        this.rating = rating;
    }

    public double getRating(){
        return rating;
    }

    public void setReviewsCount(int reviewsCount){
        this.reviewsCount = reviewsCount;
    }

    public int getReviewsCount(){
        return reviewsCount;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Detailed detailed = (Detailed) o;
        return Double.compare(detailed.rating, rating) == 0 &&
                reviewsCount == detailed.reviewsCount;
    }

    @Override
    public int hashCode () {
        return Objects.hash(rating, reviewsCount);
    }
}