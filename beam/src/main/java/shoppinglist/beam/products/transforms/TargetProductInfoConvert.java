package shoppinglist.beam.products.transforms;

import org.apache.beam.sdk.metrics.Counter;
import org.apache.beam.sdk.metrics.Metrics;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.beam.products.pojos.products.ProductInfo;
import shoppinglist.beam.products.pojos.targetjson.Product;

/**
 * @author Blake Ordway (bordway@ihmc.us) on 7/5/2021
 */
public class TargetProductInfoConvert extends SimpleFunction<KV<String, KV<String, Product>>, ProductInfo>
{
    @Override
    public ProductInfo apply(KV<String, KV<String, Product>> input)
    {
        String storeID = input.getKey();
        String searchTerm = input.getValue().getKey();

        Product result = input.getValue().getValue();
        String productName = result.getItem().getProductDescription().getTitle();
        String supplier = "Target";
        String brand = "UNKNOWN";
        try {
            brand = result.getItem().getPrimaryBrand().getName();

        } catch (NullPointerException e) {
            _logger.debug("Unknown brand for product name: {}", result.getItem().getProductDescription().getTitle());
        }
        double price = result.getPrice().getCurrentRetail();

        Counter brandCounter = Metrics.counter("brand-counter", brand);
        brandCounter.inc();

        return new ProductInfo(storeID, supplier, productName, brand, searchTerm, price);
    }

    private static final Logger _logger = LoggerFactory.getLogger(TargetProductInfoConvert.class);
}
