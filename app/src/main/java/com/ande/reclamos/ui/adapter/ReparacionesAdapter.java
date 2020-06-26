package com.ande.reclamos.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ande.reclamos.R;

public class ReparacionesAdapter extends RecyclerView.Adapter<ReparacionesAdapter.ViewHolder> {

    private String[] mDataSet;
    protected LayoutInflater inflador;
    private View.OnClickListener onItemClickListener;
    private ViewHolder holder;
    private int position;

    /**
     * Constructor
     * @param mDataSet
     */
    public ReparacionesAdapter(String[] mDataSet) {
        this.mDataSet = mDataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mtvAverias, mtvObservacion;
        public ViewHolder(View itemView) {
            super(itemView);
            mtvAverias = (TextView) itemView.findViewById(R.id.tvAverias);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout cl = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_view, parent, false);
        //View v = inflador.inflate(R.layout.item_text_view, null);
        TextView mtvTextView = (TextView) cl.getViewById(R.id.tvAverias);
        TextView mtvObservacion = (TextView) cl.getViewById(R.id.tvObservacion);
        //v.setOnClickListener(onItemClickListener);
        return new ViewHolder(cl);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mtvAverias.setText(mDataSet[position]);
        //holder.mtvObservacion.setText("Esto es una prueba de cargar datos de observacion");

    }


    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
