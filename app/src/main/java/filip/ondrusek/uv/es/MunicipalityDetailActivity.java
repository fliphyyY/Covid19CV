package filip.ondrusek.uv.es;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MunicipalityDetailActivity extends AppCompatActivity {
    private Municipality municipality;
    private ReportAdapter mAdapter;
    private SQLiteDatabase db;
    private final ArrayList<String> mArrayList = new ArrayList<String>();
    private final ReportDbHelper reportDbHelper = new ReportDbHelper(this);
    private Report report;


    private final View.OnClickListener onItemClickListener = view -> {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();
        String reportCode = mArrayList.get(position);
        Cursor cursor = getRowByDiagnosticCode(reportCode, reportDbHelper);
        createReportObject(cursor);
        Intent intent = new Intent(MunicipalityDetailActivity.this, AddReport.class);
        Bundle b = new Bundle();
        b.putSerializable("flag", "ReportEditing");
        intent.putExtras(b);
        b.putSerializable("report", report);
        intent.putExtras(b);
        b.putSerializable("municipality", municipality);
        intent.putExtras(b);
        startActivity(intent);
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipality_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        municipality = (Municipality) getIntent().getSerializableExtra("municipality");
        TextView textView0 = findViewById(R.id.textView0);
        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.dateTextView);
        TextView textView3 = findViewById(R.id.diagnosticCodeTextView);
        TextView textView4 = findViewById(R.id.textView4);
        TextView textView5 = findViewById(R.id.textView5);
        TextView textView6 = findViewById(R.id.textView6);
        TextView textView7 = findViewById(R.id.textView7);

        textView0.setText(HtmlCompat.fromHtml("<b>Name of municipality: </b>", 0) + municipality.getMunicipality());
        textView1.setText("Code of municipality: " + municipality.getCodeMunicipality());
        textView2.setText("Cases PCR+: " + municipality.getCasesPCR());
        textView3.setText("Cumulative incidence PCR+: " + municipality.getCumulativeIncidence());
        textView4.setText("Deaths PCR+ 14 days: " + municipality.getCasesPCR14());
        textView5.setText("Cumulative incidence PCR+ 14: " + municipality.getCasesPCR14cumulativeIncidence());
        textView6.setText("Deaths: " + municipality.getDeaths());
        textView7.setText("Death rate: " + municipality.getDeathRate());


        RecyclerView recyclerView = findViewById(R.id.recycler_view_report);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ReportAdapter(getApplicationContext(), getDiagnosticCode(municipality.getMunicipality(), reportDbHelper));
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MunicipalityDetailActivity.this, AddReport.class);
            Bundle b = new Bundle();
            b.putSerializable("flag", "MunicipalityDetail");
            intent.putExtras(b);
            b.putSerializable("municipality", municipality);
            intent.putExtras(b);
            startActivity(intent);
        });
        mAdapter.setOnItemClickListener(onItemClickListener);
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

    private void createReportObject(Cursor cursor) {
        try {

            // If moveToFirst() returns false then cursor is empty
            if (!cursor.moveToFirst()) {
            }

            do {
                // Read the values of a row in the table using the indexes acquired above
                String diagnosticCode = cursor.getString(cursor.getColumnIndexOrThrow(ReportContract.ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(ReportContract.ReportEntry.COLUMN_NAME_SYMPTOMS_START_DATE));
                String feverChills = cursor.getString(cursor.getColumnIndexOrThrow(ReportContract.ReportEntry.COLUMN_NAME_FEVER_CHILLS));
                String cough = cursor.getString(cursor.getColumnIndexOrThrow(ReportContract.ReportEntry.COLUMN_NAME_COUGH));
                String breath = cursor.getString(cursor.getColumnIndexOrThrow(ReportContract.ReportEntry.COLUMN_NAME_BREATHING));
                String fatigue = cursor.getString(cursor.getColumnIndexOrThrow(ReportContract.ReportEntry.COLUMN_NAME_FATIGUE));
                String bodyAches = cursor.getString(cursor.getColumnIndexOrThrow(ReportContract.ReportEntry.COLUMN_NAME_MUSCLE_BODY_ACHES));
                String headache = cursor.getString(cursor.getColumnIndexOrThrow(ReportContract.ReportEntry.COLUMN_NAME_HEADACHE));
                String lossTaste = cursor.getString(cursor.getColumnIndexOrThrow(ReportContract.ReportEntry.COLUMN_NAME_TASTE_SMELL_LOSS));
                String soreThroat = cursor.getString(cursor.getColumnIndexOrThrow(ReportContract.ReportEntry.COLUMN_NAME_SORE_THROAT));
                String congestion = cursor.getString(cursor.getColumnIndexOrThrow(ReportContract.ReportEntry.COLUMN_NAME_CONGESTION_RUNNY_NOSE));
                String nausea = cursor.getString(cursor.getColumnIndexOrThrow(ReportContract.ReportEntry.COLUMN_NAME_NAUSEA_VOMITING));
                String diarrhea = cursor.getString(cursor.getColumnIndexOrThrow(ReportContract.ReportEntry.COLUMN_NAME_DIARRHEA));
                String closeContact = cursor.getString(cursor.getColumnIndexOrThrow(ReportContract.ReportEntry.COLUMN_NAME_CLOSE_CONTACT));
                String municipality = cursor.getString(cursor.getColumnIndexOrThrow(ReportContract.ReportEntry.COLUMN_NAME_MUNICIPALITY));
                report = new Report(diagnosticCode, date, feverChills, cough, breath, fatigue, bodyAches, headache, lossTaste, soreThroat, congestion, nausea, diarrhea, closeContact, municipality);

            } while (cursor.moveToNext());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Cursor getDiagnosticCode(String municipality, ReportDbHelper reportDbHelper) {
        db = reportDbHelper.getReadableDatabase();
        String selectQuery = "SELECT diagnostic_code FROM report WHERE municipality = ?";
        String[] selectionArgs = new String[]{municipality};
        Cursor c = db.rawQuery(selectQuery, selectionArgs);
        getArrayListCursor(c);
        return c;
    }

    public Cursor getRowByDiagnosticCode(String code, ReportDbHelper reportDbHelper) {
        db = reportDbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM report WHERE diagnostic_code = ?";
        String[] selectionArgs = new String[]{code};
        Cursor c = db.rawQuery(selectQuery, selectionArgs);
        getArrayListCursor(c);
        return c;
    }


    private void getArrayListCursor(Cursor mCursor) {
        final int idIndex = mCursor.getColumnIndex(ReportContract.ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE);
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            // The Cursor is now set to the right position
            mArrayList.add(mCursor.getString(idIndex));
        }
    }
}