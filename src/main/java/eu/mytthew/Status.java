package eu.mytthew;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Status {
	public static void main(String[] args) {
		List<StatusInfo> statusInfoList = new ArrayList<>();
		Timer timer = new Timer();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					URL url = new URL("https://www.engineowning.com/shop/ajax/get-product-status?");
					BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
					String line = reader.readLine();
					JSONObject jsonObject = new JSONObject(line);
					JSONArray jsonArray = jsonObject.getJSONObject("content").getJSONArray("content");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.getJSONObject(i);
						StatusInfo statusInfo = new StatusInfo(object.getString("name"), object.getString("status"));
						statusInfoList.add(statusInfo);
					}
					boolean result = statusInfoList.stream()
							.filter(obj -> obj.name.equals("EngineOwning for CoD MW 2019"))
							.map(obj -> obj.status.equals("1"))
							.findFirst()
							.orElse(false);
					if (result) {
						System.out.println(LocalTime.now().format(formatter) + " | Status: Undetected");
					} else {
						System.out.println(LocalTime.now().format(formatter) + " | Status: Updating");
					}
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 60000);
	}
}


