package com.example.pas_genap_rizal_23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.pas_genap_rizal_23.R;
import com.example.pas_genap_rizal_23.Team;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private Context context;
    private List<Team> teamList;

    public TeamAdapter(Context context, List<Team> teamList) {
        this.context = context;
        this.teamList = teamList;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_adapter_teams, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Team team = teamList.get(position);
        holder.teamName.setText(team.getStrTeam());
        holder.stadiumName.setText(team.getStrStadium());
        Glide.with(context)
                .load(team.getStrTeamBadge())
                .into(holder.teamBadge);
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        ImageView teamBadge;
        TextView teamName, stadiumName;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            teamBadge = itemView.findViewById(R.id.teamBadge);
            teamName = itemView.findViewById(R.id.teamName);
            stadiumName = itemView.findViewById(R.id.stadiumName);
        }
    }
}
