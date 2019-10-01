package dk.witzell.l04uidemos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Tag constant for logging
    public static final String TAG = "Main Activity LifecycleEvents";

    //Declare UI Widgets
    private TextView    txtViewMain;
    private Button      btnMainPickerDemo;
    private Button      btnMainEditTextDemo;
    private Button      btnMainSlidersDemo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate Called");

        //Get UI widgets
        btnMainPickerDemo   = findViewById(R.id.btnMainPickerDemo);
        btnMainSlidersDemo  = findViewById(R.id.btnMainSlidersDemo);
        btnMainEditTextDemo = findViewById(R.id.btnMainEditTextDemo);


        //Set up buttons with click listeners
        btnMainPickerDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivityForResult(Intent.);
                startActivity(new Intent(MainActivity.this, PickerDemoActivity.class));

            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy()
    {
        Log.d(TAG,"!!!onDestroy!!!");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        //Save the state:
        //outState.putBoolean(KEY_IS_SWAPPED, swapped);
        super.onSaveInstanceState(outState);
    }
}
