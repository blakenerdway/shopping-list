package shoppinglist.beam.products.pipelines.debug;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.beam.products.pojos.products.ProductInfo;
import shoppinglist.beam.products.pojos.walmartjson.OthersItem;
import shoppinglist.beam.products.pojos.walmartjson.ProductsItem;
import shoppinglist.beam.products.pojos.walmartjson.ValuesItem;
import shoppinglist.beam.products.pojos.walmartjson.WalmartProduct;
import shoppinglist.beam.products.transforms.JdbcProductInfoWrite;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Blake Ordway (bordway@ihmc.us) on 6/20/2021
 */
public class WalmartDebug
{
    static void runDebuggingWalmartPipeline(List<String> jsons){

        Pipeline p = Pipeline.create();
        PCollection<WalmartProduct> asPojo = p.apply(Create.of(jsons))
                .apply("Clean json", ParDo.of(new DoFn<String, String>()
                {
                    @ProcessElement
                    public void processElement(ProcessContext c){
                        Type type = new TypeToken<Map<String, Object>>() {}.getType();
                        Map<String, Object> data = new Gson().fromJson(c.element(), type);

                        for (Iterator<Map.Entry<String, Object>> it = data.entrySet().iterator(); it.hasNext();) {
                            Map.Entry<String, Object> entry = it.next();
                            if (entry.getValue() == null) {
                                _logger.error("Removing key: {}", entry.getKey());
                                it.remove();
                            } else if (entry.getValue() instanceof List) {
                                if (((List<?>) entry.getValue()).isEmpty()) {
                                    _logger.error("Removing key: {}", entry.getKey());
                                    it.remove();
                                }
                            }
                        }
                        String json = new GsonBuilder().create().toJson(data);
                        c.output(json);
                    }
                }))
                .apply("As pojo", ParDo.of(new DoFn<String, WalmartProduct>()
                {
                    @ProcessElement
                    public void processElement (ProcessContext c) {
                        String input = c.element();
                        WalmartProduct productPojo = new GsonBuilder().serializeNulls().create().fromJson(input, WalmartProduct.class);
                        c.output(productPojo);
                    }
                }));

        asPojo.apply("Convert to ProductInfo", ParDo.of(new DoFn<WalmartProduct, ProductInfo>()
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
                    c.output(new ProductInfo(storeID, supplier, productName, brand, searchTerm, price));
                }
            }
        }))
                .apply("Log", MapElements.via(new SimpleFunction<ProductInfo, ProductInfo>()
                {
                    @Override
                    public ProductInfo apply (ProductInfo input) {
                        _logger.error(input.toString());
                        return input;
                    }
                }));
//                .apply("JDBC transform", new JdbcProductInfoWrite());


//      PAssert.that().containsInAnyOrder();


        p.run().waitUntilFinish();
    }
    private static final Logger _logger = LoggerFactory.getLogger(WalmartDebug.class);
}
