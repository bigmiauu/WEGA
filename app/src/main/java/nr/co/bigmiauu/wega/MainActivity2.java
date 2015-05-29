package nr.co.bigmiauu.wega;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;


public class MainActivity2 extends ActionBarActivity {

    private TextView tx1;
    private ImageButton bna;
    private ImageButton mla;
    private String nom;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        Bundle bundle = getIntent().getExtras();
        nom = bundle.getString("nom");

        tx1 = (TextView) findViewById(R.id.textView2);
        tx1.setText(nom);




        bna=(ImageButton)findViewById(R.id.imageButton);
        mla=(ImageButton)findViewById(R.id.imageButton2);

        bna.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new enviar(MainActivity2.this).execute();
            }

        });

        mla.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                    new enviar2(MainActivity2.this).execute();
            }

        });

    }

    //enviar los datos de la votacion positiva.
    private boolean bna(){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://bigmiauu.net63.net/WEGA/insert.php"); // Url del Servidor
        //Añadimos nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(3);
        nameValuePairs.add(new BasicNameValuePair("id",nom));
        nameValuePairs.add(new BasicNameValuePair("buenas","1"));
        nameValuePairs.add(new BasicNameValuePair("malas","0"));



        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.execute(httppost);
            return true;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    //enviar los datos de la votacion negativa.
    private boolean mla(){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();

        httppost= new HttpPost("http://bigmiauu.net63.net/WEGA/insert.php"); // Url del Servidor
        //Añadimos nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(3);
        nameValuePairs.add(new BasicNameValuePair("id",nom));
        nameValuePairs.add(new BasicNameValuePair("buenas","0"));
        nameValuePairs.add(new BasicNameValuePair("malas","1"));


        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.execute(httppost);
            return true;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }


    public void mandar(){
        Intent j = new Intent(this,  MainActivity5.class);
        j.putExtra("nom",nom);
        startActivity(j);
    }

    //AsyncTask para enviar positivo
    class enviar extends AsyncTask<String,String,String> {

        private Activity context;

        enviar(Activity context){
            this.context=context;
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(bna())
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Votacion registrada con exito", Toast.LENGTH_LONG).show();
                        Toast.makeText(context, "Graciar por votar a "+nom, Toast.LENGTH_LONG).show();
                        mandar();
                        finish();


                    }
                });
            else
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Error, revise su conexion a internet", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            return null;
        }
    }

    //AsyncTask para enviar positivo
    class enviar2 extends AsyncTask<String,String,String> {

        private Activity context;

        enviar2(Activity context){
            this.context=context;
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(mla())
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Votacion registrada con exito", Toast.LENGTH_LONG).show();
                        Toast.makeText(context, "Graciar por votar a "+nom, Toast.LENGTH_LONG).show();
                        mandar();
                        finish();


                    }
                });
            else
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Error, revise su conexion a internet", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            return null;
        }
    }


}
