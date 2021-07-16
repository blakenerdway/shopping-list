package shoppinglist.beam.products.transforms;

import com.google.gson.Gson;
import org.apache.beam.sdk.metrics.Counter;
import org.apache.beam.sdk.metrics.Metrics;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.beam.products.pojos.products.ProductInfo;
import shoppinglist.beam.products.pojos.targetjson.Product;

/**
 * @author Blake Ordway (bordway@ihmc.us) on 7/5/2021
 */
public class TargetProductInfoConvert extends DoFn<KV<String, KV<String, Product>>, ProductInfo> {
    @ProcessElement
    public void processElement(ProcessContext c)
    {
        String storeID = c.element().getKey();
        String searchTerm = c.element().getValue().getKey();


        Product result = c.element().getValue().getValue();
        String productName = result.getItem().getProductDescription().getTitle();
        String supplier = "Target";
        String brand = "UNKNOWN";
        try {
            brand = result.getItem().getPrimaryBrand().getName();

        } catch (NullPointerException e) {
            _logger.debug("Unknown brand for product name: {}", result.getItem().getProductDescription().getTitle());
        }
        Double price = -1.0;
        if (result.getPrice() == null || result.getPrice().getCurrentRetail() == null) {
            _logger.error(new Gson().toJson(result));
        }
        else if (result.getPrice().getCurrentRetail() != null){
            price = result.getPrice().getCurrentRetail();
        }
        else if (result.getPrice().getFormattedCurrentPrice() != null) {
            price = Double.parseDouble(result.getPrice().getFormattedCurrentPrice().split(" - ")[0].replace("$", ""));
        }

        Counter brandCounter = Metrics.counter("brand-counter", brand);
        brandCounter.inc();

        c.output(new ProductInfo(storeID, supplier, productName, brand, searchTerm, price));
    }

    private static final Logger _logger = LoggerFactory.getLogger(TargetProductInfoConvert.class);
}
