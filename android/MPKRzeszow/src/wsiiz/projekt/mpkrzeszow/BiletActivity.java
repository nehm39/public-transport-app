package wsiiz.projekt.mpkrzeszow;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class BiletActivity extends Activity {
    private Spinner RodzajBiletu;
    private int RodzajBiletuPoz;
    private Spinner RodzajUlgi;
    private int RodzajUlgiPoz;
    private Spinner Strefa;
    private int StrefaPoz;
    private double cena;
    private Button btnKup;
    private String numer;
    private TextView cenabiletu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilet);
        cenabiletu = (TextView) findViewById(R.id.cenabiletu);
        updateCena();
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
    }

    private void addListenerOnSpinnerItemSelection() {
        RodzajBiletu = (Spinner) findViewById(R.id.Spinner_bilet1);
        RodzajUlgi = (Spinner) findViewById(R.id.Spinner_bilet2);
        Strefa = (Spinner) findViewById(R.id.Spinner_bilet3);
        RodzajBiletu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RodzajBiletu = (Spinner) findViewById(R.id.Spinner_bilet1);
                RodzajBiletuPoz = RodzajBiletu.getSelectedItemPosition();
                warunki();
                updateCena();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
// TODO Auto-generated method stub
            }
        });

        RodzajUlgi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RodzajUlgi = (Spinner) findViewById(R.id.Spinner_bilet2);
                RodzajUlgiPoz = RodzajUlgi.getSelectedItemPosition();
                warunki();
                updateCena();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
// TODO Auto-generated method stub
            }
        });

        Strefa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Strefa = (Spinner) findViewById(R.id.Spinner_bilet3);
                StrefaPoz = Strefa.getSelectedItemPosition();
                warunki();
                updateCena();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
// TODO Auto-generated method stub
            }
        });


    }

    public void addListenerOnButton() {

        btnKup = (Button) findViewById(R.id.button_bilet1);

        //Akcja przycisku Kup bilet:
        btnKup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            	//Wybranie numeru:
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(numer));
                startActivity(callIntent);
            }

    });

}	
    //Warunki sprawdzające wybrane rodzaje biletów i przypisujące odpowiednią cenę/numer:
    private void warunki() {
        //(ponowne) Aktywowanie spinnerów:
        RodzajBiletu.getSelectedView();
        RodzajUlgi.getSelectedView();
        Strefa.getSelectedView();
        RodzajBiletu.setEnabled(true);
        RodzajUlgi.setEnabled(true);
        Strefa.setEnabled(true);

        //Jednoprzejazdowy:
        if (RodzajBiletuPoz == 0 && RodzajUlgiPoz == 0 && StrefaPoz == 0) {cena = 2.80; numer = "tel:172509000";}
        else if (RodzajBiletuPoz == 0 && RodzajUlgiPoz == 0 && StrefaPoz == 1) {cena = 2.60; numer = "tel:172509013";}
        else if (RodzajBiletuPoz == 0 && RodzajUlgiPoz == 0 && StrefaPoz == 2) {cena = 3.60; numer = "tel:172509005";}
        else if (RodzajBiletuPoz == 0 && RodzajUlgiPoz == 1 && StrefaPoz == 0) {cena = 1.40; numer = "tel:172509001";}
        else if (RodzajBiletuPoz == 0 && RodzajUlgiPoz == 1 && StrefaPoz == 1) {cena = 1.30; numer = "tel:172509014";}
        else if (RodzajBiletuPoz == 0 && RodzajUlgiPoz == 1 && StrefaPoz == 2) {cena = 1.80; numer = "tel:172509006";}
        else if (RodzajBiletuPoz == 0 && RodzajUlgiPoz == 2 && StrefaPoz == 0) {cena = 1.70; numer = "tel:172509002";}
        else if (RodzajBiletuPoz == 0 && RodzajUlgiPoz == 2 && StrefaPoz == 1) {cena = 1.90; numer = "tel:172509015";}
        else if (RodzajBiletuPoz == 0 && RodzajUlgiPoz == 2 && StrefaPoz == 2) {cena = 2.50; numer = "tel:172509007";}
        //60 minutowy:
        else if (RodzajBiletuPoz == 1 && RodzajUlgiPoz == 0 && StrefaPoz == 0) {cena = 4.00; numer = "tel:172509060";}
        else if (RodzajBiletuPoz == 1 && RodzajUlgiPoz == 0 && (StrefaPoz == 1 || StrefaPoz == 2)) {cena = 5.80; numer = "tel:172509016";}
        else if (RodzajBiletuPoz == 1 && RodzajUlgiPoz == 1 && StrefaPoz == 0) {cena = 2.00; numer = "tel:172509061";}
        else if (RodzajBiletuPoz == 1 && RodzajUlgiPoz == 1 && (StrefaPoz == 1 || StrefaPoz == 2)) {cena = 2.90; numer = "tel:172509017";}
        else if (RodzajBiletuPoz == 1 && RodzajUlgiPoz == 2 && StrefaPoz == 0) {cena = 2.40; numer = "tel:172509062";}
        else if (RodzajBiletuPoz == 1 && RodzajUlgiPoz == 2 && (StrefaPoz == 1 || StrefaPoz == 2)) {cena = 3.10; numer = "tel:172509018";}
        //Dobowy:
        else if (RodzajBiletuPoz == 2 && RodzajUlgiPoz == 0) { cena = 12.00; numer = "tel:172509040"; Strefa.setSelection(0); Strefa.setEnabled(false);}
        else if (RodzajBiletuPoz == 2 && RodzajUlgiPoz == 1) { cena = 6.00; numer = "tel:172509041"; Strefa.setSelection(0); Strefa.setEnabled(false);}
        else if (RodzajBiletuPoz == 2 && RodzajUlgiPoz == 2) { cena = 7.20; numer = "tel:172509042"; Strefa.setSelection(0); Strefa.setEnabled(false);}
        //Linia specjalna L:
        else if (RodzajBiletuPoz == 3 && (RodzajUlgiPoz == 0 || RodzajUlgiPoz == 2)) { cena = 8.00; numer = "tel:172509019"; Strefa.setSelection(2); Strefa.setEnabled(false);}
        else if (RodzajBiletuPoz == 3 && RodzajUlgiPoz == 1) { cena = 4.00; numer = "tel:172509023"; Strefa.setSelection(2); Strefa.setEnabled(false);}
        //Przewóz bagażu lub psa:
        else if (RodzajBiletuPoz == 4) { cena = 2.10; numer = "tel:172509024"; RodzajUlgi.setSelection(0); RodzajUlgi.setEnabled(false); Strefa.setSelection(2); Strefa.setEnabled(false);}
    }
    
    //Aktualizacja textview z ceną:
    private void updateCena() {
        cenabiletu.setText(new StringBuilder()
                        .append("Cena: ").append(cena).append(" zł"));
    }
    }
