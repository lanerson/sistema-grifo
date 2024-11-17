package com.example.navigationapplication.aviso;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigationapplication.Constantes;
import com.example.navigationapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvisoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvisoFragment extends Fragment {
    String avisoUrl = Constantes.BASE_URL+"/publications/all";
    ArrayList<Aviso> avisos = new ArrayList<>();
    RecyclerView avisoRecycler;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    public AvisoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AvisoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvisoFragment newInstance(String param1, String param2) {
        AvisoFragment fragment = new AvisoFragment();
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
        View view =  inflater.inflate(R.layout.fragment_aviso, container, false);
        avisoRecycler = view.findViewById(R.id.aviso_recycler);
        avisoRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        String response = new Constantes().makeGetRequest(avisoUrl);
        parseJson(response);
        avisoRecycler.setAdapter(new AvisoAdapter(avisos, getContext()));

        return view;
    }


    private void parseJson(String json) {
        try {
            JSONObject data = new JSONObject(json).getJSONObject("_embedded");
            JSONArray pubs = data.getJSONArray("publicationList");

            for (int i = 0; i < pubs.length(); i++) {
                JSONObject row = pubs.getJSONObject(i);
                String title = row.getString("title");
                String description = row.getString("description");
                String image = row.getString("image");

                avisos.add(new Aviso(title, description, image));
            }


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}