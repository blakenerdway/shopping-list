package shoppinglist.beam.products.transforms;

import org.apache.beam.sdk.metrics.Counter;
import org.apache.beam.sdk.metrics.Metrics;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.beam.products.pojos.products.ProductInfo;
import shoppinglist.beam.products.pojos.walmartjson.OthersItem;
import shoppinglist.beam.products.pojos.walmartjson.ProductsItem;
import shoppinglist.beam.products.pojos.walmartjson.ValuesItem;
import shoppinglist.beam.products.pojos.walmartjson.WalmartProduct;

import java.util.List;

/**
 * @author Blake Ordway (bordway@ihmc.us) on 7/5/2021
 */
public class WalmartProductInfoConvert extends DoFn<WalmartProduct, ProductInfo>
{
    @ProcessElement
    public void processElement (ProcessContext c) {
        WalmartProduct input = c.element();
        String storeID = input.getStore();
        String searchTerm = input.getProduct();

        List<ProductsItem> results = input.getResult().getProducts();
        for (ProductsItem result : results) {
            String productName = result.getBasic().getName();
            String supplier = "Walmart";
            String brand = "UNKNOWN";

            // Get brand
            List<OthersItem> others = input.getResult().getFilters().getOthers();
            OthersItem brandObj = null;
            for (OthersItem other : others) {
                if (other.getName().equalsIgnoreCase("brand")) {
                    brandObj = other;
                }
            }
            if (brandObj != null) {
                if (brandObj.getValues() != null) {
                    for (ValuesItem value : brandObj.getValues()) {
                        String name = value.getName();
                        if (productName.contains(name)) {
                            brand = name;
                            productName = productName.replace(brand, "");
                        }
                    }
                }
            }
            double price = result.getStore().getPrice().getList();

            Counter brandCounter = Metrics.counter("brand-counter", brand);
            brandCounter.inc();

            c.output(new ProductInfo(storeID, supplier, productName, brand, searchTerm, price));
        }
    }

    private static final Logger _logger = LoggerFactory.getLogger(WalmartProductInfoConvert.class);
}
