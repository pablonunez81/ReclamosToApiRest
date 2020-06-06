package com.ande.reclamos.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ande.reclamos.R;
import com.ande.reclamos.Reclamo;
import com.ande.reclamos.Reclamos;

/**
 * Un adaptador es un mecanismo estandar que permite crear vistas que
 * seran han de ser mostradas dentro de un contenedor.
 * Se utilizará una clase RecyclerView.Adapter
 */
public class AdaptadorReclamos extends RecyclerView.Adapter<AdaptadorReclamos.ViewHolder> {

    protected Reclamos reclamos;            //reclamos a mostrar
    protected LayoutInflater inflador;      //Crea Layouts a partir del xml
    protected Context contexto;             //Necesario para el inflador
    protected View.OnClickListener onClickListener;

    /**
     * Constructor. Se inicializa el conjunto de datos a mostrar, y variables globales.
     * El objeto inflador permitirá crear una vista a partir de su XML
     * @param contexto
     * @param reclamos
     */
    public AdaptadorReclamos(Context contexto, Reclamos reclamos) {
        this.contexto = contexto;
        this.reclamos = reclamos;
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    /**
     * Creamos el viewHolder con los tipos de elementos a modificar.
     * Contendrá las vistas que queremos modificar de un elemento.
     * El logo del tipo de reclamo.
     * Nombre del cliente y dirección.
     * Esta vista evita crear vistas para cada elemento desde cero.
     * Se Utilizará un ViewHolder que contendrá las 3 elemenos, pero sin personalizar.
     * Se gastará el mismo View holder para todos los elementos,
     * personalizando según la posición.
     * Mejorando el rendimiento del RecyclerView.
     * Antes que nada, se llamará primero al onCreateViewHolder para obtener el viewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView tipoReclamo;
        public TextView nombreCliente, direccion ;
        public TextView distancia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreCliente = (TextView) itemView.findViewById(R.id.nombreCliente);
            direccion = (TextView) itemView.findViewById(R.id.direccion);
            tipoReclamo = itemView.findViewById(R.id.tipoReclamo);
            distancia = (TextView) itemView.findViewById(R.id.distancia);
        }
    }

    /**
     * // Creamos el ViewHolder Poseedor o titular,
     * con las vista de un elemento sin personalizar.
     * Se podría definir diferentes vistas
     * para diferentes tipos de elementos utilizando el viewType.
     * Usamos el método inflate para crear una vista a partir del xml definido en ELEMENTO LISTA.
     * El segundo parámetro de inflate es el layout Padre que contendrá esta vista que se creará.
     * Es imprescindible indicarlo ya que la vista hija a de adaptarse al tamaño del padre.
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.elemento_lista, null);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    /**
     * Usando como base el ViewHolder, lo personalizamos según su posición.
     * Luego de personalizarlo, el SISTEMA se encarga de crear la vista definitiva que será
     * insertada en el recyclerView.
     * @param holder
     * @param posicion
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion){
        Reclamo reclamo = reclamos.elemento(posicion);
        personalizarVista(holder, reclamo);
    }

    /**
     * Personalizamos un ViewHolder a partir de un reclamo
     * Para agregar la distancia, nos aseguramos de que la posicion actual
     * y el lugar del reclamo existen.
     * Calculamos la distancia y guardamos en d.
     * si la distancia es inferior a 2000, mostramos en metros,
     * si no, mostramos en KM
     * @param holder
     * @param reclamo
     */
    protected void personalizarVista(ViewHolder holder, Reclamo reclamo) {
        holder.nombreCliente.setText(reclamo.getNombreCliente());
        holder.direccion.setText(reclamo.getDireccion());

        int id = R.drawable.otros;
        switch (reclamo.getTipoReclamo()){
            case AL: id = R.drawable.icon_foco; break;
            case FE: id = R.drawable.icon_casa; break;
        }

        holder.tipoReclamo.setImageResource(id);
        holder.tipoReclamo.setScaleType(ImageView.ScaleType.FIT_END);

        //if(Reclamos.posicionActual != null && reclamo.getPosicion() != null) {
        if(Reclamos.posicionActual != null && reclamo.getPosicion() != null &&
                reclamo.getPosicion().getLatitud() != 0 && reclamo.getPosicion().getLongitud() != 0 ) {
            int d;
            d = (int) Reclamos.posicionActual.distancia(reclamo.getPosicion());
            if(d<2000) {
                holder.distancia.setText(d+ " m");
            } else {
                holder.distancia.setText((d / 1000) + " km");
            }
        }
    }

    /**
     * Indicamos el numero de elementos de la lista
     * @return
     */
    @Override
    public int getItemCount() {
        return reclamos.tamanyo();
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
