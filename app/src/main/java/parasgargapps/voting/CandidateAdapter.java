package parasgargapps.voting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.MyViewHolder> {
    private List<Candidate> candidateList;
    private ItemClickListener mClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView partyName, holderName;

        public MyViewHolder(View view) {
            super(view);
            partyName = view.findViewById(R.id.partyName);
            holderName = view.findViewById(R.id.holderName);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, candidateList.get(getAdapterPosition()));
        }

    }

    public CandidateAdapter(List<Candidate> candidateList) {
        this.candidateList = candidateList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Candidate candidate = candidateList.get(position);
        holder.partyName.setText(candidate.getPartyName());
        holder.holderName.setText(candidate.getName());

    }

    @Override
    public int getItemCount() {
        return candidateList.size();
    }

    public void setmClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, Candidate candidate);
    }

}
