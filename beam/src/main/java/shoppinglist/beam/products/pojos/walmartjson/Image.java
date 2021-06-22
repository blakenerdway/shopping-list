package shoppinglist.beam.products.pojos.walmartjson;

import com.google.gson.annotations.SerializedName;
import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.util.Objects;

@DefaultSchema(JavaBeanSchema.class)
public class Image{

    @SerializedName("thumbnail")
    private String thumbnail = "";

    public void setThumbnail(String thumbnail){
        this.thumbnail = thumbnail;
    }

    public String getThumbnail(){
        return thumbnail;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(thumbnail, image.thumbnail);
    }

    @Override
    public int hashCode () {
        return Objects.hash(thumbnail);
    }
}