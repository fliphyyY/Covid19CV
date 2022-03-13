package filip.ondrusek.uv.es;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
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
        boolean isChecked = ((CheckBox) findViewById(R.id.checkBox)).isChecked();
        boolean isChecked2 = ((CheckBox) findViewById(R.id.checkBox2)).isChecked();
        boolean isChecked3 = ((CheckBox) findViewById(R.id.checkBox3)).isChecked();
        boolean isChecked4 = ((CheckBox) findViewById(R.id.checkBox4)).isChecked();
        boolean isChecked5 = ((CheckBox) findViewById(R.id.checkBox5)).isChecked();
        boolean isChecked6 = ((CheckBox) findViewById(R.id.checkBox6)).isChecked();
        boolean isChecked7 = ((CheckBox) findViewById(R.id.checkBox7)).isChecked();
        boolean isChecked8 = ((CheckBox) findViewById(R.id.checkBox8)).isChecked();
        boolean isChecked9 = ((CheckBox) findViewById(R.id.checkBox9)).isChecked();
        boolean isChecked10 = ((CheckBox) findViewById(R.id.checkBox10)).isChecked();
        boolean isChecked11 = ((CheckBox) findViewById(R.id.checkBox11)).isChecked();

        ContentValues values = new ContentValues();
        values.put(ReportContract.ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE, lname);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE, lname);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE, lname);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE, lname);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE, lname);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE, lname);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE, lname);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE, lname);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE, lname);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE, lname);
        values.put(ReportContract.ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE, lname);


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ReportContract.ReportEntry.TABLE_NAME, null, values);




    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        /*switch(view.getId()) {
            case R.id.checkbox_meat:
                if (checked)
                // Put some meat on the sandwich
            else
                // Remove the meat
                break;
            case R.id.checkbox_cheese:
                if (checked)
                // Cheese me
            else
                // I'm lactose intolerant
                break;
            // TODO: Veggie sandwich
        }*/
    }


}