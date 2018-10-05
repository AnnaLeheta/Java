package wallgarden;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Anna Leheta and Andreea Sandru
 */
public class Plant
{
   private String name;
   private int id;
   private String description;
   private int quantity;
   private double price;
   private ImageView image;
   private Button moreBtn;
   private Spinner<Integer> quantitySpinner;
   private ToggleButton selectToggle;
   private double total;
   private String imagePath;


   private final int INITIAL_QUANTITY = 0, MIN_QUANTITY = 0;
   private final int MAX_QUANTITY = 30;

   //constructor for the plant
   public Plant (int iD, String name, Price price, String description, String imgPath)
   {
      this.name = name;
      this.id = iD;
      this.description = description;
      this.quantity = 0;
      this.price = price.getPrice();
      this.image = new ImageView(new Image(getClass().getResource(imgPath).toExternalForm()));
      this.image.setFitHeight(60);
      this.image.setFitWidth(60);
      this.imagePath = imgPath;
      this.moreBtn = new Button("More");
      this.quantitySpinner = new Spinner<Integer>();
      this.selectToggle = new ToggleButton("Select");
      // Value factory
      SpinnerValueFactory<Integer> valueFactory
              = //
              new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_QUANTITY, MAX_QUANTITY, INITIAL_QUANTITY);

      this.quantitySpinner.setValueFactory(valueFactory);

      this.moreBtn.setOnAction((event) -> {
         System.out.println(event);
      });

      this.selectToggle.setOnAction((event) -> {

      });
      this.quantitySpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
         if (newVal != 0) {
            this.quantity = (int) newVal;
            if (total > 0) {
               getTotal();
            }
         }

      });
   }

   //we needed to create a copy of the plants for the last table,
   //so the two tables do not share the same objects
   public Plant copyPlant ()
   {
      Plant newPlant
              = new Plant(
                      this.getId(),
                      this.getName(),
                      Price.getByValue(this.getPrice()),
                      this.getDescription(),
                      this.getImagePath()
              );
      newPlant.quantity = this.quantity;
      newPlant.description = this.description;
      SpinnerValueFactory<Integer> valueFactory
              = new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_QUANTITY, MAX_QUANTITY, this.quantity);

      newPlant.quantitySpinner.setValueFactory(valueFactory);

      return newPlant;
   }

   public String getImagePath ()
   {
      return this.imagePath;
   }

   public void setImagePath (String imgPath)
   {
      this.imagePath = imgPath;
   }

   public double getTotal ()
   {
      return total = pricePerQuantity();
   }

   public ToggleButton getSelectToggle ()
   {
      return selectToggle;
   }

   public void setSelectToggle (ToggleButton selectToggle)
   {
      this.selectToggle = selectToggle;
   }

   public Button getMoreBtn ()
   {
      return moreBtn;
   }

   public void setMoreBtn (Button moreBtn)
   {
      this.moreBtn = moreBtn;
   }

   public Spinner getQuantitySpinner ()
   {
      return quantitySpinner;
   }

   public void setQuantitySpinner (Spinner quantitySpinner)
   {
      this.quantitySpinner = quantitySpinner;
   }

   public int getId ()
   {
      return id;
   }

   public double getPrice ()
   {
      return price;
   }

   public String getName ()
   {
      return name;
   }

   public void setName (String name)
   {
      this.name = name;
   }

   public String getDescription ()
   {
      return description;
   }

   public void setDescription (String description)
   {
      this.description = description;
   }

   public int getQuantity ()
   {
      return quantity;
   }

   public void setQuantity (int quantity)
   {
      this.quantity = quantity;
   }

   public double pricePerQuantity ()
   {
      //rounding the price per quantity to 2 decimal places
      return Math.round((this.quantity * this.price) * 100.0) / 100.0;
   }


   public ImageView getImage ()
   {
      return image;
   }

   public void setImage (ImageView image)
   {
      this.image = image;
   }

   @Override
   public String toString ()
   {
      String line = String.format("%-20s%-20s%-20s%-20s%n", this.name,
                                  String.valueOf(this.price),
                                  String.valueOf(this.quantity),
                                  String.valueOf(pricePerQuantity())
      );
      return line;
   }
}
