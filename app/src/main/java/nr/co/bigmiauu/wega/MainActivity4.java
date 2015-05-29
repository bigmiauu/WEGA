package nr.co.bigmiauu.wega;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity4 extends ActionBarActivity {

    private String nom;
    private AutoCompleteTextView actv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity4);

        String[] profes = getResources().
                getStringArray(R.array.profes);
        ArrayAdapter adapters = new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,profes);

        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        actv.setAdapter(adapters);
    }

    public void revisa(View view) {
        String s = this.actv.getEditableText().toString();
        boolean bandera = false;


        try {
            InputStreamReader archivo = new InputStreamReader(
                    openFileInput("profes.txt"));
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();

            while (linea != null) {
                if(linea.equals(s)){
                    bandera = true;
                }
                linea = br.readLine();
            }
            br.close();
            archivo.close();
        } catch (IOException e) {

        }

        if(bandera == true){
            nom = s;
            Intent j = new Intent(this,  MainActivity5.class);
            j.putExtra("nom",nom);
            startActivity(j);
        }
        else{
            Toast.makeText(this, "Error no se encuentra al profesor", Toast.LENGTH_LONG).show();

        }
    }

}
