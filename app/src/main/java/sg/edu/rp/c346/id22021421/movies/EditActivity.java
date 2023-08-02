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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    TextView tvID;
    EditText etName,etSongs,etDates;
    Button btnUpdate, btnDelete,btnCancel;
    Movie data;
    Spinner sp;
    ArrayAdapter<String> adapterSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit2);
        tvID = findViewById(R.id.tvID);
        etName = findViewById(R.id.etName);
        etDates = findViewById(R.id.etDates);
        etSongs = findViewById(R.id.etSong);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);
        sp = findViewById(R.id.sp);

        Intent intent = getIntent();
        data = (Movie) intent.getSerializableExtra("data");

        // Populate the text fields with the selected Song's data
        tvID.setText("ID: " + String.valueOf(data.getId()));

        etName.setText(data.getname());
        etSongs.setText(data.getGenre());
        etDates.setText(String.valueOf(data.getDate()));
        ArrayList<String> dateList = new ArrayList<>();
        DBHelper db = new DBHelper(EditActivity.this);
        ArrayList<String> years = db.getYears();
        dateList.addAll(years);
        adapterSpinner = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,dateList);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapterSpinner);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                data.setName(etName.getText().toString());
                int date = Integer.parseInt(etDates.getText().toString());
                data.setDate(date);
                data.setGenre(etSongs.getText().toString());
                data.setRatings(sp.getSelectedItem().toString());


                dbh.updateSong(data);

                dbh.close();
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(EditActivity.this);
                db.deleteSong(data.getId());

                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
