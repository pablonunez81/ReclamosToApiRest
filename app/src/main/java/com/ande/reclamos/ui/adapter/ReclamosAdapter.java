package com.ande.reclamos.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ande.reclamos.GeoPunto;
import com.ande.reclamos.R;
import com.ande.reclamos.Reclamos;
import com.ande.reclamos.model.Reclamo;

import java.util.ArrayList;

public class ReclamosAdapter extends RecyclerView.Adapter<ReclamosAdapter.ViewHolder> {
        //private String[] mDataSet;
        private ArrayList<Reclamo> mDataSet;
        protected LayoutInflater inflador;      //Crea Layouts a partir del xml
        protected Context contexto;             //Necesario para el inflador
        protected View.OnClickListener onClickListener;

        // Obtener referencias de los componentes visuales para cada elemento
        // Es decir, referencias de los EditText, TextViews, Buttons
        public static class ViewHolder extends RecyclerView.ViewHolder {
            // en este ejemplo cada elemento consta solo de un título
            public ImageView tipoReclamo;
            public TextView nombreCliente, direccion ;
            public TextView distancia;

            public ViewHolder(View itemView) {
                super(itemView);
                nombreCliente = (TextView) itemView.findViewById(R.id.nombreCliente);
                direccion = (TextView) itemView.findViewById(R.id.direccion);
                tipoReclamo = itemView.findViewById(R.id.tipoReclamo);
                distancia = (TextView) itemView.findViewById(R.id.distancia);
            }
        }

        // Este es nuestro constructor (puede variar según lo que queremos mostrar)
        public ReclamosAdapter(Context contexto) {
            //this.reclamos = reclamos;
            this.contexto = contexto;
            inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mDataSet = new ArrayList<>();
        }

        public void setDataSet(ArrayList<Reclamo> dataSet){
            this.mDataSet = dataSet;
            notifyDataSetChanged();
        }

        // El layout manager invoca este método
        // para renderizar cada elemento del RecyclerView
        @Override
        public ReclamosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Creamos una nueva vista
            View v = inflador.inflate(R.layout.elemento_lista, null);
            v.setOnClickListener(onClickListener);
            return new ViewHolder(v);
        }

    // Este método asigna valores para cada elemento de la lista
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - obtenemos un elemento del dataset según su posición
            // - reemplazamos el contenido usando tales datos
            Reclamo reclamo = mDataSet.get(position);
            personalizarVista(holder, reclamo);
        }

    protected void personalizarVista(ViewHolder holder, Reclamo reclamo) {
        holder.nombreCliente.setText(reclamo.getNombre());
        holder.direccion.setText(reclamo.getDireccion());

        int id = R.drawable.otros;
        /*switch (reclamo.getTipoReclamo()){
            case AL: id = R.drawable.icon_foco; break;
            case FE: id = R.drawable.icon_casa; break;
        }*/

        holder.tipoReclamo.setImageResource(id);
        holder.tipoReclamo.setScaleType(ImageView.ScaleType.FIT_END);

        /*if(Reclamos.posicionActual != null && reclamo.getPosicion() != null &&
                reclamo.getPosicion().getLatitud() != 0 && reclamo.getPosicion().getLongitud() != 0 ) {*/
        if(reclamo.getSuministroid().getCoordx() != 0 && reclamo.getSuministroid().getCoordy() != 0){
            int d;
            d = (int) Reclamos.posicionActual.distancia(new GeoPunto(reclamo.getSuministroid().getCoordx(), reclamo.getSuministroid().getCoordy()));
            if(d<2000) {
                holder.distancia.setText(d+ " m");
            } else {
                holder.distancia.setText((d / 1000) + " km");
            }
        }
    }

        // Método que define la cantidad de elementos del RecyclerView
        // Puede ser más complejo (por ejem, si implementamos filtros o búsquedas)
        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
    }
