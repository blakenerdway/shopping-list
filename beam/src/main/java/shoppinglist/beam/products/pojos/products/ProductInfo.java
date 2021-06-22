package shoppinglist.beam.products.pojos.products;

import org.apache.beam.sdk.schemas.JavaBeanSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

@DefaultSchema(JavaBeanSchema.class)
public class ProductInfo {
   @Override
   public String toString () {
      return "ProductInfo{" +
              "storeID='" + _storeID + '\'' +
              ", supplier='" + _supplier + '\'' +
              ", productName='" + _productName + '\'' +
              ", brand='" + _brand + '\'' +
              ", searchTerm='" + _searchTerm + '\'' +
              ", price=" + _price +
              '}';
   }

   private String _storeID;
   private String _supplier;
   private String _productName;
   private String _brand;
   private String _searchTerm;
   private double _price;

   public ProductInfo(String storeID, String supplier, String productName, String brand, String searchTerm, double price)
   {

      _storeID = storeID;
      _supplier = supplier;
      _productName = productName;
      _brand = brand;
      _searchTerm = searchTerm;
      _price = price;
   }

   public double getPrice()
   {
      return _price;
   }

   public void setPrice(double price)
   {
      _price = price;
   }

   public String getStoreID()
   {
      return _storeID;
   }

   public void setStoreID(String storeID)
   {
      _storeID = storeID;
   }

   public String getSupplier()
   {
      return _supplier;
   }

   public void setSupplier(String supplier)
   {
      _supplier = supplier;
   }

   public String getProductName()
   {
      return _productName;
   }

   public void setProductName(String productName)
   {
      _productName = productName;
   }

   public String getBrand()
   {
      return _brand;
   }

   public void setBrand(String brand)
   {
      _brand = brand;
   }

   public String getSearchTerm()
   {
      return _searchTerm;
   }

   public void setSearchTerm(String searchTerm)
   {
      _searchTerm = searchTerm;
   }
}
