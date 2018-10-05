/*
 * Anna Leheta and Andreea Sandru
 */
package wallgarden;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * Anna Leheta Adnreea Sandru
 */
public class FXMLDocumentController implements Initializable
{
   //String path to read plants from the file
   private final String DB_FILE = "src\\wallgarden\\PlantsDatabase.txt";

   //button on the first page
   @FXML
   private Button firstNext;
   @FXML
   private AnchorPane firstPage;
   @FXML
   private TabPane tabPane;

   //function for all the "next" buttons
   @FXML
   private void handleButtonNext (ActionEvent event)
   {
      if (tabPane.getSelectionModel().getSelectedIndex() == 2) {
         addPlantsToEstimate();
         total.clear();
         calculateTotal();
         total.appendText(String.format("%.2f", subtotal));
      }
      tabPane.getSelectionModel().selectNext();
   }

   //code for the second page
   @FXML
   private AnchorPane secondPage;
   @FXML
   private TextField fieldWidth;
   @FXML
   private TextField fieldLehgth;
   double width;
   double length;
   @FXML
   private TextArea areaOutput;
   @FXML
   private Button calculate;
   private Area userArea;
   @FXML
   private Button secondNext;

   //code for the second page calculations
   @FXML
   private void handleButtonAction (MouseEvent event)
   {
      try {
         String value = fieldWidth.getText();
         System.out.println(value);
         width = Double.valueOf(fieldWidth.getText());
         length = Double.valueOf(fieldLehgth.getText());
         userArea = new Area(width, length);
         areaOutput.clear();
         double systemPrice = userArea.calculateSystemPrice();
         areaOutput.appendText(String.format("\n\nYour dimensions: \n"
                 + width + " in width, \n"
                 + length + " in length. \n"
                 + "Total area is %.2f m."
                 + "\n"
                 + "Price of a equipment for this area is approximately  "
                 + systemPrice
                 + "\nFor the best result you need approximately %d plants.",
                                             userArea.getArea(),
                                             userArea.calculateNumPlants()));
         systemPriceOutput.clear();
         systemPriceOutput.appendText(String.format("%.2f", systemPrice));
         total.clear();
         calculateTotal();
         total.appendText(String.format("%.2f", subtotal));
      }
      catch (Exception e) {
         System.out.println(e.getMessage());
         areaOutput.clear();
         systemPriceOutput.clear();
         total.clear();
         calculateTotal();
         total.appendText(String.format("%.2f", subtotal));
         areaOutput.appendText("Wrong parameters,\n"
                 + " please enter a correct dimensions.");
      }
   }

   //code for the third page
   @FXML
   private AnchorPane thirdPage;
   @FXML
   private Button thirdNext;
   @FXML
   private TextField txtSearch;
   @FXML
   private TableView<Plant> plantsTableView;
   @FXML
   private TableColumn<Plant, ImageView> colPicture;
   @FXML
   private TableColumn<Plant, String> colName;
   @FXML
   private TableColumn<Plant, String> colPrice;
   @FXML
   private TableColumn<Plant, Spinner> colQuantity;
   @FXML
   private TableColumn<Plant, String> colDescription;
//   @FXML
//   private TableColumn<Plant, ToggleButton> colSelect;


   private ObservableList<Plant> plantsData;

   //read from the file all the data about plants
   private void readData ()
   {
      plantsData = FXCollections.observableArrayList();
      Storage.readRecords(this.DB_FILE, this.plantsData);
   }

   //to populate a table on the third page of the app
   private void initPlantsTable ()
   {
      readData();

      this.plantsTableView.setItems(plantsData);

      this.colName.setCellValueFactory(new PropertyValueFactory("name"));
      this.colPrice.setCellValueFactory(new PropertyValueFactory("price"));
      this.colQuantity.setCellValueFactory(new PropertyValueFactory("quantitySpinner"));
      this.colDescription.setCellValueFactory(new PropertyValueFactory("description"));
      this.colPicture.setCellValueFactory(new PropertyValueFactory("image"));
//      this.colSelect.setCellValueFactory(new PropertyValueFactory("selectToggle"));
      this.plantsTableView.getColumns().forEach((col) -> {
         col.setStyle("-fx-alignment: CENTER;");
      });

      //code for filtering table on the third page items by name and sorting it 
      FilteredList<Plant> filteredList = new FilteredList<Plant>(plantsData, p -> true);

      txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
         filteredList.setPredicate(plant -> {
            if (newValue == null || newValue.isEmpty()) {
               return true;
            }

            String lowerCaseValue = newValue.toLowerCase();
            if (plant.getName().toLowerCase().contains(lowerCaseValue)) {
               return true;
            }
            return false;
         });
      });

      SortedList<Plant> sortedList = new SortedList<Plant>(filteredList);
      sortedList.comparatorProperty().bind(plantsTableView.comparatorProperty());

      plantsTableView.setItems(sortedList);
   }
   //code for the last page
   private ObservableList<Plant> estimatePlantsData;
   @FXML
   private Button print;
   @FXML
   private TextArea systemPriceOutput;
   @FXML
   private TextArea total;
   @FXML
   private TableView<Plant> plantsEstimate;
   @FXML
   private TableColumn<Plant, ImageView> colEstPicture;
   @FXML
   private TableColumn<Plant, String> colEstName;
   @FXML
   private TableColumn<Plant, String> colEstPrice;
   @FXML
   private TableColumn<Plant, String> colEstQuantity;
   @FXML
   private TableColumn<Plant, String> colTotal;

   //function for adding selected items to the table on fourth page
   private void addPlantsToEstimate ()
   {
      if (estimatePlantsData == null) {
         estimatePlantsData = FXCollections.observableArrayList();
      }
      estimatePlantsData.clear();
      for (Plant plant : plantsData) {
         if (plant.getQuantity() > 0) {
            Plant p = plant.copyPlant();
            estimatePlantsData.add(p);
         }
      }
   }

   //function for populating the table on the last page
   private void initEstimateTable ()
   {
      addPlantsToEstimate();

      this.plantsEstimate.setItems(estimatePlantsData);

      this.colEstPicture.setCellValueFactory(new PropertyValueFactory("image"));
      this.colEstName.setCellValueFactory(new PropertyValueFactory("name"));
      this.colEstPrice.setCellValueFactory(new PropertyValueFactory("price"));
      this.colEstQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));
      this.colTotal.setCellValueFactory(new PropertyValueFactory("total"));
      this.plantsEstimate.getColumns().forEach((col) -> {
         col.setStyle("-fx-alignment: CENTER;");
      });
   }
   //the name of the file for printing
   String printFileName = "myEstimate";

   //function to print to file
   @FXML
   private void printToFileButton (MouseEvent event)
   {
      Storage.printToFile(printFileName, estimatePlantsData, userArea);
   }
   double subtotal = 0;

   //function to calculate the total
   @FXML
   private void calculateTotal ()
   {
      subtotal = 0;
      for (Plant plant : estimatePlantsData) {
         subtotal += plant.pricePerQuantity();
      }
      if (userArea != null) {
         subtotal += userArea.calculateSystemPrice();
      }
//      else{
//         
//      }
   }

   @Override
   public void initialize (URL url, ResourceBundle rb)
   {
      initPlantsTable();

      calculate.setOnMouseClicked(this::handleButtonAction);

      initEstimateTable();

      print.setOnMouseClicked(this::printToFileButton);


   }



}
