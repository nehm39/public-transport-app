package wsiiz.projekt.mpkrzeszow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wsiiz.projekt.mpkrzeszow.others.JSONFile;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

public class MapaActivity extends FragmentActivity {
	private GoogleMap map;
	private MarkerOptions mo;
	private double DLAT, DLNG;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);

		// Odczytanie ustawień mapy z pliku:
		SharedPreferences sharedprefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String mapPref = sharedprefs.getString("mapType", "normal");
		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mapPrzystanki)).getMap();
		// Ustawienie odpowiedniego rodzaju mapu:
		if (mapPref.equals("normal")) {
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		} else if (mapPref.equals("hybrid"))
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		else if (mapPref.equals("satellite"))
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		else if (mapPref.equals("terrain"))
			map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		map.setInfoWindowAdapter(new PrzystanekInfoWindowAdapter());
		// Ustawienie mapy na Rzeszow:
		CameraUpdate updateCamera = CameraUpdateFactory.newLatLngZoom(
				new LatLng(50.035974, 22.005157), 12);
		map.animateCamera(updateCamera);
		map.setMyLocationEnabled(false);
		// Odczytanie danych z jsona:
		try {
			JSONObject json = new JSONObject(JSONFile.readJSON(
					"przystanki.json", getApplicationContext()));
			JSONArray przystanekObj = json.getJSONArray("przystanki");
			for (int i = 0; i < przystanekObj.length(); i++) {
				JSONObject przystanek = przystanekObj.getJSONObject(i);
				mo = new MarkerOptions();
				DLAT = Double.parseDouble(przystanek.getString("lat"));
				DLNG = Double.parseDouble(przystanek.getString("lng"));
				mo.position(new LatLng(DLAT, DLNG));
				mo.title(przystanek.getString("name"));
				mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.punkt));
				map.addMarker(mo);
				map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
					public void onInfoWindowClick(Marker marker) {
						String id = marker.getId();
						id = id.replace("m", "");
						int position = Integer.parseInt(id);
						Intent intent = new Intent(MapaActivity.this,
								RozkladPrzystankowActivity2.class);
						intent.putExtra("position", (int) position);
						String selected = marker.getTitle();
						intent.putExtra("selected", selected);
						startActivity(intent);
					}
				});

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Detale markerów:
	class PrzystanekInfoWindowAdapter implements InfoWindowAdapter {

		private final View mContents;

		PrzystanekInfoWindowAdapter() {
			mContents = getLayoutInflater().inflate(R.layout.przystanek_info,
					null);
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

			TextView textView = ((TextView) view
					.findViewById(R.id.textViewName));
			textView.setText(getString(R.string.przystanekName) + ": "
					+ marker.getTitle());
		}

	}
}