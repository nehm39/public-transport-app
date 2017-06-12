package wsiiz.projekt.mpkrzeszow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wsiiz.projekt.mpkrzeszow.others.JSONFile;
import wsiiz.projekt.mpkrzeszow.others.ExpandableListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class RozkladPrzystankowActivity2 extends Activity {
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	Intent intent;
	String selected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rozklad_przystankow2);
		// Odczytanie danych przekazanych z poprzedniej intencji:
		intent = getIntent();
		selected = intent.getStringExtra("selected");
		setTitle(selected);
		expListView = (ExpandableListView) findViewById(R.id.listaRozwijanaPrzystankow);
		jsonDane();
		listAdapter = new ExpandableListAdapter(this, listDataHeader,
				listDataChild);
		expListView.setAdapter(listAdapter);

	}

	private void jsonDane() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		try {
			//Wczytanie pliku linie.json:
			JSONObject json = new JSONObject(JSONFile.readJSON("linie.json",
					getApplicationContext()));
			//Wczytanie tablicy linii z jsona:
			JSONArray linieObj = json.getJSONArray("linie");
			//Zmienna zawieraj¹ce listê nag³ówków(linii):
			int headery = 0;
			//Pêtla iteruj¹ca siê po elementach tablicy linii:
			for (int i = 0; i < linieObj.length(); i++) {
				//Wczytanie pojedynczego obiektu linii:
				JSONObject liniaObj = linieObj.getJSONObject(i);
				//Zapisanie nazwy linii:
				String nazwaP = liniaObj.getString("name");
				//Wczytanie tablicy przystanków danej linii z jsona:
				JSONArray przystankiObj = liniaObj.getJSONArray("przystanki");
				//Pêtla iteruj¹ca siê po elementach tablicy przystanków:
				for (int x = 0; x < przystankiObj.length(); x++) {
					//Wczytanie pojedycznego obiektu przystanku:
					JSONObject przystanekObj = przystankiObj.getJSONObject(x);
					//Zapisanie nazwa przystanku:
					String nazwa = przystanekObj.getString("name");
					//Sprawdzenie czy nazwa przystanku jest taka sama jak nazwa przystanku wybranego w poprzednim activity:
					if (nazwa.equals(selected)) {
						//Dodanie nag³ówka listy:
						listDataHeader.add(nazwaP);
						//Utworzenie listy:
						List<String> lista = new ArrayList<String>();
						//Wczytanie tablicy godzin danego przystanku:
						JSONArray godzinyObj = przystanekObj
								.getJSONArray("godziny");
						//Pêtla iteruj¹ca siê po elementach tablicy godzin:
						for (int y = 0; y < godzinyObj.length(); y++) {
							//Zapisanie danej godziny:
							String godzinaS = godzinyObj.getString(y);
							//Dodanie godziny do listy:
							lista.add(godzinaS);
							//Dodanie listy godzin jako podkategoriê nag³ówka:
							listDataChild.put(listDataHeader.get(headery),
									lista);
						}
						//Zinkrementowanie liczby nag³ówków:
						headery++;
					}
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}