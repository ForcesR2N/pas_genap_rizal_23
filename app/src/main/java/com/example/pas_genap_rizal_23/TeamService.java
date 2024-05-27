package com.example.pas_genap_rizal_23;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TeamService {
    @GET("search_all_teams.php?l=English%20Premier%20League")
    Call<TeamResponse> getTeams();
    @GET("search_all_teams.php?l=Indonesian%20Super%20League")
    Call<TeamResponse> getLiga1Indonesia();
}
