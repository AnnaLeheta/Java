package wallgarden;
/**
 * Anna Leheta and Andreea Sandru
 */
public class Area
{
   private double width;
   private double length;
   private double area;

   public Area (double width, double length) throws AreaException
   {
      if (width > 0) {
         this.width = width;
      }
      else {
         throw new AreaException("Wrong width value");
      }
      if (length > 0) {
         this.length = length;
      }
      else {
         throw new AreaException("Wrong length value");
      }
      this.area = width * length;
   }

   public double getWidth ()
   {
      return width;
   }

   public void setWidth (double width) throws AreaException
   {
      if (width > 0) {
         this.width = width;
      }
      else {
         throw new AreaException("Wrong width value");
      }
   }

   public double getLength ()
   {
      return length;
   }

   public void setLength (double length) throws AreaException
   {
      if (length > 0) {
         this.length = length;
      }
      else {
         throw new AreaException("Wrong length value");
      }
   }

   public double getArea ()
   {
      return area;
   }

   public int calculateNumPlants ()
   {
      int numPlants = (int) (this.area * 4) * 2;

      return numPlants;
   }

   public double calculateSystemPrice ()
   {
      int bracket = (int) this.area;
      double systemPrice = 0;
      switch (bracket) {
         case 0:
            systemPrice = 200.00;
            break;
         case 1:
            systemPrice = 250.00;
            break;
         case 2:
            systemPrice = 300.00;
            break;
         case 3:
            systemPrice = 350.00;
            break;
         case 4:
            systemPrice = 400.00;
            break;
         case 5:
            systemPrice = 450.00;
            break;
         case 6:
            systemPrice = 500.00;
            break;
         case 7:
            systemPrice = 550.00;
            break;
         case 8:
            systemPrice = 600.00;
            break;
         case 9:
            systemPrice = 650.00;
            break;
         case 10:
            systemPrice = 700.00;
            break;
         case 11:
            systemPrice = 700.00;
            break;
         case 12:
            systemPrice = 700.00;
            break;
         default:
            systemPrice = 800.00;
      }
      return systemPrice;
   }


}
