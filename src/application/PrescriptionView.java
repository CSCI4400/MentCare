package application;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PrescriptionView extends Application {

    private TableView prescriptTable = new TableView();
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("Patient Prescription Table");
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);

        final Label tableLabel = new Label("Patient Information");
        tableLabel.setFont(new Font("Times New Roman", 20));

        prescriptTable.setEditable(true);

        TableColumn pNameCol = new TableColumn("Prescription");
        pNameCol.setMinWidth(140);
        /*
        if(pNameCol != null){
            pNameCol.setForeground(Color.Red);
        }
        */
        TableColumn lastDateCol = new TableColumn("Last Prescribed");
        lastDateCol.setMinWidth(100);
        TableColumn pDescriptionCol = new TableColumn("Description");
        pDescriptionCol.setMinWidth(220);

        prescriptTable.getColumns().addAll(pNameCol, lastDateCol, pDescriptionCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 0, 10));
        vbox.getChildren().addAll(tableLabel,prescriptTable);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static class Prescription {

        private final SimpleStringProperty pName;
        private final SimpleStringProperty lastDate;
        private final SimpleStringProperty pDescription;

        private Prescription(String prescriptionName, String lastDateUse, String prescriptionDescription) {
            this.pName = new SimpleStringProperty(prescriptionName);
            this.lastDate = new SimpleStringProperty(lastDateUse);
            this.pDescription = new SimpleStringProperty(prescriptionDescription);
        }

        public String getPrescriptName(){
            return pName.get();
        }

        public void setPrescriptName(String presciptionName){
            pName.set(presciptionName);
        }


    }

}
