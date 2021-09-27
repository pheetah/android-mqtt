package com.example.a1bitirmeprofil2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class ProgressActivity extends AppCompatActivity {

    ProgressBar progressBar;
    int counter = 0;

    String isim, soyisim;

    TextView isimText, soyisimText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);


        isimText= findViewById(R.id.NameID);
        soyisimText= findViewById(R.id.SurnameID);

        Intent getMain= getIntent();
        isim= getMain.getStringExtra("name");
        soyisim= getMain.getStringExtra("surname");

        isimText.setText("Hoşgeldiniz");
        soyisimText.setText(isim + "  " + soyisim);

        processToMqtt();
    }


    public void processToMqtt()
    {

        progressBar= (ProgressBar) findViewById(R.id.progress_circular);

        final Timer t= new Timer();

        final TimerTask timerTask= new TimerTask() {
            @Override
            public void run() {
                counter++;
                progressBar.setProgress(counter);

                if(counter == 100)
                {
                 t.cancel();
                    Intent toAfterLogIn= new Intent(getApplicationContext(), AfterLogInActivity.class);
                    startActivity(toAfterLogIn);
                    finish();
                }
            }
        };

        t.schedule(timerTask, 0, 100 );



    }
}

