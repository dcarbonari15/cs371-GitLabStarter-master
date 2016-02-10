package edu.up.cs371.textmod;

/**
 * class TextModActivity
 *
 * Allow text to be modified in simple ways with button-presses.
 */
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.Random;

public class TextModActivity extends ActionBarActivity implements View.OnClickListener {


    protected Button upperButton;





    // array-list that contains our images to display
    private ArrayList<Bitmap> images;

    // instance variables containing widgets
    private ImageView imageView; // the view that shows the image


    // spinner in bottom
    private Spinner spinner;

    // copy name button
    private Button copyName;


    protected Button clearButton;
    protected Button reverseButton;
    protected Button lowerButton;
    protected EditText editText;
    protected Button randomInsert;
    protected Button randomSwapButton;
    protected Button removeSpaceButton;

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // perform superclass initialization; load the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_mod);

        // set instance variables for our widgets
        imageView = (ImageView)findViewById(R.id.imageView);

        copyName = (Button)findViewById(R.id.copyName);

        copyName.setOnClickListener(this);

        randomInsert = (Button)findViewById(R.id.randomInsert);

        randomInsert.setOnClickListener(this);

        // Set up the spinner so that it shows the names in the spinner array resources
        //
        // get spinner object
        spinner = (Spinner)findViewById(R.id.spinner);
        // get array of strings
        String[] spinnerNames = getResources().getStringArray(R.array.spinner_names);
        // create adapter with the strings
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, spinnerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // bind the spinner and adapter
        spinner.setAdapter(adapter);

        clearButton = (Button)findViewById(R.id.clearButton);
        clearButton.setOnClickListener(this);

        reverseButton = (Button)findViewById(R.id.reverseButton);
        reverseButton.setOnClickListener(this);

        lowerButton = (Button)findViewById(R.id.lowerButton);
        lowerButton.setOnClickListener(this);

        randomSwapButton = (Button)findViewById(R.id.RandomSwapButton);
        randomSwapButton.setOnClickListener(this);

        removeSpaceButton = (Button)findViewById(R.id.removeSpaceButton);
        removeSpaceButton.setOnClickListener(this);

        editText = (EditText)findViewById(R.id.editText);

        // load the images from the resources
        //
        // create the arraylist to hold the images
        images = new ArrayList<Bitmap>();
        // get array of image-resource IDs
        TypedArray imageIds2 = getResources().obtainTypedArray(R.array.imageIdArray);
        // loop through, adding one image per string
        for (int i = 0; i < spinnerNames.length; i++) {
            // determine the index; use 0 if out of bounds
            int id = imageIds2.getResourceId(i,0);
            if (id == 0) id = imageIds2.getResourceId(0,0);
            // load the image; add to arraylist
            Bitmap img = BitmapFactory.decodeResource(getResources(), id);
            images.add(img);

            upperButton =(Button) findViewById(R.id.upperButton);
            upperButton.setOnClickListener(this);
            editText =(EditText) findViewById(R.id.editText);


        }

        // define a listener for the spinner
        spinner.setOnItemSelectedListener(new MySpinnerListener());

    }

    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_text_mod, menu);
        return true;
    }

    /**
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
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



    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.clearButton) {
            editText.setText("");
        } else if (v.getId() == R.id.reverseButton) {
            String orig = editText.getText().toString();
            String updated = "";
            for (int i = orig.length(); i > 0; i--) {
                updated = updated + orig.substring(i - 1, i);
            }
            editText.setText(updated);
        } else if (v.getId() == R.id.lowerButton) {
            String str = editText.getText().toString().toLowerCase();
            ;
            editText.setText(str);
        } else if (v == upperButton) {

            String userText = editText.getText().toString();
            userText = userText.toUpperCase();
            editText.setText(userText);
        } else if (v.getId() == R.id.copyName) {
            editText.setText(editText.getText() + spinner.getSelectedItem().toString());
        }else if (v.getId() == R.id.removeSpaceButton){
            String str = editText.getText().toString();
            String updated = "";
            for(int i = 0; i < str.length(); i++){
                String chara = str.substring(i,i+1);
                if(chara.equals(" ")){
                    updated = updated;
                }else{
                    updated = updated + str.substring(i,i+1);
                }
            }
            editText.setText(updated);
        } else if (v.getId() == R.id.RandomSwapButton) {
            String old = editText.getText().toString();
            int swapLocal = (int) (editText.length() * Math.random());
            String front = old.substring(0,swapLocal);
            String back = old.substring(swapLocal, old.length());
            editText.setText(back + front);
        } else if (v.getId() == R.id.randomInsert){
            String str = editText.getText().toString();
            int insertLoc = (int)(editText.getText().length() * Math.random());
            Random rand = new Random();
            String printableChars = "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()_+QWERTYUIOP[]|ASDFGHJKL:\"zxcvbnm<>?";
            char insertChar = printableChars.charAt(rand.nextInt(printableChars.length()));
            editText.setText(str.substring(0,insertLoc) + insertChar + str.substring(insertLoc,editText.getText().toString().length()));
        }
    }



   /*
     * class that handles our spinner's selection events
     */
    private class MySpinnerListener implements OnItemSelectedListener {

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
         *                  android.widget.AdapterView, android.view.View, int, long)
         */
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                   int position, long id) {
            // set the image to the one corresponding to the index selected by the spinner
            imageView.setImageBitmap(images.get(position));
        }

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(
         *                  android.widget.AdapterView)
         */
        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // your code here
        }
    }
}
