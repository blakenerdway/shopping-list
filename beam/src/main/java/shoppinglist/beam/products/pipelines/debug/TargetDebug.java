package shoppinglist.beam.products.pipelines.debug;

import com.google.gson.Gson;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import shoppinglist.beam.products.pojos.products.ProductInfo;
import shoppinglist.beam.products.pojos.targetjson.Product;
import shoppinglist.beam.products.pojos.targetjson.TargetProduct;
import shoppinglist.beam.products.transforms.JdbcProductInfoWrite;

import java.util.List;

public class TargetDebug {

   static void runDebuggingTargetPipeline(List<String> jsons){
      Pipeline p = Pipeline.create();
      p.apply(Create.of(jsons))
              .apply("As pojo", ParDo.of(new DoFn<String, KV<String, KV<String, Product>>>() {
         @ProcessElement
         public void processElement(ProcessContext c)
         {
            String input = c.element();
            TargetProduct productPojo = new Gson().fromJson(input, TargetProduct.class);
            List<Product> product = productPojo.getResult().getData().getSearch().getProducts();
            for (Product product1 : product) {
               c.output(KV.of(productPojo.getStore(), KV.of(productPojo.getProduct(), product1)));
            }
         }
      }))
      .apply("Convert to ProductInfo", MapElements.via(new SimpleFunction<KV<String, KV<String, Product>>, ProductInfo>() {
         @Override
         public ProductInfo apply(KV<String, KV<String, Product>> input)
         {
            String storeID = input.getKey();
            String searchTerm = input.getValue().getKey();

            Product result = input.getValue().getValue();
            String productName = result.getItem().getProductDescription().getTitle();
//            String productID = result.getTcin();
            String supplier = "Target";
            String brand = "UNKNOWN";
            try {
               brand = result.getItem().getPrimaryBrand().getName();

            } catch (NullPointerException e) {
//               _logger.debug("Unknown brand for product name: {}", result.getItem().getProductDescription().getTitle());
            }
            double price = result.getPrice().getCurrentRetail();

            return new ProductInfo(storeID, supplier, productName, brand, searchTerm, price);
         }
      }))
              .apply("Product stored proc", new JdbcProductInfoWrite());


//      PAssert.that().containsInAnyOrder();


      p.run().waitUntilFinish();
   }
}
