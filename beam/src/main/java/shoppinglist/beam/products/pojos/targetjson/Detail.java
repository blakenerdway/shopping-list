
package shoppinglist.beam.products.pojos.targetjson;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Detail {

    @SerializedName("display_name")
    private String displayName;
    @SerializedName("facet_canonical")
    private String facetCanonical;
    @SerializedName("facet_id")
    private String facetId;
    @SerializedName("facet_source")
    private String facetSource;
    @SerializedName("label_image_url")
    private String labelImageUrl;
    @Expose
    private String url;
    @Expose
    private String value;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFacetCanonical() {
        return facetCanonical;
    }

    public void setFacetCanonical(String facetCanonical) {
        this.facetCanonical = facetCanonical;
    }

    public String getFacetId() {
        return facetId;
    }

    public void setFacetId(String facetId) {
        this.facetId = facetId;
    }

    public String getFacetSource() {
        return facetSource;
    }

    public void setFacetSource(String facetSource) {
        this.facetSource = facetSource;
    }

    public String getLabelImageUrl() {
        return labelImageUrl;
    }

    public void setLabelImageUrl(String labelImageUrl) {
        this.labelImageUrl = labelImageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
