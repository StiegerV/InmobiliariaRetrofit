package com.prueba.inmobiliariaretrofit.ui.contratos.detalleContrato.pagos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prueba.inmobiliariaretrofit.R;


import java.util.List;

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
    public void onBindViewHolder(@NonNull pagoAdapter.ViewHolderPagos holder, int position) {
        PagoModel pago = listaPago.get(position);
        holder.getFechaDePago().setText(pago.getFechaPago());
        holder.getMonto().setText(pago.getImporte()+"");
        holder.getDetalle().setText(pago.getDetalle());
        if (pago.isEstado()){
            holder.getEstado().setText("pagado");
        }else{
            holder.getEstado().setText("sin pagar");
        }



    }

    @Override
    public int getItemCount() {
        return listaPago.size();
    }

    public class ViewHolderPagos extends RecyclerView.ViewHolder{

        TextView fechaDePago,monto,detalle,estado;
        public ViewHolderPagos(@NonNull View itemView) {
            super(itemView);
            this.fechaDePago=itemView.findViewById(R.id.tvFechaPagop);
            this.monto=itemView.findViewById(R.id.tvMontop);
            this.detalle=itemView.findViewById(R.id.tvDetallep);
            this.estado=itemView.findViewById(R.id.tvEstadop);
        }

        public TextView getFechaDePago() {
            return fechaDePago;
        }

        public TextView getMonto() {
            return monto;
        }

        public TextView getDetalle() {
            return detalle;
        }

        public TextView getEstado() {
            return estado;
        }
    }
}