package filip.ondrusek.uv.es;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONException;
import java.util.ArrayList;

public class AdapterMunicipality extends RecyclerView.Adapter<AdapterMunicipality.ViewHolder> implements Filterable {
    private final ArrayList<Municipality> municipalityAll;
    private ArrayList<Municipality> municipality;
    private View.OnClickListener onItemClickListener;
    private final double LowIncidence = 10000D;
    private final double MediumIncidence = 20000D;

    public AdapterMunicipality(ArrayList<Municipality> parMunicipality ) throws JSONException {
        this.municipality = parMunicipality;
        this.municipalityAll = new ArrayList<>(this.municipality);
    }

    private ArrayList<Municipality> getMunicipalityAll() {
        return municipalityAll;
    }

    public ArrayList<Municipality> getMunicipalityList() { return municipality;
    }

    private void setMunicipalityList(ArrayList<Municipality> newList)
    {
        this.municipality = newList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.getTextView().setText("Name: " + getMunicipalityList().get(position).getMunicipality() + " \nCumulative incidence: " + getMunicipalityList().get(position).getCumulativeIncidence());
            double incidence = Double.parseDouble(getMunicipalityList().get(position).getCumulativeIncidence().trim().replace(",", "."));
            if (incidence < LowIncidence) {
                holder.getTextView().setBackgroundColor(Color.rgb(102, 255, 102));
            } else if (incidence >= LowIncidence && incidence < MediumIncidence) {
                holder.getTextView().setBackgroundColor(Color.rgb(255, 255, 102));
            } else {
                holder.getTextView().setBackgroundColor(Color.rgb(250, 81, 81));
            }
    }
    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return municipality.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Municipality> filteredList = new ArrayList<>();
            if(charSequence.toString().isEmpty()) {
                filteredList.addAll(getMunicipalityAll());
            } else {
                for(Municipality municipality: getMunicipalityAll()) {
                    if (municipality.getMunicipality().toLowerCase().contains(charSequence.toString().toLowerCase().trim())) {
                        filteredList.add(municipality);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            filterResults.count = filteredList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            getMunicipalityList().clear();
            setMunicipalityList((ArrayList<Municipality>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public  class ViewHolder extends RecyclerView.ViewHolder  {

        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textview);
            view.setTag(this);
            view.setOnClickListener(onItemClickListener);
        }
        public TextView getTextView() {
            return textView;
        }
    }
}