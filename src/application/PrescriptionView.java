package application;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.*;

public class PrescriptionView extends Application {

    private TableView<Prescription> prescriptTable = new TableView<Prescription>();

    public static void main(String[] args){
        launch(args);
    }

    //borrowed from Charts.java
    public Connection connectDB(){
        try{
            String dbString = "jdbc:mysql://164.132.49.5:3306/mentcare";
            String user = "mentcare";
            String password = "mentcare1";

            //connections
            Connection conn = (Connection) DriverManager.getConnection(dbString, user, password);
            return conn;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void getTableData() throws SQLException {

        Statement stmt = null;
        String query = "SELECT id, Prescription, Date FROM Prescription_History";
        Connection conn = connectDB();

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                ObservableList<Prescription> data = FXCollections.observableArrayList(new Prescription("id"))
            }


        } catch (SQLException e){

        }


    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("prescriptionTable.fxml"));
        Scene scene= new Scene(root, 600, 300);
        primaryStage.setTitle("Patient Prescription Table");
        primaryStage.setScene(scene);


        final Label tableLabel = new Label("Patient Information");
        tableLabel.setFont(new Font("Times New Roman", 20));

        prescriptTable.setEditable(true);

        TableColumn pNameCol = new TableColumn("Prescription");
        pNameCol.setMinWidth(140);
        TableColumn lastDateCol = new TableColumn("Last Prescribed");
        lastDateCol.setMinWidth(100);
        TableColumn pDescriptionCol = new TableColumn("Description");
        pDescriptionCol.setMinWidth(220);

        prescriptTable.getColumns().addAll(pNameCol, lastDateCol, pDescriptionCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 0, 10));
        vbox.getChildren().addAll(tableLabel,prescriptTable);

        //((Group) scene.getRoot()).getChildren().addAll(vbox);
        //scene.getRoot()).getChildren().addAll(vbox);

        //primaryStage.setScene(scene);

        primaryStage.show();

    }

    public static class Prescription {

        private final SimpleStringProperty pID;
        private final SimpleStringProperty pName;
        private final SimpleStringProperty lastDate;

        private Prescription(String prescriptionID, String prescriptionName, String lastDateUse) {
            this.pID = new SimpleStringProperty(prescriptionID);
            this.pName = new SimpleStringProperty(prescriptionName);
            this.lastDate = new SimpleStringProperty(lastDateUse);
        }

        public String getPrescriptName(){
            return pName.get();
        }

        public void setPrescriptName(String presciptionName){
            pName.set(presciptionName);
        }


    }

}
