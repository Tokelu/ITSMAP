package dk.witzell.l04uidemos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class PickerDemoActivity extends AppCompatActivity {

    //Tag constant for logging
    public static final String TAG = "Picker Demo Events";

    //Declare widgets
    private NumberPicker npPickerDemo;
    private TextView    txtViewPickerDemo;
    private Button      btnPickerDemoOK;
    private Button      btnPickerDemoCancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_demo);


        //Get UI widgets
        btnPickerDemoOK     = findViewById(R.id.btnPickerDemoOK);
        btnPickerDemoCancel = findViewById(R.id.btnPickerDemoCancel);
        npPickerDemo        = findViewById(R.id.npPickerDemo);


        //Setting min/max values for picker
        npPickerDemo.setMinValue(1);
        npPickerDemo.setMaxValue(1000);

        //Creating listener for onValueChanged
        npPickerDemo.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int valuePicker1 = npPickerDemo.getValue();
                Log.d(TAG, "picker value" + valuePicker1 + "");
            }
        });


    }
}
