
package shoppinglist.beam.products.pojos.targetjson;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SearchResponse {

    @SerializedName("facet_list")
    private List<FacetList> facetList;
    @SerializedName("sort_options")
    private List<SortOption> sortOptions;
    @SerializedName("typed_metadata")
    private TypedMetadata typedMetadata;
    @SerializedName("visual_facet_list")
    private List<VisualFacetList> visualFacetList;

    public List<FacetList> getFacetList() {
        return facetList;
    }

    public void setFacetList(List<FacetList> facetList) {
        this.facetList = facetList;
    }

    public List<SortOption> getSortOptions() {
        return sortOptions;
    }

    public void setSortOptions(List<SortOption> sortOptions) {
        this.sortOptions = sortOptions;
    }

    public TypedMetadata getTypedMetadata() {
        return typedMetadata;
    }

    public void setTypedMetadata(TypedMetadata typedMetadata) {
        this.typedMetadata = typedMetadata;
    }

    public List<VisualFacetList> getVisualFacetList() {
        return visualFacetList;
    }

    public void setVisualFacetList(List<VisualFacetList> visualFacetList) {
        this.visualFacetList = visualFacetList;
    }

}
