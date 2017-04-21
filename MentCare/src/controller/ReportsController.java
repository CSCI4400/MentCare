package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

import application.DBConfig;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DBConnection;

public class ReportsController
{

	Stage stage;
	Scene scene;
	Parent root;
	
	@FXML
	public ResourceBundle resources;
	@FXML
	public URL location;
	@FXML
	public Button dailyButton;
	@FXML
	private Button weeklyButton;
	@FXML
	private Button monthlyButton;
	@FXML
	private Button yearlyButton;
	@FXML
	private TextField tf;
	@FXML
	private Button btn;

	final static String day = "Today";
	final static String week = "Last Week";
	final static String month = "Last Month";
	final static String year = "Last Year";
	
	@FXML
	void backMain (ActionEvent event) throws Exception{
    	Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
		scene = new Scene(root);
		stage.setScene(scene);
		
    }

	public ArrayList<Integer> getChartData(){
		int t0 = 0, t1 = 0, t2 = 0;
		int lw0 = 0, lw1 = 0, lw2 = 0;
		int lm0 = 0, lm1 = 0, lm2 = 0;
		int ly0 = 0, ly1 = 0, ly2 = 0;
		ArrayList<Integer> chartData = new ArrayList<Integer>();
		try{

			//			DBConfig dbconf = new DBConfig();
			//		Connection conn = DBConfig.getConnection();

			Connection conn = DBConfig.getConnection();
			//sql
			String query = "select time_update, threat_level from Personal_Info";
			PreparedStatement sql;
			try {
				sql = conn.prepareStatement(query);
				ResultSet rs = sql.executeQuery();
				//date formatting for time_update
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

				//date formatting for today's date
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				//get today's date in milliseconds
				df.setTimeZone(TimeZone.getTimeZone("UTC"));
				String todays_date_string = df.format(new Date());
				Date todays_date = sdf.parse(todays_date_string);
				long todays_date_milliseconds = todays_date.getTime();

				//loop through records
				while(rs.next()){

					//get unformatted timestamp
					String time_update = rs.getString("time_update");
					String danger_lvl = rs.getString("threat_level");
					
					//Switch danger level to number, used to be based on numbers before database changes.
					//Team 5 - 4/20/2017 #blazeit
					int dl;
					switch(danger_lvl){
					case "low":
						dl = 0;
						break;
					case "medium":
						dl = 1;
						break;
					case "high":
						dl = 2;
						break;
					default:
						dl = 0;
						break;
					}
					
					//convert timestamp to milliseconds
					Date update = sdf.parse(time_update);
					long time_updated_milliseconds = update.getTime();

					//see if update is 1 day or less from today
					if((todays_date_milliseconds - time_updated_milliseconds) <= 86400000L){
						if(dl == 0){
							t0+=1;
						}
						else if(dl == 1){
							t1+=1;
						}
						else{
							t2+=1;
						}
					}

					//see if update is 7 days or less from today
					if(todays_date_milliseconds - time_updated_milliseconds <= 604800000L){
						if(dl == 0){
							lw0+=1;
						}
						else if(dl == 1){
							lw1+=1;
						}
						else{
							lw2+=1;
						}
					}

					//see if update is 30 days or less from today
					if(todays_date_milliseconds - time_updated_milliseconds <= 2592000000L){
						if(dl == 0){
							lm0+=1;
						}
						else if(dl == 1){
							lm1+=1;
						}
						else{
							lm2+=1;
						}
					}

					//see if update is 365 days or less from today
					if(todays_date_milliseconds - time_updated_milliseconds <= 31536000000L){
						if(dl == 0){
							ly0+=1;
						}
						else if(dl == 1){
							ly1+=1;
						}
						else{
							ly2+=1;
						}
					}
				}
				chartData.add(t0);
				chartData.add(lw0);
				chartData.add(lm0);
				chartData.add(ly0);
				chartData.add(t1);
				chartData.add(lw1);
				chartData.add(lm1);
				chartData.add(ly1);
				chartData.add(t2);
				chartData.add(lw2);
				chartData.add(lm2);
				chartData.add(ly2);
				return chartData;

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
	}

	@FXML
	public void initialize()
	{
		assert (this.dailyButton != null) : "fx:id=\"dailyButton\" was not injected: check your FXML file 'reports.fxml'.";
		assert (this.weeklyButton != null) : "fx:id=\"weeklyButton\" was not injected: check your FXML file 'reports.fxml'.";
		assert (this.monthlyButton != null) : "fx:id=\"monthlyButton\" was not injected: check your FXML file 'reports.fxml'.";
		assert (this.yearlyButton != null) : "fx:id=\"yearlyButton\" was not injected: check your FXML file 'reports.fxml'.";
		this.dailyButton.setOnMouseClicked((event) -> {
			Scene scene;
			ArrayList<Integer> chartData = getChartData();
			final CategoryAxis xAxis = new CategoryAxis();
			final NumberAxis yAxis = new NumberAxis();
			final BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);
			bc.setTitle("Mentcare Generated Report");
			xAxis.setLabel("Patient Danger level");
			yAxis.setLabel("Number of Patients Seen");

			XYChart.Series series1 = new XYChart.Series();
			series1.setName("0");
			series1.getData().add(new XYChart.Data(day, chartData.get(0)));
			//				series1.getData().add(new XYChart.Data(week, chartData.get(1)));
			//				series1.getData().add(new XYChart.Data(month, chartData.get(2)));
			//				series1.getData().add(new XYChart.Data(year, chartData.get(3)));

			XYChart.Series series2 = new XYChart.Series();
			series2.setName("1");
			series2.getData().add(new XYChart.Data(day, chartData.get(4)));
			//				series2.getData().add(new XYChart.Data(week, chartData.get(5)));
			//				series2.getData().add(new XYChart.Data(month, chartData.get(6)));
			//				series2.getData().add(new XYChart.Data(year, chartData.get(7)));

			XYChart.Series series3 = new XYChart.Series();
			series3.setName("2");
			series3.getData().add(new XYChart.Data(day, chartData.get(8)));
			//				series3.getData().add(new XYChart.Data(week, chartData.get(9)));
			//				series3.getData().add(new XYChart.Data(month, chartData.get(10)));
			//				series3.getData().add(new XYChart.Data(year, chartData.get(11)));
			scene  = new Scene(bc,500,600);
			bc.getData().addAll(series1, series2, series3);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		});
		this.weeklyButton.setOnMouseClicked((event) -> {
			Scene scene;
			ArrayList<Integer> chartData = getChartData();
			final CategoryAxis xAxis = new CategoryAxis();
			final NumberAxis yAxis = new NumberAxis();
			final BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);
			bc.setTitle("Mentcare Generated Report");
			xAxis.setLabel("Patient Danger level");
			yAxis.setLabel("Number of Patients Seen");

			XYChart.Series series1 = new XYChart.Series();
			series1.setName("0");
			series1.getData().add(new XYChart.Data(week, chartData.get(1)));
			XYChart.Series series2 = new XYChart.Series();
			series2.setName("1");
			series2.getData().add(new XYChart.Data(week, chartData.get(5)));
			XYChart.Series series3 = new XYChart.Series();
			series3.setName("2");
			series3.getData().add(new XYChart.Data(week, chartData.get(9)));
			scene  = new Scene(bc,500,600);
			bc.getData().addAll(series1, series2, series3);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		});
		this.monthlyButton.setOnMouseClicked((event) -> {
			Scene scene;
			ArrayList<Integer> chartData = getChartData();
			final CategoryAxis xAxis = new CategoryAxis();
			final NumberAxis yAxis = new NumberAxis();
			final BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);
			bc.setTitle("Mentcare Generated Report");
			xAxis.setLabel("Patient Danger level");
			yAxis.setLabel("Number of Patients Seen");

			XYChart.Series series1 = new XYChart.Series();
			series1.setName("0");
			series1.getData().add(new XYChart.Data(month, chartData.get(2)));
			XYChart.Series series2 = new XYChart.Series();
			series2.setName("1");
			series2.getData().add(new XYChart.Data(month, chartData.get(6)));
			XYChart.Series series3 = new XYChart.Series();
			series3.setName("2");
			series3.getData().add(new XYChart.Data(month, chartData.get(10)));
			scene  = new Scene(bc,500,600);
			bc.getData().addAll(series1, series2, series3);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		});
		this.yearlyButton.setOnMouseClicked((event) -> {
			Scene scene;
			ArrayList<Integer> chartData = getChartData();
			final CategoryAxis xAxis = new CategoryAxis();
			final NumberAxis yAxis = new NumberAxis();
			final BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);
			bc.setTitle("Mentcare Generated Report");
			xAxis.setLabel("Patient Danger level");
			yAxis.setLabel("Number of Patients Seen");

			XYChart.Series series1 = new XYChart.Series();
			series1.setName("0");
			series1.getData().add(new XYChart.Data(year, chartData.get(3)));

			XYChart.Series series2 = new XYChart.Series();
			series2.setName("1");
			series2.getData().add(new XYChart.Data(year, chartData.get(7)));

			XYChart.Series series3 = new XYChart.Series();
			series3.setName("2");
			series3.getData().add(new XYChart.Data(year, chartData.get(11)));
			scene  = new Scene(bc,500,600);
			bc.getData().addAll(series1, series2, series3);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		});
	}
}
