package com.leafcastlelabs.demoui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.leafcastlelabs.demoui.adaptors.DemoAdaptor;
import com.leafcastlelabs.demoui.model.Demo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_DEMO_PICKER = 100;
    private static final int REQUEST_CODE_DEMO_EDIT_TEXT = 101;
    private static final int REQUEST_CODE_DEMO_DEMO_SLIDERS_COLOR = 102;
    private static final int REQUEST_CODE_DEMO_DEMO_WEB = 103;
    private static final int REQUEST_CODE_DEMO_DEMO_IMAGE_SWITCHER = 104;
    private DemoAdaptor demoAdaptor;
    private ListView demoListView;
    private ArrayList<Demo> demoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //make demo list of 1000 placeholder demos and a custom demos in the beginning of that list
        demoList = initDemos();

        //create our adaptor and attach to ListView
        demoAdaptor = new DemoAdaptor(this, demoList);
        demoListView = (ListView) findViewById(R.id.listView);
        demoListView.setAdapter(demoAdaptor);

        //react to clicks on specific list items
        demoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent startDemoIntent = new Intent();
                //startDemoIntent.putExtra("position", position);
                String action = demoList.get(position).getIntentAction();
                int demoResultCode = demoList.get(position).getResultCode();
                if (action != null && !action.equals("")) {
                    startDemoIntent.setAction(action);
                    startActivityForResult(startDemoIntent, demoResultCode);
                }
            }
        });

        demoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "You long pressed the " + demoList.get(position).getName() + "demo!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private ArrayList<Demo> initDemos() {
        final ArrayList<Demo> demos = new ArrayList<Demo>();
        for (int i = 0; i < 1000; i++) {
            demos.add(new Demo("Demo #" + (i + 1), "Demo #" + (i + 1) + " is a great demo"));
        }

        //add the actual demo activities with name, description, intent and request code
        //note that implicit intents are used! See manifest and model/Demo.java
        demos.set(0, new Demo("Picker", "This is a demo of using Pickers", "com.leafcastlelabs.demoui.DEMO_PICKER", REQUEST_CODE_DEMO_PICKER));
        demos.set(1, new Demo("EditText", "Shows verious EditText inputs", "com.leafcastlelabs.demoui.DEMO_EDIT_TEXT", REQUEST_CODE_DEMO_EDIT_TEXT));
        demos.set(2, new Demo("Sliders&Color", "Demonstrates sliders and color", "com.leafcastlelabs.demoui.DEMO_SLIDERS_COLOR", REQUEST_CODE_DEMO_DEMO_SLIDERS_COLOR));
        demos.set(3, new Demo("WebView", "Showcasing a web view", "com.leafcastlelabs.demoui.DEMO_WEB", REQUEST_CODE_DEMO_DEMO_WEB));
        demos.set(4, new Demo("ImageSwitcher", "Showcasing an ImageSwitcher with animation", "com.leafcastlelabs.demoui.DEMO_IMAGE_SWITCHER", REQUEST_CODE_DEMO_DEMO_IMAGE_SWITCHER));
        demos.set(5, new Demo("Recyclerview+Cards", "Showcasing a RecyclerView and CardViews", "com.leafcastlelabs.demoui.DEMO_RECYCLERVIEW", REQUEST_CODE_DEMO_DEMO_IMAGE_SWITCHER));
        return demos;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //for handling when the user clicks items in the menu
        int id = item.getItemId();
        boolean overrideDefaultHandling = false;
        switch (id) {
            case R.id.action_exit:
                overrideDefaultHandling = true;
                finish();

                break;
            case R.id.action_settings:
                overrideDefaultHandling = true;
                break;
            case R.id.action_main_thing: //search
                overrideDefaultHandling = true;
                createSearchPopup();
                break;

        }
        if (overrideDefaultHandling) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void createSearchPopup() {

        //create a dialog using the builder pattern
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //because we want to use a custom layout we need to first inflate it
        LayoutInflater inflater = getLayoutInflater();
        final View v = inflater.inflate(R.layout.search_view, null);

        //setting up the dialog, not that we use setView(v) to put the custom layout
        builder.setMessage(R.string.dialog_message)
                .setView(v)
                .setTitle(R.string.dialog_title)
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        EditText edtSearch = (EditText) v.findViewById(R.id.edtSearch);
                        if (edtSearch != null) {
                            //get text string and call method for search
                            doSearch(edtSearch.getText().toString());
                        }
                    }
                });

        //use builder to create the dialog object and then show it
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void doSearch(String searchFor) {
        //TODO: make search here

        Toast.makeText(MainActivity.this, "Searching for " + searchFor, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //react differently when returning from different demos
        switch (requestCode) {
            case REQUEST_CODE_DEMO_PICKER:
                if (resultCode == RESULT_OK) {
                    String combination = data.getStringExtra("combination");
                    if (combination != null) {
                        Toast.makeText(this, "Awesome, you picked: " + combination, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case REQUEST_CODE_DEMO_EDIT_TEXT:
                if (resultCode == RESULT_OK) {
                    String plain = data.getStringExtra("text_plain");
                    String email = data.getStringExtra("text_email");
                    String number = data.getStringExtra("text_number");
                    String password = data.getStringExtra("text_password");
                    if (plain != null && email != null && number != null && password != null) {
                        if (!plain.equals("") || !email.equals("") || !number.equals("") || !password.equals("")) {
                            Toast.makeText(this, "Awesome, you picked:\n" + plain + "\n"
                                    + email + "\n"
                                    + number + "\n"
                                    + password, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "Enter at least one value ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("EDIT_TEXT", "Strings should not be zero");

                    }
                }
                break;
            case REQUEST_CODE_DEMO_DEMO_SLIDERS_COLOR:
                if (resultCode == RESULT_OK) {
                    String color = data.getStringExtra("color");
                    if (color != null) {
                        Toast.makeText(this, "Awesome, you chose the color: " + color, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case REQUEST_CODE_DEMO_DEMO_IMAGE_SWITCHER:
                if (resultCode == RESULT_OK) {

                }
                break;
            default:

                break;
        }
    }
}
