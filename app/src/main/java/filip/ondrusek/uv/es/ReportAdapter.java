package filip.ondrusek.uv.es;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private View.OnClickListener onItemClickListener;


    public  ReportAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;

    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {
        public TextView item;

        public ReportViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.textviewReportList);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }
    }


    @NonNull
    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.report_recycler_list

                , parent, false);
        return new ReportViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ReportViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)) {
           return;
        }

        int code = mCursor.getInt(mCursor.getColumnIndexOrThrow(ReportContract.ReportEntry.COLUMN_NAME_DIAGNOSTIC_CODE));
        holder.item.setText(String.valueOf(code));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}
