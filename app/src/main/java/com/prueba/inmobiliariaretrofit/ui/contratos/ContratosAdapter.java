package com.prueba.inmobiliariaretrofit.ui.inquilinos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

public class ContratosAdapter extends RecyclerView.Adapter<ContratosAdapter.ViewHolderInmuebleInquilino> {
    private List<InmuebleModel> listaInmuebles;
    private Context context;
    private LayoutInflater inflater;


    public ContratosAdapter(List<InmuebleModel> listaInmuebles, Context context, LayoutInflater inflater) {

        this.listaInmuebles = listaInmuebles;
        this.listaInmuebles.sort((o1, o2) -> o2.getIdInmueble() - o1.getIdInmueble());
        this.context = context;
        this.inflater = inflater;

    }

    @NonNull
    @Override
    public ContratosAdapter.ViewHolderInmuebleInquilino onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=inflater.inflate(R.layout.iteminmuebleinquilino,parent,false);
        return new ContratosAdapter.ViewHolderInmuebleInquilino(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratosAdapter.ViewHolderInmuebleInquilino holder, int position) {
        InmuebleModel inmActual = listaInmuebles.get(position);
        holder.direccion.setText(inmActual.getDireccion());
        holder.precio.setText(inmActual.getValor()+"");
        holder.ambientes.setText("ambientes:"+inmActual.getAmbientes());
        Glide.with(context)
                .load("https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/" + inmActual.getImagen())
                .placeholder(null)
                .error("null")
                .into(holder.portada);
        ((ContratosAdapter.ViewHolderInmuebleInquilino)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putInt("inmueble",inmActual.getIdInmueble());

                Navigation.findNavController((Activity) context,R.id.nav_host_fragment_content_main).navigate(R.id.action_nav_contratos_to_detalleContratoFragment,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaInmuebles.size();
    }

    public class ViewHolderInmuebleInquilino extends RecyclerView.ViewHolder{

        private TextView direccion,precio,ambientes;
        private ImageView portada;
        public ViewHolderInmuebleInquilino(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvFechaPagop);
            precio = itemView.findViewById(R.id.tvDetallep);
            portada= itemView.findViewById(R.id.imgInmueble);
            ambientes=itemView.findViewById(R.id.tvMontop);
        }

        public TextView getDireccion() {
            return direccion;
        }

        public TextView getPrecio() {
            return precio;
        }

        public TextView getAmbientes() {
            return ambientes;
        }

        public ImageView getPortada() {
            return portada;
        }
    }
}
