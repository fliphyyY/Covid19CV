package filip.ondrusek.uv.es;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
   private ArrayList<Municipality> municipality = new ArrayList<>();
   private ArrayList<String> municipalityNamesList = new ArrayList<>();
   private HTTPConnector httpConnector = new HTTPConnector();
   private AdapterMunicipality adapterMunicipality;
   private Report report;
   private JSONObject jsonObject;
   private boolean internetConnection;
   private FloatingActionButton floatingActionButton;
   private boolean orderedByMunicipality;
   private boolean sortedAscending;
   private String jsonId;

    private View.OnClickListener onItemClickListener = view -> {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();
        Municipality municipality = adapterMunicipality.getMunicipalityList().get(position);
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
        this.orderedByMunicipality = true;
        this.sortedAscending = true;
        try {
            Init();
        } catch (JSONException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        try {
            adapterMunicipality = new AdapterMunicipality(getMunicipality());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterMunicipality);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        adapterMunicipality.setOnItemClickListener(onItemClickListener);

        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddReportActivity.class);
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
            this.jsonId = jsonObjectResult.getString("resource_id");
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
                    String codeMunicipality = jsonObjectItem.getString("CodMunicipio");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        inflater.inflate(R.menu.search_main_activity, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapterMunicipality.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                String url = "https://dogv.gva.es/es/covid-19";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            case R.id.item2:
                if(this.orderedByMunicipality) {
                    this.orderedByMunicipality = false;
                    this.orderingSorting("Ordered by cumulative incidence",Municipality.OrderByCumulativeIncidenceAscending);
                } else {
                    this.orderedByMunicipality = true;
                    this.orderingSorting("Ordered by municipality",Municipality.OrderByMunicipalityAscending);
                }
                this.sortedAscending = true;
                return true;
            case R.id.item3:
                if(this.sortedAscending)
                {
                    this.sortedAscending = false;
                    if (this.orderedByMunicipality) {
                        this.orderingSorting("Sorted descending",Municipality.OrderByMunicipalityDescending);

                    } else {
                        this.orderingSorting("Sorted descending",Municipality.OrderByCumulativeIncidenceDescending);
                    }
                } else {
                    this.sortedAscending = true;
                    if (this.orderedByMunicipality) {
                        this.orderingSorting("Sorted ascending",Municipality.OrderByMunicipalityAscending);
                    } else {
                        this.orderingSorting("Sorted ascending",Municipality.OrderByCumulativeIncidenceAscending);
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void orderingSorting(String message, Comparator<Municipality> comparator)
    {
        Collections.sort(adapterMunicipality.getMunicipalityList(), comparator);
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
        adapterMunicipality.notifyDataSetChanged();
    }

}

