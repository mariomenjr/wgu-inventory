package home;

import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.OutSourced;
import models.Product;

public class Main extends Application {

    private static Stage homeStage;

    private static Inventory inventory;
    // private static ArrayList<Product> dbProducts = new ArrayList();
    // private static ArrayList<InHouse> dbPartsInHouse = new ArrayList();
    // private static ArrayList<OutSourced> dbPartsOutSource = new ArrayList();

    public static final String PATH_PRODUCT = "../data/products.db";
    public static final String PATH_PART_INHOUSE = "../data/parts.inhouse.db";
    public static final String PATH_PART_OUTSOURCED = "../data/parts.outsourced.db";

    @Override
    public void start(final Stage primaryStage) throws Exception {
        final Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));

        homeStage = primaryStage;

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static Stage getPrimeryStage() {
        return homeStage;
    }

    public static void loadData(String filePath) {
        try {

            URL url = Main.class.getResource(filePath);

            FileInputStream iS = new FileInputStream(url.getPath());
            BufferedReader bR = new BufferedReader(new InputStreamReader(iS));

            String line;
            while ((line = bR.readLine()) != null) {
                System.out.println(new String(line));
                String[] split = line.split(",");

                switch (filePath) {
                    case PATH_PRODUCT:
                        Product prod = new Product(Integer.parseInt(split[0]), split[1], Double.parseDouble(split[2]),
                                Integer.parseInt(split[3]), Integer.parseInt(split[4]), Integer.parseInt(split[5]));

                        inventory.addProduct(prod);
                        break;

                    case PATH_PART_INHOUSE:
                        InHouse inh = new InHouse(Integer.parseInt(split[0]), split[1], Double.parseDouble(split[2]),
                                Integer.parseInt(split[3]), Integer.parseInt(split[4]), Integer.parseInt(split[5]));
                        inh.setMachineId(Integer.parseInt(split[6]));

                        inventory.addPart(inh);
                        break;

                    case PATH_PART_OUTSOURCED:
                        OutSourced outs = new OutSourced(Integer.parseInt(split[0]), split[1],
                                Double.parseDouble(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4]),
                                Integer.parseInt(split[5]));
                        outs.setCompanyName(split[6]);

                        inventory.addPart(outs);
                        break;

                    default:
                        break;
                }
            }

            iS.close();
            bR.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File could not be read");
        } catch (IOException ex) {
            System.out.println("Error while loading File");
        } catch (Exception ex) {
            System.out.println("Something went wrong");
        }
    }

    public static void main(final String[] args) {
        inventory = new Inventory();
        // loadData(PATH_PRODUCT);
        // loadData(PATH_PART_INHOUSE);
        // loadData(PATH_PART_OUTSOURCED);

        launch(args);
    }
}
