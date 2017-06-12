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

public class RozkladPrzystankowActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rozklad_przystankow);
        final ListView listaPrzystankow = (ListView) findViewById(R.id.listviewListaPrzystankow);

 		try {
			JSONObject json = new JSONObject(JSONFile.readJSON("przystanki.json",getApplicationContext()));
			JSONArray przystankiObj = json.getJSONArray("przystanki");
			ArrayList<String> przystanki = new ArrayList<String>();
			for(int i=0; i < przystankiObj.length() ; i++) {
			    JSONObject przystanekObj = przystankiObj.getJSONObject(i);
			    String name=przystanekObj.getString("name");
			    przystanki.add(name);
			}
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		            android.R.layout.simple_list_item_1, przystanki);
		    listaPrzystankow.setAdapter(adapter);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    listaPrzystankow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	@Override
	        public void onItemClick(AdapterView<?> av, View view, int position, long id) {
	        	String selected = (listaPrzystankow.getItemAtPosition(position).toString());
	        	Intent i = new Intent(RozkladPrzystankowActivity.this, RozkladPrzystankowActivity2.class); 
	        	i.putExtra("position",(int)position); 
	        	i.putExtra("selected",selected);
	        	startActivity(i);
	        }
	    });
    }
}