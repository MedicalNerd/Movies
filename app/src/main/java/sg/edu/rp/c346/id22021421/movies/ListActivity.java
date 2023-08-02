package sg.edu.rp.c346.id22021421.movies;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ListView lvShow;

    //ArrayAdapter<Song> adapterSong;
    ArrayAdapter<String> adapterSpinner;
    ArrayList<Movie> songList;
    ArrayList<Movie> filteredList;
    boolean runOnresume;
    Spinner spYear;
    CustomAdapter adapterSong;

    Button btnStars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        runOnresume = false;
        lvShow = findViewById(R.id.lv);
        btnStars = findViewById(R.id.btn5star);
        spYear = findViewById(R.id.spYear);


        Intent intent = getIntent();
        songList = (ArrayList<Movie>) intent.getSerializableExtra("SONG_LIST");
        Log.i("test", String.valueOf(songList.size()));


        ArrayList<String> dateList = new ArrayList<>();
        DBHelper db = new DBHelper(ListActivity.this);
        ArrayList<String> years = db.getYears();
        dateList.addAll(years);

        adapterSpinner = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,dateList);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spYear.setAdapter(adapterSpinner);
        // Set up the adapter and display the song list
        adapterSong = new CustomAdapter(this, R.layout.row, songList);
        lvShow.setAdapter(adapterSong);


        btnStars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filteredList = new ArrayList<>();
                String adapterselected = spYear.getSelectedItem().toString();
                for (Movie Movie : songList) {
                    if (Movie.getRatings().equals(adapterselected)) {
                        filteredList.add(Movie);
                    }
                }

                adapterSong.clear();
                adapterSong.addAll(filteredList);
                adapterSong.notifyDataSetChanged();
            }
        });
        lvShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedSong = songList.get(position);
                Intent i = new Intent(ListActivity.this,
                        EditActivity.class);
                i.putExtra("selectedRating", selectedSong.getRatings());
                i.putExtra("data", selectedSong);
                startActivity(i);
            }
        });

        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedYear = parent.getItemAtPosition(position).toString();
                Log.i("test", selectedYear);
                DBHelper dbh = new DBHelper(ListActivity.this);
                ArrayList<Movie> filteredSongs = filterSongsByYear(selectedYear);
                Log.i("test", String.valueOf(filteredSongs.size()));

                adapterSong.addAll(filteredSongs);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        DBHelper db = new DBHelper(ListActivity.this);

        adapterSong.clear();
        adapterSong.addAll(db.get_Songs());



        adapterSong.notifyDataSetChanged();
    }
    private ArrayList<Movie> filterSongsByYear(String selectedYear) {
        ArrayList<Movie> filteredSongs = new ArrayList<>();
        DBHelper db = new DBHelper(ListActivity.this);
        ArrayList<Movie> songs = db.get_Songs();

        for (Movie song : songs) {
            if (String.valueOf(song.getRatings()).equals(selectedYear)) {
                filteredSongs.add(song);
            }
        }

        return filteredSongs;
    }




}
