package cs491.project.NJFTRUS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;


public class Calendar extends AppCompatActivity {

    private mySQLiteDBHandler databaseHandler;
    private EditText editEventText;
    private EditText editLocationText;
    private CalendarView calView;
    private String dateSelected;
    private SQLiteDatabase sqLiteDB;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar2);

        editEventText = findViewById(R.id.editEventText);
        editLocationText = findViewById(R.id.editLocationText);
        calView = findViewById(R.id.calendarView3);
        //Toast.makeText(Calendar.this, "Event successfully saved!", Toast.LENGTH_SHORT).show();
        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {



            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dateSelected = Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth);
                ReadFromDatabase(view);
            }
        });


        try{
            databaseHandler = new mySQLiteDBHandler(this, "CalendarDatabase", null, 1);
            sqLiteDB = databaseHandler.getWritableDatabase();
            sqLiteDB.execSQL("CREATE TABLE EventCalendar(Date TEXT, Event TEXT)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void InsertIntoDatabase(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date",dateSelected);
        contentValues.put("Event", editEventText.getText().toString());
        sqLiteDB.insert("EventCalendar", null, contentValues);
        Toast.makeText(Calendar.this, "Event successfully saved!", Toast.LENGTH_SHORT).show();
    }

    public void ReadFromDatabase(View view){
        String query = "Select Event from EventCalendar where Date =" + dateSelected;
        try{
            Cursor cursor = sqLiteDB.rawQuery(query, null);
            // cursor.moveToFirst();
            cursor.moveToLast();
            editEventText.setText(cursor.getString(0));
        }
        catch (Exception e){
            e.printStackTrace();
            editEventText.setText("");
        }
    }



}