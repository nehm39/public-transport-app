package wsiiz.projekt.mpkrzeszow;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wsiiz.projekt.mpkrzeszow.others.JSONFile;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RozkladAutobusowActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rozklad_autobusow);
		final ListView listaAutobusow = (ListView) findViewById(R.id.listviewListaAutobusow);
		// Odczytanie danych z jsona:
		try {
			JSONObject json = new JSONObject(JSONFile.readJSON("linie.json",
					getApplicationContext()));
			JSONArray linieObj = json.getJSONArray("linie");
			ArrayList<String> linie = new ArrayList<String>();
			for (int i = 0; i < linieObj.length(); i++) {
				JSONObject liniaObj = linieObj.getJSONObject(i);
				String name = liniaObj.getString("name");
				linie.add(name);
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, linie);
			listaAutobusow.setAdapter(adapter);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listaAutobusow
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> av, View view,
							int position, long id) {
						String selected = (listaAutobusow
								.getItemAtPosition(position).toString());
						Intent i = new Intent(RozkladAutobusowActivity.this,
								RozkladAutobusowActivity2.class);
						// Przekazanie zmiennych do kolejnej intencji:
						i.putExtra("position", (int) position);
						i.putExtra("selected", selected);
						startActivity(i);
					}
				});
	}
}