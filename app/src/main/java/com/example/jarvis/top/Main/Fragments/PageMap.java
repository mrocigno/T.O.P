package com.example.jarvis.top.Main.Fragments;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jarvis.top.Login.Sessao.Sessao;
import com.example.jarvis.top.R;
import com.example.jarvis.top.Utils.LoadingSettings;
import com.example.jarvis.top.WebService.Connects;
import com.example.jarvis.top.WebService.Models.Chamados.ChamadosModel;
import com.example.jarvis.top.WebService.Models.Chamados.ResultChamadosModel;
import com.example.jarvis.top.WebService.Network;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageMap extends Fragment implements OnMapReadyCallback {

    boolean mapIsReady = false;
    private GoogleMap mMap;
    Activity activity;
    public PageMap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_page_map, container, false);

        activity = getActivity();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapIsReady = true;
        mMap = googleMap;
        placeApin(new LatLng(0, 0));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getActivity(), "TESTE", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    public void placeApin(LatLng latLng) {
        if (mapIsReady) {
            mMap.addMarker(new MarkerOptions().position(latLng).snippet("GG iziiii").title("Marker in Sydney"));
        }
    }

    public void refreshPins() {
        LoadingSettings.showProgressBar(true, activity.findViewById(R.id.header_pb));
        final Retrofit retrofit = Network.teste();
        Connects con = retrofit.create(Connects.class);
        con.getChamadosExternos(String.valueOf(Sessao.getId())).enqueue(new Callback<ChamadosModel>() {
            @Override
            public void onResponse(Call<ChamadosModel> call, Response<ChamadosModel> response) {
                assert response.body() != null;
                ArrayList<ResultChamadosModel> list = response.body().getResultado();
                LoadingSettings.showProgressBar(false, activity.findViewById(R.id.header_pb));
                for (int i = 0; i < list.size(); i++) {
                    try {
                        placeApin(new LatLng(Double.parseDouble(list.get(i).getLatitude()), Double.parseDouble(list.get(i).getLongitude())));
                    } catch (Exception ignore) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ChamadosModel> call, Throwable t) {
                LoadingSettings.showProgressBar(false, activity.findViewById(R.id.header_pb));
                Log.e("TESTEE", t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshPins();
    }
}
