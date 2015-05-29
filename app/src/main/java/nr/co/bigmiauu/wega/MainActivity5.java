package nr.co.bigmiauu.wega;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity5 extends ActionBarActivity {

    private List<Personas> listaPersonas;
    private TextView nombre;
    private TextView ultcal;
    private TextView total;


    private ImageView i1,i2,i3,i4,i5;

    private int posicion=0;
    private String direccion = "http://bigmiauu.net63.net/WEGA/selectall.php?nombre=";
    private String nom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity5);
        listaPersonas=new ArrayList<Personas>();
        nombre=(TextView)findViewById(R.id.textView3);
        total=(TextView)findViewById(R.id.textView4);
        ultcal=(TextView)findViewById(R.id.textView5);

        i1=(ImageView)findViewById(R.id.imageView2);
        i2=(ImageView)findViewById(R.id.imageView3);
        i3=(ImageView)findViewById(R.id.imageView4);
        i4=(ImageView)findViewById(R.id.imageView5);
        i5=(ImageView)findViewById(R.id.imageView6);
        //i1.getLayoutParams().width = 50;
        //i1.getLayoutParams().height = 50;
        i1.setAlpha(5);
        i2.setAlpha(5);
        i3.setAlpha(5);
        i4.setAlpha(5);
        i5.setAlpha(5);

        Bundle bundle = getIntent().getExtras();
        nom = bundle.getString("nom");

        nom = nom.replaceAll(" ","%20");

        direccion = direccion+nom;
        //Mostramos los datos de la persona por pantalla.
                new Mostrar().execute();

    }

    //Obtiene los datos del servidor en forma de String
    private String mostrar(){
        String resquest="";
        HttpClient httpclient;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost(direccion); // Url del Servidor
        try {
            //Ejecutamos y obtenemos la respuestaa del servidor
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            resquest = httpclient.execute(httppost, responseHandler);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block

            ultcal.setText("Error al revisar informacion, no hay conexion a internet");
            //e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            ultcal.setText("Error al revisar informacion, no hay conexion a internet");

            //e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            ultcal.setText("Error al revisar informacion, no hay conexion a internet");

            //e.printStackTrace();
        }
        return resquest;

    }



    //Descompone, crea un objeto con los datos descompuestos y lo almacena en nuestro ArrayList
    private boolean filtrarDatos(){
        listaPersonas.clear();
        if(!mostrar().equalsIgnoreCase("")){

                String datosPersona[]=mostrar().split("<br>");
                Personas personas=new Personas();
                personas.setNombre(datosPersona[0]);
                personas.setCalb(datosPersona[1]);
                personas.setCalm(datosPersona[2]);
               personas.setTotal(datosPersona[3]);
               personas.setUltcal(datosPersona[4]);
                listaPersonas.add(personas);
            }
            return true;


    }



    //Muestra la persona almacenada como objeto en nuestro ArrayList
    private void mostrarPersona(final int posicion){
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Personas personas=listaPersonas.get(posicion);
                nombre.setText(personas.getNombre());
                total.setText("Total de calificaciones: "+personas.getTotal());
                ultcal.setText("Ultima calificacion: "+personas.getUltcal());
                if(!(Integer.parseInt(personas.getTotal())==0)){
                    int total = Integer.parseInt(personas.getTotal());
                    int calbuenas = Integer.parseInt(personas.getCalb());
                    int porsentaje = (calbuenas*100)/total;

                    //serie de ifs para las estrellas
                    if(porsentaje>=1 && porsentaje<=20){

                        i1.setAlpha((porsentaje*1000)/20);
                    }
                    if(porsentaje>=21 && porsentaje<=40){
                        i1.setAlpha(1000);
                        i2.setAlpha((porsentaje*1000)/40);
                    }
                    if(porsentaje>=41 && porsentaje<=60){
                        i1.setAlpha(1000);
                        i2.setAlpha(1000);
                        i3.setAlpha((porsentaje*1000)/60);
                    }
                    if(porsentaje>=61 && porsentaje<=80){
                        i1.setAlpha(1000);
                        i2.setAlpha(1000);
                        i3.setAlpha(1000);
                        i4.setAlpha((porsentaje*1000)/80);
                    }
                    if(porsentaje>=81 && porsentaje<=100){
                        i1.setAlpha(1000);
                        i2.setAlpha(1000);
                        i3.setAlpha(1000);
                        i4.setAlpha(1000);
                        i5.setAlpha((porsentaje*1000)/100);
                    }

                }

            }
        });
    }

    //AsyncTask para mostrar Personas
    class Mostrar extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(filtrarDatos())mostrarPersona(posicion);
            return null;
        }
    }

}
