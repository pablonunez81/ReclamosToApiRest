package com.ande.reclamos.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ande.reclamos.R;
import com.ande.reclamos.model.ReclamosDetalle;
import com.ande.reclamos.ui.activity.ReparacionesActivity;

import java.util.ArrayList;

public class ReparacionesAdapter extends RecyclerView.Adapter<ReparacionesAdapter.ViewHolder> {

    private ArrayList<ReclamosDetalle> mDataSet;
    protected LayoutInflater inflador;
    private ViewHolder holder;
    private int position;

    /**
     * Constructor
     */
    public ReparacionesAdapter() {
        mDataSet = new ArrayList<>();
    }

    public void setDataSet(ArrayList<ReclamosDetalle> dataSet) {
        this.mDataSet = dataSet;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Context context;
        public TextView mtvAverias, mtvObservacion;
        public ImageView btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();

            mtvAverias = (TextView) itemView.findViewById(R.id.tvAverias);
            mtvObservacion = (TextView) itemView.findViewById(R.id.tvObservacion);
            //obtenemos la referencia de los botones a variables
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        void setOnClickListener(){
            btnDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnDelete:
                    Toast.makeText(context,"click en borrar. Id: " + v.getId(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout cl = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_view, parent, false);
        TextView mtvTextView = (TextView) cl.getViewById(R.id.tvAverias);
        TextView mtvObservacion = (TextView) cl.getViewById(R.id.tvObservacion);
        return new ViewHolder(cl);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReclamosDetalle reclamosdetalle = mDataSet.get(position);
        holder.mtvAverias.setText(reclamosdetalle.getAveria().getAveria());
        holder.mtvObservacion.setText(reclamosdetalle.getObservacion());
        holder.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
