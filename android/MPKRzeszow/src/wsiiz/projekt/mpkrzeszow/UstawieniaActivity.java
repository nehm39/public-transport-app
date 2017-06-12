package wsiiz.projekt.mpkrzeszow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.provider.Settings;
 
public class UstawieniaActivity extends PreferenceActivity {
	
	final Context context = this;
	
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sczytanie ustawień z pliku:
        addPreferencesFromResource(R.xml.settings);
         
    //Ustawienia GPS:
    Preference ustawieniaGPS = (Preference) findPreference("ustawieniaGPS");
    //Listener kliknięcia opcji:
    ustawieniaGPS.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
            	//Otworzenie ustawień lokalizacji:
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                return true;
            }
        });
    
    //Informacje o aplikacji:
    Preference informacje = (Preference) findPreference("informacje");
    informacje.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
    		//Listener kliknięcia opcji:
            public boolean onPreferenceClick(Preference preference) {
            		//Otworzenie okienka z dialogiem:
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

            }
        });
    
    //Zaciemnienie wersji aplikacji:
    Preference wersja = (Preference) findPreference("wersja");
    wersja.setEnabled(false);
    
    
    }
    
}