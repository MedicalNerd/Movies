package sg.edu.rp.c346.id22021421.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnInsert, btnGetSongs;
    EditText etSong , etDate , etName;

    Spinner sp;



    ArrayList<Movie> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInsert = findViewById(R.id.btnInsert);
        btnGetSongs = findViewById(R.id.btnGetSongs);
        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDates);
        etSong = findViewById(R.id.etSong);
        sp = findViewById(R.id.sp);
        al = new ArrayList<Movie>();

        DBHelper db = new DBHelper(MainActivity.this);
        ArrayList<Movie> sList = db.get_Songs();
        al.addAll(sList);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                int date = Integer.parseInt(etDate.getText().toString());

                String song = etSong.getText().toString();
                String name = etName.getText().toString();
                String ratings = sp.getSelectedItem().toString();



                long insert_song = db.insertSongs(song,date,name,ratings);
                al.clear();
                al.addAll(db.get_Songs());


            }
        });
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnGetSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);


                // Pass the entire list of songs to the ListActivity
                intent.putExtra("SONG_LIST", al);


                // Start the ListActivity
                startActivity(intent);





            }
        });
    }

}