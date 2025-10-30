package com.prueba.inmobiliariaretrofit.ui.contratos.detalleContrato.pagos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prueba.inmobiliariaretrofit.R;


import java.util.List;

import modelos.InmuebleModel;
import modelos.PagoModel;

public class pagoAdapter extends RecyclerView.Adapter<pagoAdapter.ViewHolderPagos> {
    private List<PagoModel> listaPago;
    private Context context;
    private LayoutInflater inflater;


    public pagoAdapter(List<PagoModel> listPago, Context context, LayoutInflater inflater) {
        this.listaPago = listPago;
        this.context = context;
        this.inflater = inflater;

    }

    @NonNull
    @Override
    public pagoAdapter.ViewHolderPagos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=inflater.inflate(R.layout.itempagos,parent,false);
        return new pagoAdapter.ViewHolderPagos(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull com.prueba.inmobiliariaretrofit.ui.inquilinos.ContratosAdapter.ViewHolderInmuebleInquilino holder, int position) {
        PagoModel pago = listaPago.get(position);
        //holder.direccion.setText(inmActual.getDireccion());



    }

    @Override
    public int getItemCount() {
        return listaPago.size();
    }

    public class ViewHolderPagos extends RecyclerView.ViewHolder{

        TextView FechaDePago,monto,detalle,estado;
        public ViewHolderPagos(@NonNull View itemView) {
            super(itemView);

        }


    }
}