package com.example.navigationapplication.produto;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.example.navigationapplication.Constantes;
import com.example.navigationapplication.R;
import com.example.navigationapplication.aviso.Aviso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProdutoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProdutoFragment extends Fragment {

    String produtoUrl = Constantes.BASE_URL+"/products/all";
    ArrayList<Produto> produtos = new ArrayList<>();
    GridLayoutManager layoutManager;
    RecyclerView produtoRecycler;
    EditText editPesquisar;
    int nColunas = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProdutoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LojinhaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProdutoFragment newInstance(String param1, String param2) {
        ProdutoFragment fragment = new ProdutoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_produto, container, false);
        produtoRecycler = view.findViewById(R.id.produto_recycler);
        editPesquisar = view.findViewById(R.id.edit_pesquisar);
        ToggleButton buttomPesquisar = view.findViewById(R.id.button_pesquisar);

        layoutManager = new GridLayoutManager(getContext(), nColunas);
        produtoRecycler.setLayoutManager(layoutManager);

        String response = new Constantes().makeGetRequest(produtoUrl);
        parseJson(response);

        ProdutoAdapter produtoAdapter = new ProdutoAdapter(produtos, getContext());
        produtoRecycler.setAdapter(produtoAdapter);

        buttomPesquisar.setOnClickListener(v -> {
            nColunas = (nColunas == 1)? 2 : 1;
            layoutManager.setSpanCount(nColunas);
            produtoRecycler.setLayoutManager(layoutManager);
        });

        editPesquisar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString();
                Log.d("TextWatcher","Texto alterado: /"+query+"/");
                produtoAdapter.filter(query);
            }
        });

//        produtoRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

    private void parseJson(String json) {
        try {
            JSONObject data = new JSONObject(json).getJSONObject("_embedded");
            JSONArray pubs = data.getJSONArray("productList");

            for (int i = 0; i < pubs.length(); i++) {
                JSONObject row = pubs.getJSONObject(i);
                String name = row.getString("name");
                double price = row.getDouble("price");
                String image = row.getString("image");

                produtos.add(new Produto(name, price, image));
            }


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}