package com.prueba.inmobiliariaretrofit.ui.home;

import android.app.Application;
import android.widget.Toast;

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
import com.prueba.inmobiliariaretrofit.request.ApiClient;

import modelos.PropietarioModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<Mapa> mapaMutable;

    private MutableLiveData<PropietarioModel> propietarioMutable;


    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Mapa> getMapa(){
        if (mapaMutable==null){
            mapaMutable=new MutableLiveData<>();
        }

        return mapaMutable;
    }


    public LiveData<PropietarioModel> getPropietario(){
        if (propietarioMutable==null){
            propietarioMutable=new MutableLiveData<>();
        }

        return propietarioMutable;
    }


    public void cargarPropietario(){

    }

    public void obtenerMapas(){
        Mapa mapaActual=new Mapa();
        mapaMutable.setValue(mapaActual);
    }



    public void obtenerPerfil(){

        String token = ApiClient.leerToken(getApplication());

        ApiClient.InmoService api=ApiClient.getInmoService();
        Call<PropietarioModel> call=api.getPropietario("Bearer "+token);

        call.enqueue(new Callback<PropietarioModel>() {
            @Override
            public void onResponse(Call<PropietarioModel> call, Response<PropietarioModel> response) {
                if (response.isSuccessful()){
                    propietarioMutable.postValue(response.body());
                }else{
                    Toast.makeText(getApplication(),response.toString(),Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<PropietarioModel> call, Throwable throwable) {
                Toast.makeText(getApplication(),throwable.getMessage(),Toast.LENGTH_SHORT);
            }
        });


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