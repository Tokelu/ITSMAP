package dk.witzell.l03thereandbackagain;


/*
*   1. Create a new application with two activities: MainActivity and SecondActivity
*   2. Create a Button in you MainActivity user interface that takes the user to SecondActivity.
*   3. SecondActivity must have a Button where the user returns to the MainActivity
*/

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{


    TextView mainTextView;
    Button mainPageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainTextView = findViewById(R.id.pageOneTextView);

        mainPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent.
            }
        });




    }




    }

