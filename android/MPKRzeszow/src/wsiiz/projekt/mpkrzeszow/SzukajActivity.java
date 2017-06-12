package wsiiz.projekt.mpkrzeszow;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wsiiz.projekt.mpkrzeszow.others.Bus;
import wsiiz.projekt.mpkrzeszow.others.JSONParser;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SzukajActivity extends FragmentActivity implements OnItemSelectedListener {
	
	private GoogleMap map;
	private Spinner spinnerBuses;
	private List<Bus> listBus = new ArrayList<Bus>();
	Timer timer;
	TimerTask update;
	
	private static final String URL_BUS_DETAILS = "http://patrycjusz.mydevil.net/mpk/get_bus_details.php";
	private static final String URL_BUSES_LIST = "http://patrycjusz.mydevil.net/mpk/get_all_buses.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_AUTOBUS = "bus";
	private static final String TAG_AUTOBUS_TAB = "buses";
	private static final String TAG_ID = "id";
	private static final String TAG_LINIA = "linia";
	private static final String TAG_KURS = "kurs";
	private static final String TAG_LAT = "lat";
	private static final String TAG_LNG = "lng";
	private static final String TAG_SPEED = "speed";
	private static final String TAG_UPDATED_AT = "updated_at";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szukaj);
        
        //Odczytanie ustawień mapy z pliku:
    	SharedPreferences sharedprefs = PreferenceManager.getDefaultSharedPreferences(this);
    	String mapPref = sharedprefs.getString("mapType", "normal");
        
        spinnerBuses = (Spinner) findViewById(R.id.spinnerBuses);
        spinnerBuses.setOnItemSelectedListener(this);
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapBuses)).getMap();
        
        //Ustawienie odpowiedniego rodzaju mapu:
		if(mapPref.equals("normal")) {
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		} else if(mapPref.equals("hybrid")) map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		else if(mapPref.equals("satellite")) map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		else if(mapPref.equals("terrain")) map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		map.setInfoWindowAdapter(new BusInfoWindowAdapter());
		//ustawienie mapy na Rzeszow
		CameraUpdate updateCamera = CameraUpdateFactory.newLatLngZoom(new LatLng(50.035974, 22.005157), 12);
		map.animateCamera(updateCamera);
		map.setMyLocationEnabled(false);
		
		new GetAllBuses().execute();
		
		timer = new Timer();
		update = new TimerTask() {
		    @Override
		    public void run() {
		    	Bus bus = (Bus) spinnerBuses.getSelectedItem();
				
				if(!bus.getId().equalsIgnoreCase("init"))
					new GetBusDetails().execute(bus.getId());
		    }
		};
    }
    
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
			long arg3) {
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {	
	}
    
    class GetAllBuses extends AsyncTask<Void, Void, String> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected String doInBackground(Void... params) {

			int success;
			try {
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				JSONParser jsonParser = new JSONParser();
				JSONObject json = jsonParser.makeHttpRequest(
						URL_BUSES_LIST, "GET", params1);

				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					JSONArray autobusObj = json.getJSONArray(TAG_AUTOBUS_TAB);

					listBus.add(new Bus("INIT",getResources().getString(R.string.spinner_default),""));
					
					for(int i = 0; i < autobusObj.length(); i++) {
						JSONObject autobus = autobusObj.getJSONObject(i);

						listBus.add(new Bus(autobus.getString(TAG_ID), autobus.getString(TAG_LINIA),
								autobus.getString(TAG_KURS)));
					}
					
				} else {
					finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			
			if (listBus.size() > 0) {
				ArrayAdapter<Bus> spinnerArrayAdapter = new ArrayAdapter<Bus>(
						SzukajActivity.this,
						android.R.layout.simple_spinner_dropdown_item,
						listBus.toArray(new Bus[listBus.size()]));

				spinnerBuses.setAdapter(spinnerArrayAdapter);
				
				timer.scheduleAtFixedRate(update, 0, 2000);
			}
		}
	}
    
    class GetBusDetails extends AsyncTask<String, Void, Void> {

		private double DLAT, DLNG;
		private MarkerOptions mo;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected Void doInBackground(String... params) {
			
			String id = params[0];
			int success;
			try {
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("id", id));
				JSONParser jsonParser = new JSONParser();
				JSONObject json = jsonParser.makeHttpRequest(
						URL_BUS_DETAILS, "GET", params1);

				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					JSONArray autobusObj = json.getJSONArray(TAG_AUTOBUS);

					JSONObject autobus = autobusObj.getJSONObject(0);
					
					DLAT = Double.parseDouble(autobus.getString(TAG_LAT));
					DLNG = Double.parseDouble(autobus.getString(TAG_LNG));
					
					mo = new MarkerOptions();
					mo.position(new LatLng(DLAT, DLNG));
					mo.title(autobus.getString(TAG_SPEED));
					mo.snippet(autobus.getString(TAG_UPDATED_AT));
					
				} else {
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			runOnUiThread(new Runnable() {
				public void run() {
					try {
						map.clear();
						map.addMarker(mo).showInfoWindow();

						CameraUpdate update = CameraUpdateFactory
						 .newLatLngZoom(mo.getPosition(), 15);
						map.animateCamera(update);

					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			return null;
		}
	}
    
    class BusInfoWindowAdapter implements InfoWindowAdapter {

        private final View mContents;

        BusInfoWindowAdapter() {
            mContents = getLayoutInflater().inflate(R.layout.bus_info_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
        	return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
        	
        	TextView textViewSpeed = ((TextView) view.findViewById(R.id.textViewSpeed));
        	textViewSpeed.setText(getString(R.string.speed) + ": " + marker.getTitle() + " km/h");
        	
        	TextView textViewUpdatedAt = ((TextView) view.findViewById(R.id.textViewUpdatedAt));
        	textViewUpdatedAt.setText(getString(R.string.updated_at) + ": " + marker.getSnippet());
        }
    }
}