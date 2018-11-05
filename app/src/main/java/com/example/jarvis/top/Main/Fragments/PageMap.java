package com.example.jarvis.top.Main.Fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
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

import com.example.jarvis.top.CustomAlert.CustomBottomSheetBehavior;
import com.example.jarvis.top.Login.Sessao.Sessao;
import com.example.jarvis.top.Main.Chamado.Chamado;
import com.example.jarvis.top.Main.Main;
import com.example.jarvis.top.R;
import com.example.jarvis.top.Utils.LoadingSettings;
import com.example.jarvis.top.Utils.StatusUtil;
import com.example.jarvis.top.Utils.Utils;
import com.example.jarvis.top.WebService.Connects;
import com.example.jarvis.top.WebService.Models.Chamados.ChamadosModel;
import com.example.jarvis.top.WebService.Models.Chamados.ResultChamadosModel;
import com.example.jarvis.top.WebService.Network;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

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
    ArrayList<ResultChamadosModel> list;
    Main.BehaviorItens behaviorItens;
    private FusedLocationProviderClient mFusedLocationClient;

    public PageMap() {
        // Required empty public constructor
    }

    public void setBehaviorItens(Main.BehaviorItens behaviorItens) {
        this.behaviorItens = behaviorItens;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_page_map, container, false);

        activity = getActivity();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapIsReady = true;
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String id = marker.getSnippet().split("\n\n")[1];
                for (int i = 0; i < list.size(); i++) {
                    if(String.valueOf(list.get(i).getID()).equals(id)){
                        showChamado(i);
                    }
                }
            }
        });

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Utils.verifiePermissions(activity);
            return;
        }
        mMap.setMyLocationEnabled(true);
        mFusedLocationClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng mylocation = new LatLng(location.getLatitude(), location.getLongitude());
//                mMap.addMarker(new MarkerOptions().position(mylocation).title("Você está aqui"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 15));
            }
        });
    }

    public void placeApin(LatLng latLng, String title, String snippet) {
        if (mapIsReady) {
            mMap.addMarker(new MarkerOptions().position(latLng).snippet(snippet).title(title));
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
                list = response.body().getResultado();
                LoadingSettings.showProgressBar(false, activity.findViewById(R.id.header_pb));
                for (int i = 0; i < list.size(); i++) {
                    try {
                        placeApin(new LatLng(Double.parseDouble(list.get(i).getLatitude()), Double.parseDouble(list.get(i).getLongitude())), list.get(i).getTitulo(), list.get(i).getConteudo() + "\n\n" + list.get(i).getID());
                    } catch (Exception ignore) {}
                }
            }

            @Override
            public void onFailure(Call<ChamadosModel> call, Throwable t) {
//                LoadingSettings.showProgressBar(false, activity.findViewById(R.id.header_pb));
//                Log.e("TESTEE", t.getMessage());
            }
        });
    }

    public void showChamado(int position){
        final ResultChamadosModel resultChamadosModel = list.get(position);
        behaviorItens.txtTit.setText(resultChamadosModel.getTitulo());
        behaviorItens.txtCdo.setText(resultChamadosModel.getConteudo());
        behaviorItens.txtByy.setText(resultChamadosModel.getDe());
        behaviorItens.txtFor.setText(resultChamadosModel.getPara());
        behaviorItens.txtDtt.setText(resultChamadosModel.getPostadoEm());

        StatusUtil status = new StatusUtil(Integer.parseInt(resultChamadosModel.getStatus()));
        behaviorItens.behavior_imgStatus.setImageDrawable(activity.getDrawable(status.getResourceImage()));
        behaviorItens.behavior_imgStatus.setColorFilter(activity.getColor(status.getResourceColor()));
        behaviorItens.behavior_txtStatus.setText(status.getMensagem());
        behaviorItens.behavior_txtStatus.setTextColor(activity.getColor(status.getResourceColor()));

        behaviorItens.btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Chamado.class);
                intent.putExtra("item", resultChamadosModel);
                startActivity(intent);
                behaviorItens.cbsb.setState(CustomBottomSheetBehavior.CLOSED);
            }
        });

        behaviorItens.cbsb.setState(CustomBottomSheetBehavior.SHOWING);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshPins();
    }
}
