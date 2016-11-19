package ead.app.br.com.appelite.ead.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ead.app.br.com.appelite.ead.R;
import ead.app.br.com.appelite.ead.interfaces.DadosVolley;
import ead.app.br.com.appelite.ead.net.Config;
import ead.app.br.com.appelite.ead.net.volley.Conexao;


/**
 * Created by PC on 08/03/2016.
 */
public class BuscarCate extends Fragment implements View.OnClickListener {

    private View view;
    private LinearLayout layout;
    private ArrayList<String> strings;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.categorias, container, false);

        layout = (LinearLayout) view.findViewById(R.id.linea);


        strings = new ArrayList<String>();

        strings = getArguments().getStringArrayList("neme");


        generateButton(strings);


        return view;
    }

    public ArrayList<String> loading() {
        final ArrayList<String> strings = new ArrayList<String>();

        Conexao.Conexao(getActivity(), Config.DOMIONIO + "/php/request/getcategoria.php", null, new DadosVolley() {
            @Override
            public void geJsonObject(JSONObject jsonObject) {
                Log.i("LOG", jsonObject + "");
                strings.add("TODOS OS CURSOS");
                for (int i = 0; i < jsonObject.length(); i++) {
                    try {
                        JSONObject cate = jsonObject.getJSONObject(i + "");
                        String categoria = cate.getString("nomecate");
                        strings.add(categoria);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void ErrorVolley(String messege) {


            }
        });

        return strings;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList("cate", strings);

    }

    public void generateButton(ArrayList<String> arrayList) {


        final int[] colorIntArray = {R.color.md_red_500, R.color.colorPrimaryDark, R.color.md_amber_200, R.color.md_green_500, R.color.md_grey_500,
                R.color.md_brown_500, R.color.md_orange_500, R.color.md_pink_700, R.color.md_cyan_500, R.color.md_light_green_500,
                R.color.md_red_500, R.color.colorPrimaryDark, R.color.md_amber_200, R.color.md_green_500, R.color.md_grey_500,
                R.color.md_brown_500, R.color.md_orange_500, R.color.md_pink_700, R.color.md_cyan_500, R.color.md_light_green_500};

        for (int i = 0; i < arrayList.size(); i++) {

            if (i > 1) {


                Button button = new Button(getActivity());
                button.setText(arrayList.get(i).toUpperCase());
                button.setBackgroundColor(getActivity().getResources().getColor(colorIntArray[i]));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
               // params.setMargins(5, 5, 5, 5);
                button.setTag(i);
                button.setTextColor(Color.WHITE);
                button.setOnClickListener(BuscarCate.this);
                layout.addView(button, params);

            }

        }

    }

    @Override
    public void onClick(View view) {

        CategoriaFrag frag = new CategoriaFrag();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.catecontainer, frag, "gg");
        transaction.addToBackStack("j");
        Bundle bundle = new Bundle();
        bundle.putString("tabs", strings.get((int) view.getTag()));
        frag.setArguments(bundle);
        transaction.commit();


    }
}

