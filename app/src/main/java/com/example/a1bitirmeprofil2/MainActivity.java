package com.example.a1bitirmeprofil2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    //List<User> users = new ArrayList<User>();
    List<User> gotusers = new ArrayList<User>();
    EditText editText1, editText2, editText3, editText4;
    String newText, newText1, newText2, newText3;
    Button button1, button2;

    User user= new User();
    User user2= new User();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final AppDatabase db2 = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        gotusers= db2.userDao().getAll();


        if(gotusers.size() == 0) {
            button1= findViewById(R.id.button);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Executor myExecutor = Executors.newSingleThreadExecutor();
                    myExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            editText1= findViewById(R.id.editText);
                            String email1= editText1.getText().toString();
                            editText2= findViewById(R.id.editText4);
                            String passw= editText2.getText().toString();
                            editText3= findViewById(R.id.editText5);
                            String name1= editText3.getText().toString();
                            editText4= findViewById(R.id.editText6);
                            String surname= editText4.getText().toString();


                            user.setEmail(email1);
                            user.setFirstName(name1);
                            user.setLastName(surname);
                            user.setPassword(passw);

                            db2.userDao().insertone(user);


                            System.out.println("name: " +name1);
                            System.out.println("surname: " +surname);
                            System.out.println("email: " +email1);
                            System.out.println("password: " +passw);
                        }
                    });
                }
            });
        }
        else{

            String gotName, gotSurname, gotEmail, gotPassword;

            user2= gotusers.get(0);


            gotEmail = user2.getEmail();
            gotName = user2.getFirstName();
            gotSurname = user2.getLastName();
            gotPassword = user2.getPassword();

            System.out.println("name: " + gotName);
            System.out.println("surname: " + gotSurname);
            System.out.println("email: " + gotEmail);
            System.out.println("password: " + gotPassword);

            Intent toProgressActivity1= new Intent(getApplicationContext(), ProgressActivity.class);
            toProgressActivity1.putExtra("name", gotName);
            toProgressActivity1.putExtra("surname", gotSurname);
            startActivity(toProgressActivity1);
            finish();
        }
    }


    public void OnClickRegister(View view) {
        //try{Thread.sleep(500);}catch (Exception e)
        //{e.printStackTrace();}
    }

    public void onClickLogin(View view) {
        Intent toProgressActivity= new Intent(getApplicationContext(), ProgressActivity.class);
        startActivity(toProgressActivity);
        finish();
    }
}
