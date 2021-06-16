package shoppinglist.beam.products.pojos.targetjson;

import com.google.gson.annotations.Expose;

public class TargetProduct {

    @Expose
    private String product;
    @Expose
    private Result result;
    @Expose
    private String store;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

}
