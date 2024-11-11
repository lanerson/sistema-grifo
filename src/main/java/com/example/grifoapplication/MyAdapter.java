package com.example.grifoapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Event> eventList;
    private Context context;

    public MyAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.title.setText(event.getTitle());
        holder.image.setImageResource(event.getImageResId());

        // Adicionar evento de clique
        holder.itemView.setOnClickListener(v -> {
            showPopup(event);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    private void showPopup(Event event) {
        // Criar a visualização do popup
        View popupView = LayoutInflater.from(context).inflate(R.layout.popup_layout, null);
        ImageView popupImage = popupView.findViewById(R.id.popupImage);
        TextView popupTitle = popupView.findViewById(R.id.popupTitle);
        TextView popupDescription = popupView.findViewById(R.id.popupDescription);

        // Configurar os dados do evento
        popupImage.setImageResource(event.getImageResId());
        popupTitle.setText(event.getTitle());
        popupDescription.setText(event.getDescription());

        // Criar e exibir o popup
        new AlertDialog.Builder(context)
                .setView(popupView)
                .setPositiveButton("Fechar", null)
                .show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            image = itemView.findViewById(R.id.itemImage);
        }
    }
}
