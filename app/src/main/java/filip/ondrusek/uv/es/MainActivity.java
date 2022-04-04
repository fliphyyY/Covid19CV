package filip.ondrusek.uv.es;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   private ArrayList<Municipality> municipality = new ArrayList<>();
   private ArrayList<String> municipalityNamesList = new ArrayList<>();
   private HTTPConnector httpConnector = new HTTPConnector();
   private AdapterMunicipality adapterMunicipality;
   private Report report;
   private JSONObject jsonObject;
   private boolean internetConnection;
   private FloatingActionButton floatingActionButton;

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
        floatingActionButton = findViewById(R.id.fab);
        try {
            Init();
        } catch (JSONException | InterruptedException | IOException e) {
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


        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,AddReport.class);
            Bundle b = new Bundle();
            b.putSerializable("municipalityNamesList", municipalityNamesList);
            intent.putExtras(b);
            b.putSerializable("flag", "MainActivity");
            intent.putExtras(b);
            startActivity(intent);
        });

    }
    private void openMunicipalityDetailActivity(Municipality municipality) {
        Intent intent = new Intent(this,MunicipalityDetailActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("municipality", municipality);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void Init() throws JSONException, InterruptedException, IOException {
        isConnected();
        floatingActionButton.setEnabled(internetConnection);
        if(internetConnection)
        {
            Thread thread = new Thread(() -> {
                try  {
                    this.jsonObject =  httpConnector.doInBackground();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            thread.start();
            thread.join();
            JSONObject jsonObjectResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonObjectResult.optJSONArray("records");
            this.CreateList(jsonArray);
        } else {
            Toast.makeText(getApplicationContext(),"No internet connection!",Toast.LENGTH_LONG).show();

        }
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
        CreateReportObject();
    }

    private void CreateReportObject()
    {
        for(int i = 0; i < this.municipality.size(); i++)
        {
            this.municipalityNamesList.add(this.municipality.get(i).getMunicipality());
        }
    }

    private void isConnected() throws InterruptedException, IOException {

        String command = "ping -c 1 google.com";
        internetConnection = Runtime.getRuntime().exec(command).waitFor() == 0;

    }

}