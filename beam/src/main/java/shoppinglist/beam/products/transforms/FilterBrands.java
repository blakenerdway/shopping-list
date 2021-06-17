package shoppinglist.beam.products.transforms;

import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.beam.products.pojos.targetjson.Product;

/**
 * Take in a PCollection KV String, Product and find the brands associated with the product results.
 * @author bordway@ihmc.us 6/17/2021
 */
public class FilterBrands extends SimpleFunction<KV<String, Product>, String> {
   public static FilterBrands create() {
      return new FilterBrands();
   }

   @Override
   public String apply(KV<String, Product> input)
   {
      String brand = "UNKNOWN";
      try {
         brand = input.getValue().getItem().getPrimaryBrand().getName();

      } catch (NullPointerException e) {
         _logger.debug("Unknown brand for product name: {}", input.getValue().getItem().getProductDescription().getTitle());
      }
      return brand;

   }

   private static final Logger _logger = LoggerFactory.getLogger(FilterBrands.class);
}
