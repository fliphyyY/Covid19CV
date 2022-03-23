package filip.ondrusek.uv.es;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MunicipalityDetail extends AppCompatActivity {
private Municipality municipality;
private ReportAdapter mAdapter;
private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipality_detail);
        municipality = (Municipality) getIntent().getSerializableExtra("municipality");
        TextView textView0 = findViewById(R.id.textView0);
        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView3 = findViewById(R.id.textView3);
        TextView textView4 = findViewById(R.id.textView4);
        TextView textView5 = findViewById(R.id.textView5);
        TextView textView6 = findViewById(R.id.textView6);
        TextView textView7 = findViewById(R.id.textView7);

        textView0.setText("Name of municipality: "+ municipality.getMunicipality());
        textView1.setText("Code of municipality: "+ municipality.getCodeMunicipality());
        textView2.setText("Cases PCR+: "+ municipality.getCasesPCR());
        textView3.setText("Cumulative incidence PCR+: "+ municipality.getCumulativeIncidence());
        textView4.setText("Deaths PCR+ 14 days: "+ municipality.getCasesPCR14());
        textView5.setText("Cumulative incidence PCR+ 14: "+ municipality.getCasesPCR14cumulativeIncidence());
        textView6.setText("Deaths: "+ municipality.getDeaths());
        textView7.setText("Death rate: "+ municipality.getDeathRate());


        ReportDbHelper reportDbHelper = new ReportDbHelper(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_report);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ReportAdapter(getApplicationContext(), getAllItems(municipality.getMunicipality(), reportDbHelper));
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            ArrayList<String> municipalityName = new ArrayList<>();
            municipalityName.add(municipality.getMunicipality());
            Intent intent = new Intent(MunicipalityDetail.this,AddReport.class);
            Bundle b = new Bundle();
            b.putSerializable("municipalityName", municipalityName);
            b.putSerializable("municipality", municipality);
            intent.putExtras(b);
            startActivity(intent);
        });

    }

    public Cursor getAllItems(String municipality, ReportDbHelper reportDbHelper)
    {
        db = reportDbHelper.getReadableDatabase();
        String selectQuery = "SELECT diagnostic_code FROM report WHERE municipality = ?";
        String[] selectionArgs = new String[]{municipality};
        Cursor c = db.rawQuery(selectQuery, selectionArgs);
        return c;
    }


    public ReportAdapter getmAdapter() {
        return mAdapter;
    }
}