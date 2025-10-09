package com.prueba.inmobiliariaretrofit.ui.logout;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prueba.inmobiliariaretrofit.R;
import com.prueba.inmobiliariaretrofit.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;
    private LogoutViewModel mViewModel;

    public static LogoutFragment newInstance() {
        return new LogoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel= new ViewModelProvider(this).get(LogoutViewModel.class);
        binding=FragmentLogoutBinding.inflate(inflater,container,false);
        muestraDialog(getActivity());
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    private void muestraDialog(Context donde){
        new AlertDialog.Builder(donde)
                .setTitle("Salir")
                .setMessage("Cerrar aplicacion?")
                .setIcon(android.R.drawable.ic_lock_power_off)
                .setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface di,int i){
                        getActivity().finish();
                    }
                })
                .setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface di,int i){
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                }).show();
    }

}