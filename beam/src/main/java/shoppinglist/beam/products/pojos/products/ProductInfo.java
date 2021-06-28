package shoppinglist.beam.products.pojos.products;

import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.util.Objects;

@DefaultSchema(JavaBeanSchema.class)
public class ProductInfo {
   @Override
   public String toString () {
      return "ProductInfo{" +
              "storeID='" + storeID + '\'' +
              ", supplier='" + supplier + '\'' +
              ", productName='" + productName + '\'' +
              ", brand='" + brand + '\'' +
              ", searchTerm='" + searchTerm + '\'' +
              ", price=" + price +
              '}';
   }

   @Override
   public boolean equals (Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ProductInfo that = (ProductInfo) o;
      return Double.compare(that.price, price) == 0 &&
              Objects.equals(storeID, that.storeID) &&
              Objects.equals(supplier, that.supplier) &&
              Objects.equals(productName, that.productName) &&
              Objects.equals(brand, that.brand) &&
              Objects.equals(searchTerm, that.searchTerm);
   }

   @Override
   public int hashCode () {
      return Objects.hash(storeID, supplier, productName, brand, searchTerm, price);
   }

   private String storeID = "";
   private String supplier = "";
   private String productName = "";
   private String brand = "";
   private String searchTerm = "";
   private double price = -1.0;
   public ProductInfo(){}
   public ProductInfo(String storeID, String supplier, String productName, String brand, String searchTerm, double price)
   {

      this.storeID = storeID;
      this.supplier = supplier;
      this.productName = productName;
      this.brand = brand;
      this.searchTerm = searchTerm;
      this.price = price;
   }

   public double getPrice()
   {
      return price;
   }

   public void setPrice(double price)
   {
      this.price = price;
   }

   public String getStoreID()
   {
      return storeID;
   }

   public void setStoreID(String storeID)
   {
      this.storeID = storeID;
   }

   public String getSupplier()
   {
      return supplier;
   }

   public void setSupplier(String supplier)
   {
      this.supplier = supplier;
   }

   public String getProductName()
   {
      return productName;
   }

   public void setProductName(String productName)
   {
      this.productName = productName;
   }

   public String getBrand()
   {
      return brand;
   }

   public void setBrand(String brand)
   {
      this.brand = brand;
   }

   public String getSearchTerm()
   {
      return searchTerm;
   }

   public void setSearchTerm(String searchTerm)
   {
      this.searchTerm = searchTerm;
   }
}
