package filip.ondrusek.uv.es;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONException;
import java.util.ArrayList;


public class AdapterMunicipality extends RecyclerView.Adapter<AdapterMunicipality.ViewHolder> {
    private ArrayList<Municipality> municipality;
    private View.OnClickListener onItemClickListener;
    private TextView textView;


    public AdapterMunicipality(ArrayList<Municipality> parMunicipality ) throws JSONException {
        this.municipality = parMunicipality;
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
        holder.getTextView().setText(String.valueOf(municipality.get(position).municipality));

    }
    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return municipality.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder  {

        private final TextView textView;
       // private View.OnClickListener onItemClickListener;


        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textview);
            view.setTag(this);
            view.setOnClickListener(onItemClickListener);

        }
        public TextView getTextView() {
            return textView;
        }



    }
}