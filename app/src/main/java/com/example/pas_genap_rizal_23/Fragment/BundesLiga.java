package com.example.pas_genap_rizal_23.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pas_genap_rizal_23.R;
import com.example.pas_genap_rizal_23.Team;
import com.example.pas_genap_rizal_23.TeamAdapter;
import com.example.pas_genap_rizal_23.TeamResponse;
import com.example.pas_genap_rizal_23.TeamService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BundesLiga extends Fragment {

    private static final String BASE_URL = "https://www.thesportsdb.com/api/v1/json/3/";

    private RecyclerView recyclerView;
    private TeamAdapter teamAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teams_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TeamService teamService = retrofit.create(TeamService.class);

        Call<TeamResponse> call = teamService.getBundesLigahSoccerTeams();
        call.enqueue(new Callback<TeamResponse>() {
            @Override
            public void onResponse(Call<TeamResponse> call, Response<TeamResponse> response) {
                if (response.isSuccessful()) {
                    TeamResponse teamResponse = response.body();
                    if (teamResponse != null && teamResponse.getTeams() != null) {
                        List<Team> teams = teamResponse.getTeams();
                        teamAdapter = new TeamAdapter(getActivity(), teams);
                        recyclerView.setAdapter(teamAdapter);
                    }
                } else {
                    Toast.makeText(getActivity(), "Response was not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TeamResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
