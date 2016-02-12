package com.per161.icesi.miaplicaciondelasemana1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {


    private String direccionBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        direccionBusqueda = "www.google.com";
        TextView tvDir = (TextView) findViewById(R.id.tv_dir);
        tvDir.setText(direccionBusqueda);


        Intent i =  new Intent(getApplicationContext(), Main2Activity.class);
        startActivity(i);


        Button btnConsultar = (Button) findViewById(R.id.btn_consulta);
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"CLICK",Toast.LENGTH_SHORT).show();
                TareaConsulta t = new TareaConsulta();
                t.execute(direccionBusqueda);
                try {
                    InetAddress dirs[] = t.get();
                    String contenido = "";
                    for(int i = 0 ; i < dirs.length ; i++){
                        contenido+=dirs[i].toString()+"\n";
                    }
                    TextView tvs = (TextView) findViewById(R.id.tv_ip_list);
                    tvs.setText(contenido);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class TareaConsulta extends AsyncTask<String, Void, InetAddress[]> {
        @Override
        protected InetAddress[] doInBackground(String... params) {
            InetAddress[] ips = null;
            try {
                ips = InetAddress.getAllByName(params[0]);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return ips;
        }
    }
}
