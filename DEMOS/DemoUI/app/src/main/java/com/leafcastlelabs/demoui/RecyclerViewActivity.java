package com.leafcastlelabs.demoui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import com.leafcastlelabs.demoui.adaptors.CardDisplayAdaptor;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView rcvList;
    private RecyclerView.Adapter adaptor;
    private RecyclerView.LayoutManager layoutMan;

    private Button btnOK, btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        btnOK = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);
        rcvList = (RecyclerView) findViewById(R.id.rcvCardList);

        layoutMan = new LinearLayoutManager(this);
        rcvList.setLayoutManager(layoutMan);

        String[] cardTitles = new String[200];
        for(int i=0; i<cardTitles.length; i++) {
            cardTitles[i] = "Card "+(i+1);
        }

        adaptor = new CardDisplayAdaptor(cardTitles);
        rcvList.setAdapter(adaptor);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }
}
