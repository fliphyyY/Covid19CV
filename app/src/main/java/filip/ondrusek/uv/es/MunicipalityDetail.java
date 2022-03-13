package filip.ondrusek.uv.es;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MunicipalityDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipality_detail);
        Municipality municipality = (Municipality) getIntent().getSerializableExtra("municipality");
        TextView textView0 = findViewById(R.id.textView0);
        textView0.setText(municipality.codeMunicipality + municipality.getCasesPCR() + municipality.deathRate);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MunicipalityDetail.this,AddReport.class);
            startActivity(intent);
        });

    }
}