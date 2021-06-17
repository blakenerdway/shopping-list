package shoppinglist.beam.products.transforms;

import jdk.nashorn.internal.objects.Global;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.transforms.windowing.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.beam.products.pojos.targetjson.Product;

import javax.annotation.Nullable;

/**
 * Take in a PCollection KV String, Product and find the brands associated with the product results.
 * @author bordway@ihmc.us 6/17/2021
 */
public class DistinctBrands extends PTransform<PCollection<KV<String, Product>>, PCollection<String>> {
   public static DistinctBrands create() {
      return new DistinctBrands();
   }

   @Override
   public PCollection<String> expand(PCollection<KV<String, Product>> in)
   {
      return in.apply("Key by brands", MapElements.via(new SimpleFunction<KV<String, Product>, KV<String, Void>>() {
         @Override
         public KV<String, Void> apply(KV<String, Product> input)
         {
            String brand = null;
            try{
               brand = input.getValue().getItem().getPrimaryBrand().getName();
               return KV.of(brand, null);
            }
            catch(NullPointerException e){

            }
            return null;
         }
      }))
              .apply("DropValues", Combine.perKey(new SerializableFunction<Iterable<Void>, Void>() {
                 @Override
                 @Nullable
                 public Void apply(Iterable<Void> iter)
                 {
                    return null; // ignore input
                 }
              }))
              .apply("ExtractFirstKey", ParDo.of(new DoFn<KV<String, Void>, String>() {
                 @ProcessElement
                 public void processElement(@Element KV<String, Void> element, PaneInfo pane, OutputReceiver<String> receiver)
                 {
                    if (pane.isFirst()) {
                       // Only output the key if it's the first time it's been seen.
                       receiver.output(element.getKey());
                    }
                 }
              }));
   }
   private static final Logger _logger = LoggerFactory.getLogger(DistinctBrands.class);
}
