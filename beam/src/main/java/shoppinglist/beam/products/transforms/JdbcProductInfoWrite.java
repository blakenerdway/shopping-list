package shoppinglist.beam.products.transforms;

import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shoppinglist.beam.products.pojos.products.ProductInfo;

public class JdbcProductInfoWrite extends PTransform<PCollection<ProductInfo>, PDone> {
   @Override
   public PDone expand(PCollection<ProductInfo> input)
   {
      return input.apply("Product stored proc", JdbcIO.<ProductInfo>write()
              .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                      "com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/shopping_list")
                      .withUsername("root")
                      .withPassword("password!"))
              // Insert into table only if the brand doesn't exist already
              .withStatement("Call insert_productinfo(?, ?, ?, ?, ?, ?)")
              .withPreparedStatementSetter((JdbcIO.PreparedStatementSetter<ProductInfo>) (element, query) -> {
                 String storeID = element.getStoreID();
                 String searchTerm = element.getSearchTerm();
                 String productName = element.getProductName();
                 String supplier = element.getSupplier();
                 String brand = element.getBrand();
                 double price = element.getPrice();

                 query.setString(1, searchTerm);
                 query.setInt(2, Integer.parseInt(storeID));
                 query.setString(3, productName);
                 query.setString(4, brand);
                 query.setString(5, supplier);
                 query.setFloat(6, (float)price);

              }));
   }

   private static final Logger _logger = LoggerFactory.getLogger(JdbcProductInfoWrite.class);
}
