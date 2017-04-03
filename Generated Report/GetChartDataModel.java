//change to model during integration
package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class GetChartDataModel {
	
	ArrayList<Integer> chartData;
	
	public GetChartDataModel(){
		this.chartData = getChartData();
	}

	
	public ArrayList<Integer> getChartData(){
		int t0 = 0, t1 = 0, t2 = 0;
		int lw0 = 0, lw1 = 0, lw2 = 0;
		int lm0 = 0, lm1 = 0, lm2 = 0;
		int ly0 = 0, ly1 = 0, ly2 = 0;
		chartData = new ArrayList<Integer>();
		DBConfigModel dbconf = new DBConfigModel();
		Connection conn = dbconf.con;

		//sql
		String query = "select time_update, Danger_lvl from Personal_Info";
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
				String danger_lvl = rs.getString("Danger_lvl");
				int dl = Integer.parseInt(danger_lvl);
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
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} 
	}
}
