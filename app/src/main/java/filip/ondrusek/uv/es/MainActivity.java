package filip.ondrusek.uv.es;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   private ArrayList<Municipality> municipality = new ArrayList<>();
   private ArrayList<String> municipalityName = new ArrayList<>();
   private AdapterMunicipality adapterMunicipality;

    private View.OnClickListener onItemClickListener = view -> {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();

        Municipality municipality = this.municipality.get(position);
        openMunicipalityDetailActivity(municipality);
    };




    public ArrayList<Municipality> getMunicipality() {
        return municipality;
    }
    public MainActivity() throws JSONException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Init();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        try {
            adapterMunicipality = new AdapterMunicipality(municipality);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterMunicipality);
        adapterMunicipality.setOnItemClickListener(onItemClickListener);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,AddReport.class);
            Bundle b = new Bundle();
            b.putSerializable("municipalityName", municipalityName);
            intent.putExtras(b);
            startActivity(intent);
        });

    }
    private void openMunicipalityDetailActivity(Municipality municipality) {
        Intent intent = new Intent(this,MunicipalityDetail.class);
        Bundle b = new Bundle();
        b.putSerializable("municipality", municipality);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void Init() throws JSONException {
        InputStream is = getResources().openRawResource(R.raw.datastore_search);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JSONObject jsonObject = new JSONObject(writer.toString());
        JSONObject jsonObjectResult = jsonObject.getJSONObject("result");
        JSONArray jsonArray = jsonObjectResult.optJSONArray("records");
        this.CreateList(jsonArray);
    }

    private void CreateList(JSONArray jsonArray) {
        if (jsonArray != null) {

            for (int i=0;i<jsonArray.length();i++){
                try {
                    JSONObject jsonObjectItem = jsonArray.getJSONObject(i);
                    int id = Integer.parseInt(jsonObjectItem.getString("_id"));
                    int codeMunicipality = Integer.parseInt(jsonObjectItem.getString("CodMunicipio"));
                    String municipality = jsonObjectItem.getString("Municipi");
                    int casesPCR  = Integer.parseInt(jsonObjectItem.getString("Casos PCR+"));
                    String cumulativeIncidence = jsonObjectItem.getString("Incidència acumulada PCR+");
                    int casesPCR14 = Integer.parseInt(jsonObjectItem.getString("Casos PCR+ 14 dies"));
                    String casesPCR14cumulativeIncidence = jsonObjectItem.getString("Incidència acumulada PCR+14");
                    int deaths = Integer.parseInt(jsonObjectItem.getString("Defuncions"));
                    String deathRate = jsonObjectItem.getString("Taxa de defunció");
                    this.municipality.add(new Municipality(id, codeMunicipality, municipality, casesPCR, cumulativeIncidence, casesPCR14, casesPCR14cumulativeIncidence, deaths, deathRate));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        CreateMunicipalityList();
    }

    private void CreateMunicipalityList()
    {
        for(int i = 0; i < this.municipality.size(); i++)
        {
            this.municipalityName.add(this.municipality.get(i).getMunicipality());
        }
    }

}