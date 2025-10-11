package com.prueba.inmobiliariaretrofit.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<Mapa> mapaMutable;


    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Mapa> getMapa(){
        if (mapaMutable==null){
            mapaMutable=new MutableLiveData<>();
        }

        return mapaMutable;
    }

    public void obtenerMapas(){
        Mapa mapaActual=new Mapa();
        mapaMutable.setValue(mapaActual);
    }


public class Mapa implements OnMapReadyCallback {
    private LatLng ulp=new LatLng(-33.150720,-66.306864);
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        googleMap.addMarker(new MarkerOptions().position(ulp).title("test title"));

        CameraPosition cameraPosition=
                new CameraPosition.Builder().target(ulp)
                        .zoom(19)
                        .bearing(45)
                        .tilt(70)
                        .build();
        CameraUpdate cameraUpdate= CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.animateCamera(cameraUpdate);

    }
}

}