package shoppinglist.beam.products.pojos.targetjson;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Generated("net.hexar.json2pojo")
@DefaultSchema(JavaBeanSchema.class)
@SuppressWarnings("unused")
public class Product implements Serializable {
   @Override
   public String toString()
   {
      return "Product{" +
              "typename='" + typename + '\'' +
              ", item=" + item +
              ", originalTcin='" + originalTcin + '\'' +
              ", price=" + price +
              ", promotions=" + promotions +
              ", ratingsAndReviews=" + ratingsAndReviews +
              ", tcin='" + tcin + '\'' +
              '}';
   }

   public String get_Typename()
   {
      return typename;
   }

   public void set_Typename(String _Typename)
   {
      this.typename = _Typename;
   }

   @Override
   public int hashCode()
   {
      return new HashCodeBuilder(17, 37)
              .append(typename)
              .append(item)
              .append(originalTcin)
              .append(price)
              .append(promotions)
              .append(ratingsAndReviews)
              .append(tcin)
              .toHashCode();
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;

      if (o == null || getClass() != o.getClass()) return false;

      Product product = (Product) o;

      return new EqualsBuilder()
              .append(typename, product.typename)
              .append(item, product.item)
              .append(originalTcin, product.originalTcin)
              .append(price, product.price)
              .append(promotions, product.promotions)
              .append(ratingsAndReviews, product.ratingsAndReviews)
              .append(tcin, product.tcin)
              .isEquals();
   }

   public Item getItem()
   {
      return item;
   }

   public void setItem(Item item)
   {
      this.item = item;
   }

   public String getOriginalTcin()
   {
      return originalTcin;
   }

   public void setOriginalTcin(String originalTcin)
   {
      this.originalTcin = originalTcin;
   }

   public Price getPrice()
   {
      return price;
   }

   public void setPrice(Price price)
   {
      this.price = price;
   }

   public List<Object> getPromotions()
   {
      return promotions;
   }

   public void setPromotions(List<Object> promotions)
   {
      this.promotions = promotions;
   }

   public RatingsAndReviews getRatingsAndReviews()
   {
      return ratingsAndReviews;
   }

   public void setRatingsAndReviews(RatingsAndReviews ratingsAndReviews)
   {
      this.ratingsAndReviews = ratingsAndReviews;
   }

   public String getTcin()
   {
      return tcin;
   }

   public void setTcin(String tcin)
   {
      this.tcin = tcin;
   }
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

}
