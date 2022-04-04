package filip.ondrusek.uv.es;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class AddReport extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
   private String[] optionsSpinner = { "No" , "Yes" };
   private Calendar calendar;
   private TextView date;
   private  CheckBox feverChills, cough, breathing, fatigue, bodyAches, headache, lossTaste, soreThroat, congestionRunnyNose, nauseaVomiting, diarrhea;
   private int year, month, day;
   private EditText diagnosticCode;
   private ArrayList<String> municipalityNamesList = new ArrayList<>();
   private ArrayAdapter<String> arrayAdapter;
   private ListView listView;
   private EditText etSearch;
   private Spinner closeContactSpinner;
   private String closeContactValue, municipalitySearch, flag;
   private MunicipalityDetailActivity municipalityDetail = new MunicipalityDetailActivity();
   private Button saveButton, deleteButton;
   private Municipality municipality;
   private Report report;
   private ReportDbHelper reportDbHelper;
   private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView textView = findViewById(R.id.textViewHeadlineNewReport);
        date = findViewById(R.id.date);
        reportDbHelper = new ReportDbHelper(this);
        database = reportDbHelper.getWritableDatabase();


        setCurrentDate();

        Button saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
        diagnosticCode = findViewById(R.id.diagnosticCode);
        diagnosticCode.setInputType(InputType.TYPE_CLASS_NUMBER);

        etSearch = findViewById(R.id.municipalitySearch);

        checkBoxInitialization();
        spinnerInitialization();
        diagnosticCode = findViewById(R.id.diagnosticCode);
        diagnosticCode.setOnClickListener(v -> {
        });

        diagnosticCode.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });


        flag = (String) getIntent().getSerializableExtra("flag");
        deleteButton.setText("Delete");
        if(flag.equals("MainActivity")) {
            municipalityNamesList = (ArrayList<String>) getIntent().getSerializableExtra("municipalityNamesList");
            textView.setText("Creating new report");
            generateRandomDiagnosticCode();
            saveButton.setText("Save");
            deleteButton.setEnabled(false);
        }
        else if(flag.equals("MunicipalityDetail")) {
            municipality = (Municipality) getIntent().getSerializableExtra("municipality");
            this.municipalitySearch = municipality.getMunicipality();
            textView.setText("Creating new report for " + municipality.getMunicipality());
            generateRandomDiagnosticCode();
            setMunicipalitySearch(this.municipalitySearch);
            saveButton.setText("Save");
            deleteButton.setEnabled(false);
        }

        else {
            report = (Report) getIntent().getSerializableExtra("report");
            municipality = (Municipality) getIntent().getSerializableExtra("municipality");
            this.municipalitySearch = municipality.getMunicipality();
            textView.setText("Editing the report");
            setReportValues();
            saveButton.setText("Update");
            this.deleteButton.setOnClickListener(v -> {
                        deleteReport();
                Toast.makeText(getApplicationContext(),"Report successfully deleted!",Toast.LENGTH_LONG).show();
                openMunicipalityDetailActivity(municipality);
                    }
            );
        }

        listView = findViewById(R.id.municipalityListView);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, municipalityNamesList);
        listView.setAdapter(arrayAdapter);
        this.saveButton = findViewById(R.id.saveButton);
        this.saveButton.setOnClickListener(v -> {
            flag = (String) getIntent().getSerializableExtra("flag");
            boolean correctDiagnosticCode = inputValuesHandling();
            if(flag.equals("ReportEditing"))
            {
                if(correctDiagnosticCode) {
                    updateReport();
                    Toast.makeText(getApplicationContext(),"Report successfully updated!",Toast.LENGTH_LONG).show();
                }
            }
            else {
                if(correctDiagnosticCode) {
                    insertNewReport(v);
                    Toast.makeText(getApplicationContext(),"New report successfully created!",Toast.LENGTH_LONG).show();
                }
            }
              if(municipality == null) {
                  if(correctDiagnosticCode) {
                      openMainActivity();
                  }
            }

            else {
                  if(correctDiagnosticCode) {
                      openMunicipalityDetailActivity(municipality);
                  }
            }
        });

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
            etSearch.setText(arrayAdapter.getItem(position));
            this.municipalitySearch = arrayAdapter.getItem(position);

            if (inputMethodManager.isAcceptingText()) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
        showDate(arg1, arg2+1, arg3);
    };

    private void showDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        date.setText(new StringBuilder().append(this.day).append("/")
                .append(this.month).append("/").append(this.year));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.closeContactValue = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void checkBoxInitialization()
    {
        feverChills =  findViewById(R.id.feverChills);
        cough =  findViewById(R.id.cough);
        breathing =  findViewById(R.id.breathing);
        fatigue =  findViewById(R.id.fatigue);
        bodyAches =  findViewById(R.id.bodyAches);
        headache =  findViewById(R.id.headache);
        lossTaste =  findViewById(R.id.lossTaste);
        soreThroat =  findViewById(R.id.soreThroat);
        congestionRunnyNose =  findViewById(R.id.congestionRunnyNose);
        nauseaVomiting =  findViewById(R.id.nauseaVomiting);
        diarrhea =  findViewById(R.id.diarrhea);
    }

    private void spinnerInitialization()
    {
        closeContactSpinner = findViewById(R.id.closeContact);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        closeContactSpinner.setAdapter(adapter);
        closeContactSpinner.setOnItemSelectedListener(this);
    }

    private void setValueCheckboxes(boolean feverChills, boolean cough, boolean breathing, boolean fatigue, boolean bodyAches, boolean headache, boolean lossTaste, boolean soreThroat, boolean congestionRunnyNose, boolean nauseaVomiting, boolean diarrhea)
    {
        this.feverChills.setChecked(feverChills);
        this.cough.setChecked(cough);
        this.breathing.setChecked(breathing);
        this.fatigue.setChecked(fatigue);
        this.bodyAches.setChecked(bodyAches);
        this.headache.setChecked(headache);
        this.lossTaste.setChecked(lossTaste);
        this.soreThroat.setChecked(soreThroat);
        this.congestionRunnyNose.setChecked(congestionRunnyNose);
        this.nauseaVomiting.setChecked(nauseaVomiting);
        this.diarrhea.setChecked(diarrhea);
    }

    private void setCurrentDate()
    {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
    }

    private void setMunicipalitySearch(String municipalityName)
    {
        etSearch.setText(municipalityName);
        etSearch.setFocusable(false);
    }

    private String[] getReportActivityValues()
    {
        String diagnosticCodeS = this.diagnosticCode.getText().toString();
        String dateS = new StringBuilder().append(this.day).append("/")
                .append(this.month).append("/").append(this.year).toString();
        String isCheckedS =  new Boolean((feverChills).isChecked()).toString();
        String coughS = new Boolean((cough).isChecked()).toString();
        String breathingS = new Boolean((breathing).isChecked()).toString();
        String fatigueS = new Boolean((fatigue).isChecked()).toString();
        String bodyAchesS = new Boolean((bodyAches).isChecked()).toString();
        String headacheS = new Boolean((headache).isChecked()).toString();
        String lossTasteS = new Boolean((lossTaste).isChecked()).toString();
        String soreThroatS = new Boolean((soreThroat).isChecked()).toString();
        String congestionRunnyNoseS = new Boolean((congestionRunnyNose).isChecked()).toString();
        String nauseaVomitingS = new Boolean((nauseaVomiting).isChecked()).toString();
        String diarrheaS = new Boolean((diarrhea).isChecked()).toString();
        return new String[] {diagnosticCodeS, dateS, isCheckedS, coughS, breathingS, fatigueS, bodyAchesS, headacheS, lossTasteS, soreThroatS, congestionRunnyNoseS, nauseaVomitingS, diarrheaS};
    }

    private ContentValues reportValues()
    {
        String[] reportActivityValues = getReportActivityValues();
        ContentValues reportValues = new ContentValues();
        reportValues.put(ReportContract.ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE, reportActivityValues[0]);
        reportValues.put(ReportContract.ReportEntry.COLUMN_NAME_SYMPTOMS_START_DATE, reportActivityValues[1]);
        reportValues.put(ReportContract.ReportEntry.COLUMN_NAME_FEVER_CHILLS, reportActivityValues[2]);
        reportValues.put(ReportContract.ReportEntry.COLUMN_NAME_COUGH, reportActivityValues[3]);
        reportValues.put(ReportContract.ReportEntry.COLUMN_NAME_BREATHING, reportActivityValues[4]);
        reportValues.put(ReportContract.ReportEntry.COLUMN_NAME_FATIGUE, reportActivityValues[5]);
        reportValues.put(ReportContract.ReportEntry.COLUMN_NAME_MUSCLE_BODY_ACHES, reportActivityValues[6]);
        reportValues.put(ReportContract.ReportEntry.COLUMN_NAME_HEADACHE, reportActivityValues[7]);
        reportValues.put(ReportContract.ReportEntry.COLUMN_NAME_TASTE_SMELL_LOSS, reportActivityValues[8]);
        reportValues.put(ReportContract.ReportEntry.COLUMN_NAME_SORE_THROAT, reportActivityValues[9]);
        reportValues.put(ReportContract.ReportEntry.COLUMN_NAME_CONGESTION_RUNNY_NOSE, reportActivityValues[10]);
        reportValues.put(ReportContract.ReportEntry.COLUMN_NAME_NAUSEA_VOMITING, reportActivityValues[11]);
        reportValues.put(ReportContract.ReportEntry.COLUMN_NAME_DIARRHEA, reportActivityValues[12]);
        reportValues.put(ReportContract.ReportEntry.COLUMN_NAME_CLOSE_CONTACT, this.closeContactValue);
        reportValues.put(ReportContract.ReportEntry.COLUMN_NAME_MUNICIPALITY, this.municipalitySearch);
        return reportValues;
    }

    private void openMunicipalityDetailActivity(Municipality municipality) {
        Intent intent = new Intent(this,MunicipalityDetailActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("municipality", municipality);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void setReportValues() {
        diagnosticCode.setText(String.valueOf(report.getDiagnosticCode()));
        String date = report.getDate();
        String[] arrOfStr = date.split("/", 3);

        try {
            showDate(Integer.parseInt(arrOfStr[2]), Integer.parseInt(arrOfStr[1]), Integer.parseInt(arrOfStr[0]));
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid String");
        }

        setValueCheckboxes(new Boolean((report.getFeverChills())), new Boolean((report.getCough())), new Boolean((report.getBreathing())), new Boolean((report.getFatigue())), new Boolean((report.getBodyAches())), new Boolean((report.getHeadache())), new Boolean((report.getLossTaste())), new Boolean((report.getSoreThroat())), new Boolean((report.getCongestionRunnyNose())), new Boolean((report.getNauseaVomiting())), new Boolean((report.getDiarrhea())));
        if(report.getCloseContact().equals("Yes"))
        {
            closeContactSpinner.setSelection(1);
        }
        else {
            closeContactSpinner.setSelection(0);
        }

        setMunicipalitySearch(report.getMunicipality());
    }

    private void openMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private boolean inputValuesHandling()
    {
        String diagnosticCode = this.diagnosticCode.getText().toString();
        if(diagnosticCode.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Diagnostic code not entered!",Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(diagnosticCode.length() < 8)
        {
            Toast.makeText(getApplicationContext(),"Length of diagnostic code must be 8 digits!",Toast.LENGTH_LONG).show();
            return false;
        }

        else if(this.municipalitySearch == null)
        {
            Toast.makeText(getApplicationContext(),"The municipality wasn't picked!",Toast.LENGTH_LONG).show();
            return false;
        }

        else if(municipalityDetail.getRowByDiagnosticCode(diagnosticCode, reportDbHelper).getCount() > 0 && !flag.equals("ReportEditing"))
        {
            Toast.makeText(getApplicationContext(),"Report with the same  diagnostic code already exists!",Toast.LENGTH_LONG).show();
            return false;
        }

        else if(((flag.equals("ReportEditing") && municipalityDetail.getRowByDiagnosticCode(diagnosticCode, reportDbHelper).getCount() > 0) && (!diagnosticCode.equals(this.report.getDiagnosticCode()))))
        {
            Toast.makeText(getApplicationContext(),"Report with the same  diagnostic code already exists!",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void insertNewReport(View view)
    {
        reportDbHelper.insertReport(database, reportValues());
    }

    private void updateReport()
    {
        reportDbHelper.updateReport(database, reportValues(), this.diagnosticCode.getText().toString());
    }

    private void deleteReport()
    {
        reportDbHelper.deleteReport(database, this.report.getDiagnosticCode());
       // reportDbHelper.onDelete(database);
    }


    private void generateRandomDiagnosticCode()
    {
        Cursor cursor;
        String code;

        do {
            code = Integer.toString(generateRandomDigits());
            cursor = municipalityDetail.getRowByDiagnosticCode(code, reportDbHelper);

        } while (cursor.getCount() > 1);
        this.diagnosticCode.setText(code);
    }

    private int generateRandomDigits() {
        int m = (int) Math.pow(10, 8 - 1);
        return m + new Random().nextInt(9 * m);
    }
}