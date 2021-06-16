
package shoppinglist.beam.products.pojos.targetjson;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Item {

    @SerializedName("cart_add_on_threshold")
    private Double cartAddOnThreshold;
    @Expose
    private String dpci;
    @SerializedName("eligibility_rules")
    private EligibilityRules eligibilityRules;
    @Expose
    private Enrichment enrichment;
    @Expose
    private Fulfillment fulfillment;
    @SerializedName("merchandise_classification")
    private MerchandiseClassification merchandiseClassification;
    @SerializedName("primary_brand")
    private PrimaryBrand primaryBrand;
    @SerializedName("product_description")
    private ProductDescription productDescription;
    @SerializedName("product_vendors")
    private List<ProductVendor> productVendors;
    @SerializedName("relationship_type")
    private String relationshipType;
    @SerializedName("relationship_type_code")
    private String relationshipTypeCode;

    public Double getCartAddOnThreshold() {
        return cartAddOnThreshold;
    }

    public void setCartAddOnThreshold(Double cartAddOnThreshold) {
        this.cartAddOnThreshold = cartAddOnThreshold;
    }

    public String getDpci() {
        return dpci;
    }

    public void setDpci(String dpci) {
        this.dpci = dpci;
    }

    public EligibilityRules getEligibilityRules() {
        return eligibilityRules;
    }

    public void setEligibilityRules(EligibilityRules eligibilityRules) {
        this.eligibilityRules = eligibilityRules;
    }

    public Enrichment getEnrichment() {
        return enrichment;
    }

    public void setEnrichment(Enrichment enrichment) {
        this.enrichment = enrichment;
    }

    public Fulfillment getFulfillment() {
        return fulfillment;
    }

    public void setFulfillment(Fulfillment fulfillment) {
        this.fulfillment = fulfillment;
    }

    public MerchandiseClassification getMerchandiseClassification() {
        return merchandiseClassification;
    }

    public void setMerchandiseClassification(MerchandiseClassification merchandiseClassification) {
        this.merchandiseClassification = merchandiseClassification;
    }

    public PrimaryBrand getPrimaryBrand() {
        return primaryBrand;
    }

    public void setPrimaryBrand(PrimaryBrand primaryBrand) {
        this.primaryBrand = primaryBrand;
    }

    public ProductDescription getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(ProductDescription productDescription) {
        this.productDescription = productDescription;
    }

    public List<ProductVendor> getProductVendors() {
        return productVendors;
    }

    public void setProductVendors(List<ProductVendor> productVendors) {
        this.productVendors = productVendors;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getRelationshipTypeCode() {
        return relationshipTypeCode;
    }

    public void setRelationshipTypeCode(String relationshipTypeCode) {
        this.relationshipTypeCode = relationshipTypeCode;
    }

}
