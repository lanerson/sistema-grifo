package com.example.navigationapplication.produto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationapplication.Constantes;
import com.example.navigationapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder> {
    private List<Produto> produtos;
    private final List<Produto> produtosOriginal;
    private Context context;

    String url = Constantes.BASE_URL+"/images/";

    ProdutoAdapter(List<Produto> produtos, Context context){
        this.produtos = new ArrayList<>(produtos);
        this.produtosOriginal = new ArrayList<>(produtos);
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.produto_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto produto = produtos.get(position);
        holder.name.setText(produto.getName());
//        holder.image.setImageResource(event.getImageResId());

        Picasso.get().load(url+produto.getImage()).into(holder.image);
        // Adicionar evento de clique

    }


    public void filter(String query) {
        if (query.isEmpty()) {
            Log.d("Ok", "tá vindo pra cá");
            produtos.clear();
            produtos.addAll(produtosOriginal);  // Se a busca estiver vazia, volta à lista original
        } else {
            List<Produto> filteredList = new ArrayList<>();
            for (Produto produto : produtosOriginal) {
                if (produto.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(produto);
                }
            }
            produtos.clear();
            produtos.addAll(filteredList);  // Atualiza a lista com os itens filtrados
        }
        notifyDataSetChanged();  // Notifica o RecyclerView de que os dados foram atualizados
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView price;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.produtoName);
            price = itemView.findViewById(R.id.produtoPrice);
            image = itemView.findViewById(R.id.produtoImage);
        }
    }


}
