package wallgarden;
import java.util.HashMap;
import java.util.Map;
public enum Price
//Anna Leheta and Andreea Sandru
{
   FOLIAGE(14.99), FLOWERING(17.99), VEGETABLE(15.99), SUCCELENT(18.99), HERB(13.99);

   private double price;

   private static final Map<Double, Price> MAP = new HashMap<Double, Price>();

   static {
      for (Price price : values()) {
         MAP.put(price.getPrice(), price);
      }
   }

   private Price (double value)
   {
      this.price = value;
   }

   public double getPrice ()
   {
      return price;
   }

   public static Price getByValue (double value)
   {
      return MAP.get(value);
   }
}
