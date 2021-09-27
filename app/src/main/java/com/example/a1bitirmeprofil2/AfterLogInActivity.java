package com.example.a1bitirmeprofil2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;


public class AfterLogInActivity extends AppCompatActivity {

    //public MyResultReceiver mReceiver;

    ArrayList<String> messagesToList;

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static Context context;

    List<User> messagesSaved = new ArrayList<User>();

    MqttHelper mqttHelper;
    //Bundle bundle= new Bundle();
    TextView msgtoshow;
    SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_log_in);






        //Intent toIntentService= new Intent(getApplicationContext(),MyIntentService.class);
        //toIntentService.putExtra("name", "memet");
        //startService(toIntentService);

        //ResultReceiver resultReceiver= new MyResultReceiver(null);



        String userInput= "memo";

        AfterLogInActivity.context= getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        messagesToList= new ArrayList<String>();

        startMqtt();

        mAdapter.setClickListener(new MyAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                messagesToList.remove(position);
                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onDeleteClick(int position) {
                messagesToList.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        });



    }
   /* public class MyResultReceiver extends ResultReceiver
    {
        private RecyclerView.Adapter mAdapter2;
        Handler handler= new Handler();


        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
  /*      public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(final int resultCode, final Bundle resultData) {

            if(resultCode==1 && resultData != null)
            {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String result= resultData.getString("name");
                        System.out.println("memo geldi mi :"+ result);
                    }
                });
            }


            super.onReceiveResult(resultCode, resultData);
        }
    } */



    private void startMqtt() {
        try{
            int i=0;
            //Gelen mesajları kaydettiğimiz database
            sqLiteDatabase = this.openOrCreateDatabase("musicians", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS musicians (message VARCHAR)");
            Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM musicians",null);
            int nameIx= cursor.getColumnIndex("message");
            while(cursor.moveToNext()){
                messagesToList.add(cursor.getString(nameIx));
            }
            System.out.println("Name: " + messagesToList);
            cursor.close();
            mAdapter= new MyAdapter(AfterLogInActivity.context,messagesToList);
            mAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(mAdapter);


        }catch(Exception e){

            e.printStackTrace();
        }
        mqttHelper = new MqttHelper(getApplicationContext());

        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", mqttMessage.toString());
                String messageHolder = mqttMessage.toString();
                sqLiteDatabase.execSQL("INSERT INTO musicians (message) VALUES (?)", new String [] {messageHolder});
                messagesToList.add(mqttMessage.toString());
                mAdapter= new MyAdapter(AfterLogInActivity.context,messagesToList);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}


