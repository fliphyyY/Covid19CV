package filip.ondrusek.uv.es;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;

public class AddReport extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String[] options = { "No" , "Yes" };
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
   // CheckBox checkBox,checkBox2,checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10, checkBox11;
    Button buttonOrder;
    EditText editText;
    ArrayList<String> myList = new ArrayList<>();
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    EditText etSearch;
    Spinner spin;
   private String dropdown;
   private String municipalitySearch;
   private MunicipalityDetail municipalityDetail = new MunicipalityDetail();
   private Button button;
   private Municipality municipality;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        // checkBox = findViewById(R.id.checkBox);
       /* checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);
        checkBox7 = findViewById(R.id.checkBox7);
        checkBox8 = findViewById(R.id.checkBox8);
        checkBox9 = findViewById(R.id.checkBox9);
        checkBox10 = findViewById(R.id.checkBox10);
        checkBox11 = findViewById(R.id.checkBox11);*/

        buttonOrder = findViewById(R.id.button);
        editText = findViewById(R.id.editText);

        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        myList = (ArrayList<String>) getIntent().getSerializableExtra("municipalityName");
        municipality = (Municipality) getIntent().getSerializableExtra("municipality");
        setContentView(R.layout.activity_add_report);
        listView = findViewById(R.id.listView0);
        etSearch = findViewById(R.id.etSearch);




        spin = (Spinner) findViewById(R.id.spinner0);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        dateView = (TextView) findViewById(R.id.textView1);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, myList);
        listView.setAdapter(arrayAdapter);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(v -> {
              writeToDatabase(v);
              if(municipality == null) {
                openMainActivity();
            }

            else {
                openMunicipalityDetailActivity(municipality);
            }
        }
        );

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayAdapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        if(myList.size() == 1) {
            this.municipalitySearch =myList.get(0);
            etSearch.setText(municipalitySearch);
        }

        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            // TODO Auto-generated method stub
            this.municipalitySearch =arrayAdapter.getItem(position);
            etSearch.setText(municipalitySearch);
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = (arg0, arg1, arg2, arg3) -> {
        // TODO Auto-generated method stub
        // arg1 = year
        // arg2 = month
        // arg3 = day
        showDate(arg1, arg2+1, arg3);
    };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

   /* @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.dropdown = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void writeToDatabase(View view) {
        ReportDbHelper reportDbHelper = new ReportDbHelper(this);
            SQLiteDatabase db = reportDbHelper.getWritableDatabase();
        String lname = ((EditText)findViewById(R.id.editText)).getText().toString();
        String date = new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year).toString();
        String isChecked =  new Boolean(((CheckBox) findViewById(R.id.checkBox)).isChecked()).toString();
        String isChecked2 = new Boolean(((CheckBox) findViewById(R.id.checkBox2)).isChecked()).toString();
        String isChecked3 = new Boolean(((CheckBox) findViewById(R.id.checkBox3)).isChecked()).toString();
        String isChecked4 = new Boolean(((CheckBox) findViewById(R.id.checkBox4)).isChecked()).toString();
        String isChecked5 = new Boolean(((CheckBox) findViewById(R.id.checkBox5)).isChecked()).toString();
        String isChecked6 = new Boolean(((CheckBox) findViewById(R.id.checkBox6)).isChecked()).toString();
        String isChecked7 = new Boolean(((CheckBox) findViewById(R.id.checkBox7)).isChecked()).toString();
        String isChecked8 = new Boolean(((CheckBox) findViewById(R.id.checkBox8)).isChecked()).toString();
        String isChecked9 = new Boolean(((CheckBox) findViewById(R.id.checkBox9)).isChecked()).toString();
        String isChecked10 = new Boolean(((CheckBox) findViewById(R.id.checkBox10)).isChecked()).toString();
        String isChecked11 = new Boolean(((CheckBox) findViewById(R.id.checkBox11)).isChecked()).toString();

        ContentValues values = new ContentValues();
        values.put(ReportContract.ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE, lname);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_SYMPTOMS_START_DATE, date);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_FEVER_CHILLS, isChecked);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_COUGH, isChecked2);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_BREATHING, isChecked3);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_FATIGUE, isChecked4);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_MUSCLE_BODY_ACHES, isChecked5);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_HEADACHE, isChecked6);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_TASTE_SMELL_LOSS, isChecked7);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_SORE_THROAT, isChecked8);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_CONGESTION_RUNNY_NOSE, isChecked9);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_NAUSEA_VOMITING, isChecked10);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_DIARRHEA, isChecked11);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_CLOSE_CONTACT, this.dropdown);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_MUNICIPALITY, this.municipalitySearch);
        // Insert the new row, returning the primary key value of the new row
        db.insert(ReportContract.ReportEntry.TABLE_NAME, null, values);
     //   Cursor newCursor = municipalityDetail.getAllItems(this.municipalitySearch, reportDbHelper);
       // municipalityDetail.getmAdapter().swapCursor(newCursor);
    }

    private void openMunicipalityDetailActivity(Municipality municipality) {
         ArrayList<String> municipalityName = new ArrayList<>();
        municipalityName.add(this.municipalitySearch);
        Intent intent = new Intent(this,MunicipalityDetail.class);
        Bundle b = new Bundle();
        b.putSerializable("municipality", municipality);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void openMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}