package wallgarden;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.Scanner;
import javafx.collections.ObservableList;

//Anna Leheta and Andreea Sandru

public class Storage
{
   private static final String PATH_BASE = "images/";

   //function for printing to the file
   public static void printToFile (String fileName, ObservableList<Plant> chosenPlants, Area area)
   {
      String name = "Name:";
      String price = "Price per each:";
      String quantity = "# of items:";
      String totalPerEach = "Price for all:";
      String total = "Total price";
      String systemPrice = "Price of the equipment:";
      double totalPrice = 0;
      try {

         PrintWriter printFile = new PrintWriter(new BufferedWriter(new FileWriter(fileName + ".txt")));
         printFile.format("%-20s%-20s%-20s%-20s%n\n", name, price, quantity, totalPerEach);
         printFile.format("%n---------------------------------------------------------------------------%n");
         for (Plant plant : chosenPlants) {
            totalPrice += plant.pricePerQuantity();
            printFile.format(plant.toString());
         }
         totalPrice += area.calculateSystemPrice();
         printFile.format("%n---------------------------------------------------------------------------%n");
         printFile.format("%-60s%-20s%n", systemPrice, String.valueOf(area.calculateSystemPrice()));
         printFile.format("%-60s%-20s%n", total, String.valueOf(totalPrice));

         printFile.close();

      }
      catch (IOException ioe) {
         System.out.println(ioe.getMessage());
      }

   }

   //function for extracting info from the file
   public static void readRecords (String path, ObservableList<Plant> plants)
   {

      try {
         Path p = Paths.get(path);
         if (Files.exists(p)) {
            Scanner scan = new Scanner(p);
            scan.useDelimiter("~");
            while (scan.hasNext()) {
               int iD = scan.nextInt();
               String name = scan.next().trim();
               String description = scan.next().trim();
               String price = scan.next().trim().replace('$', '\0');
               Price prce = Price.getByValue(Double.parseDouble(price));
               Plant plant = new Plant(iD, name, prce, description, String.format("%s%02d.JPG", PATH_BASE, iD));
               plants.add(plant);
            }
         }
      }
      catch (Exception ex) {
         System.out.println(ex.getMessage());
      }
   }

}
