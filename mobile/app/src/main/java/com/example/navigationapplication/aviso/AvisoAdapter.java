package com.example.navigationapplication.aviso;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationapplication.Constantes;
import com.example.navigationapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AvisoAdapter extends RecyclerView.Adapter<AvisoAdapter.ViewHolder> {
    private List<Aviso> avisos;
    private Context context;

    String url = Constantes.BASE_URL+"/images/";

    AvisoAdapter(List<Aviso> avisos, Context context){
        this.avisos = avisos;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.aviso_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Aviso aviso = avisos.get(position);
        holder.title.setText(aviso.getTitle());
//        holder.image.setImageResource(event.getImageResId());

        Picasso.get().load(url+aviso.getImage()).into(holder.image);
        // Adicionar evento de clique
        holder.itemView.setOnClickListener(view -> {
            showPopup(aviso);
        });
    }

    @Override
    public int getItemCount() {
        return avisos.size();
    }

    private void showPopup(Aviso aviso) {
        // Criar a visualização do popup
        View popupView = LayoutInflater.from(context).inflate(R.layout.popup_layout, null);

        // Obter referências para as views
        ImageView popupImage = popupView.findViewById(R.id.popupImage);
        TextView popupTitle = popupView.findViewById(R.id.popupTitle);
        TextView popupDescription = popupView.findViewById(R.id.popupDescription);

        // Configurar os dados do aviso
        Picasso.get().load(url + aviso.getImage()).into(popupImage);  // Carregar imagem
        popupTitle.setText(aviso.getTitle());  // Configurar título
        popupDescription.setText(aviso.getDescription());  // Configurar descrição

        // Criar e exibir o popup
        new AlertDialog.Builder(context)
                .setView(popupView)
                .show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.avisoTitle);
            image = itemView.findViewById(R.id.avisoImage);
        }
    }


}
