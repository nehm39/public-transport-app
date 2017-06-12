package wsiiz.projekt.mpkrzeszow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wsiiz.projekt.mpkrzeszow.others.ExpandableListAdapter;
import wsiiz.projekt.mpkrzeszow.others.JSONFile;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.app.Activity;
import android.content.Intent;

public class RozkladAutobusowActivity2 extends Activity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rozklad_autobusow2);
        expListView = (ExpandableListView) findViewById(R.id.listaRozwijanaAutobusow);
        Intent intent = getIntent();
        String selected = intent.getStringExtra("selected");
        jsonDane();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        setTitle(selected);
	}
	
    private void jsonDane() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        
        int position = getIntent().getExtras().getInt("position");
		try {
			JSONObject json = new JSONObject(JSONFile.readJSON("linie.json",getApplicationContext()));
			JSONArray linieObj = json.getJSONArray("linie");
			JSONObject wybranaLiniaObj = linieObj.getJSONObject(position);
			JSONArray przystankiLiniiObj = wybranaLiniaObj.getJSONArray("przystanki");
			for(int i=0; i < przystankiLiniiObj.length() ; i++) {
			    JSONObject przystanekObj = przystankiLiniiObj.getJSONObject(i);
			    String name = przystanekObj.getString("name");
			    listDataHeader.add(name);
			    JSONArray godzinyObj = przystanekObj.getJSONArray("godziny");
			    List<String> lista = new ArrayList<String>();
			    for(int x=0; x < godzinyObj.length(); x++){
				    String godzinaS = godzinyObj.getString(x);		    
				    lista.add(godzinaS);
				    listDataChild.put(listDataHeader.get(i), lista);
			    }
			}
			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

}
