package com.example.currentctconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textView1,textView2,textView3,textView4,textView5;
    ArrayList<String> ulkeler;
    String[] tokens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner kurListe = findViewById(R.id.spinner);
        textView1=findViewById(R.id.txtCAD);
        textView2=findViewById(R.id.txtUSD);
        textView3=findViewById(R.id.txtJPY);
        textView4=findViewById(R.id.txtKRW);
        textView5=findViewById(R.id.txtTR);
        getRates();

        ulkeler = new ArrayList<>();


    }

    public void getRates() {
        DownloadData downloadData = new DownloadData();
        try {
            String url = "http://data.fixer.io/api/latest?access_key=2fbd760028dc01f6790b1eb95d311089&format=1";
            //String url="https://finans.truncgil.com/today.json";
            downloadData.execute(url);
        } catch (Exception e) {

        }
    }


    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();//int değer veriyor

                while (data > 0) {
                    char character = (char) data;
                    result = result + character;

                    data = inputStreamReader.read();
                }


                return result;
            } catch (Exception e) {
                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //System.out.println("Alınan Data" + s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String rates = jsonObject.getString("rates");
                JSONObject jsonObject1 = new JSONObject(rates);
                String turkishLira = jsonObject1.getString("TRY");
                String canadian = jsonObject1.getString("CAD");
                String usd = jsonObject1.getString("USD");
                String japan = jsonObject1.getString("JPY");
                String kore = jsonObject1.getString("KRW");
                textView1.setText("CAD :"+canadian);
                textView2.setText("USD :"+usd);
                textView3.setText("JPY :"+japan);
                textView4.setText("KRW :"+kore);
                textView5.setText("TRY :"+turkishLira);
                //System.out.println("Veriler :"+rates);
                rates += rates.replace("{","");
                rates += rates.replace("}","");
                tokens = rates.split(",");
                String x;
                ArrayList<String> veriler =new ArrayList<>();
                for (int i=0;i<168;i++)
                    veriler.add(tokens[i]);

                System.out.println(veriler.get(0));
                System.out.println();



            }catch (Exception e)
            {

            }
        }


    }
}
