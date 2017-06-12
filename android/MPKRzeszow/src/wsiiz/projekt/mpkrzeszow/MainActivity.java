package wsiiz.projekt.mpkrzeszow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

/*
Projekt z programowania obiektowego.
Autorzy:
Patryk Siwiec w50034
Szymon Gajewski w50033
Szymon Świętek w50036
Nikol Wojdacz w50035
 */

public class MainActivity extends Activity {
    final Context context = this;
    private Button btnSzukaj;
    private Button btnRozkladAutobusow;
    private Button btnRozkladPrzystankow;
    private Button btnMapa;
    private Button btnBilet;
    private Button btnUstawienia;
    private Button btnKontakt;
    private Button btnWyjscie;
    //Sprawdzenie czy telefon ma dostęp do internetu:
    public Boolean isInternet()  {
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiInfo.isConnected() || mobileInfo.isConnected()) {
                return true;
            }
        }
        catch(Exception e){
           e.printStackTrace();
        }
        return false;
    }
    //Odczytanie ustawień użytkownika odnośnie wifi:
    public Boolean isWifiSettings()  {
    	SharedPreferences sharedprefs = PreferenceManager.getDefaultSharedPreferences(this);
    	Boolean isWifiSettings = sharedprefs.getBoolean("wifi", false);
    	return isWifiSettings;    	
    }
    
    //Start aplikacji:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Wczytanie odpowiedniego layoutu:
        setContentView(R.layout.activity_main);
        //Zapisanie widoku aktualnego activity:
    	View view = this.getWindow().getDecorView();
    	//Odczytanie ustawień użytkownika odnośnie koloru:
    	SharedPreferences sharedprefs = PreferenceManager.getDefaultSharedPreferences(this);
    	String color = sharedprefs.getString("backgroundColor", "#ffa30000");
    	//Ustawienie tła activity na kolor wybrany w ustawieniach:
    	view.setBackgroundColor(Color.parseColor(color));
    	//Listener przycisków:
        addListenerOnButton();
    }
    
    //Działanie aplikacji po jej wznowieniu:
    @Override
    public void onResume() {
    	super.onResume();
    	View view = this.getWindow().getDecorView();
    	SharedPreferences sharedprefs = PreferenceManager.getDefaultSharedPreferences(this);
    	String color = sharedprefs.getString("backgroundColor", "#ffa30000");
    	view.setBackgroundColor(Color.parseColor(color));
    }
    
    //Działanie aplikacji po jej ponownym uruchomieniu:
    @Override
    public void onRestart() {
    	super.onRestart();
    	View view = this.getWindow().getDecorView();
    	SharedPreferences sharedprefs = PreferenceManager.getDefaultSharedPreferences(this);
    	String color = sharedprefs.getString("backgroundColor", "#ffa30000");
    	view.setBackgroundColor(Color.parseColor(color));
    }
   

    //Listener przycisków:
    private void addListenerOnButton() {
        btnSzukaj = (Button) findViewById(R.id.button_szukaj);
        btnRozkladAutobusow = (Button) findViewById(R.id.button_rozklad_autobusow);
        btnRozkladPrzystankow = (Button) findViewById(R.id.button_rozklad_przystankow);
        btnMapa = (Button) findViewById(R.id.button_mapa);
        btnBilet = (Button) findViewById(R.id.button_bilet);
        btnUstawienia = (Button) findViewById(R.id.button_ustawienia);
        btnKontakt = (Button) findViewById(R.id.button_kontakt);
        btnWyjscie = (Button) findViewById(R.id.button_wyjscie);

        //Przycisk szukaj:
        btnSzukaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(isInternet() && isWifiSettings()){
        			ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                        if (wifiInfo.isConnected()) {
                            //Nowa intencja, wywołanie nowej aktywności:
                    		Intent szukaj = new Intent(MainActivity.this, SzukajActivity.class); startActivity(szukaj);
                        }
                        else Toast.makeText(getApplicationContext(), "Brak połączenia wi-fi. Zmień ustawienia aplikacji, aby korzystać z sieci GSM.", Toast.LENGTH_SHORT).show();
            	}
            	if(isInternet() && !(isWifiSettings())){
                        Intent szukaj = new Intent(MainActivity.this, SzukajActivity.class); startActivity(szukaj);
            	}
            	if( !(isInternet()) ){ Toast.makeText(getApplicationContext(), "Brak połączenia internetowego", Toast.LENGTH_SHORT).show();
            	}
            }
        });

        //Przycisk rozkład autobusów:
        btnRozkladAutobusow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rozkladA = new Intent(MainActivity.this, RozkladAutobusowActivity.class); startActivity(rozkladA);
            }
        });

        //Przycisk rozkład przystanków:
        btnRozkladPrzystankow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rozkladB = new Intent(MainActivity.this, RozkladPrzystankowActivity.class); startActivity(rozkladB);
            }
        });

        //Przycisk mapa:
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapa = new Intent(MainActivity.this, MapaActivity.class); startActivity(mapa);
            }
        });

        //Przycisk kup bilet:
        btnBilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bilet = new Intent(MainActivity.this, BiletActivity.class); startActivity(bilet);
            }
        });

        //Przycisk ustawienia:
        btnUstawienia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ustawienia = new Intent(MainActivity.this, UstawieniaActivity.class); startActivity(ustawienia);
            }
        });

        //Przycisk kontakt:
        btnKontakt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent kontakt = new Intent(MainActivity.this, KontaktActivity.class); startActivity(kontakt);
            }
        });

        //Przycisk wyjście:
        btnWyjscie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

//Menu:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    //Listener wybrania opcji w menu:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        	//Informacje:
            case R.id.informacje:
            	//Wyświetlenie okienka dialogu:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder
                        .setTitle(R.string.informacje)
                        .setMessage(R.string.informacje_tresc)
                        .setCancelable(true)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            //Strona www:
            case R.id.www:
            	String url = "http://www.mpk.rzeszow.pl";
            	Intent i = new Intent(Intent.ACTION_VIEW);
            	i.setData(Uri.parse(url));
            	startActivity(i);
            	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
