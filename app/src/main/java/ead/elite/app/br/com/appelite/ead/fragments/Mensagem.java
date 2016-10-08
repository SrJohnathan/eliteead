package ead.elite.app.br.com.appelite.ead.fragments;

import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.List;

import ead.elite.app.br.com.appelite.ead.R;
import ead.elite.app.br.com.appelite.ead.adapter.AdapterMensagem;
import ead.elite.app.br.com.appelite.ead.adapter.ListExcluir;
import ead.elite.app.br.com.appelite.ead.bd.Database;
import ead.elite.app.br.com.appelite.ead.dominio.Mesagem;
import ead.elite.app.br.com.appelite.ead.interfaces.OnClickFrag;

/**
 * Created by Pc on 03/06/2016.
 */
public class Mensagem extends Fragment {

    private View view;
    private TextView tilulo,data,hora;
    private RecyclerView recyclerView;
    private AdapterMensagem mensagem;
    private ContextMenu contextMenu;
    private List<Mesagem> mesagems;
    MaterialDialog dialog;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.containertwo, container, false);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final Database database = new Database(getActivity());
        mesagems = database.buscar();
        mensagem = new AdapterMensagem(getActivity(),mesagems);

        if(mesagems.size() == 0){
            TextView textView = (TextView) view.findViewById(R.id.error);
            textView.setVisibility(View.VISIBLE);
            textView.setCompoundDrawables(new IconicsDrawable(getActivity()).sizeDp(30).color(Color.BLACK).icon(CommunityMaterial.Icon.cmd_alert_circle),null,null,null);
            textView.setText("  Caixa de Entrada Vazia");


        }else {

            recyclerView = (RecyclerView) view.findViewById(R.id.recy);
            recyclerView.setAdapter(mensagem);
            recyclerView.setLayoutManager(layoutManager);
        }









        mensagem.setExcluirClickMensage(new AdapterMensagem.OnClickMensage() {
            @Override
            public void Click(View view, final int positionn) {
                ListExcluir excluir = new ListExcluir(getActivity());
                final ListPopupWindow popupWindow = new ListPopupWindow(getActivity());
                popupWindow.setAnchorView(view);
                popupWindow.setWidth(50);
                popupWindow.setAdapter(excluir);
                popupWindow.setModal(true);
                popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case  0 :
                                Database database1 = new Database(getActivity());
                                database1.deleta(mesagems.get(positionn).getId());
                                popupWindow.dismiss();
                                mensagem.removeItem(positionn);
                                break;
                        }

                    }
                });

                popupWindow.show();


            }
        });

        mensagem.setOnClickFrag(new OnClickFrag() {
            @Override
            public void Click(View view, int position) {

                Database database1 = new Database(getActivity());
                database1.leitura(mesagems.get(position).getId(),true);
                mensagem.atualizar(new Database(getActivity()).buscar());
                NotificationManager manager = (NotificationManager) getActivity().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(55);
                View view1 = View.inflate(getActivity(),R.layout.opemensa,null);
                TextView data = (TextView) view1.findViewById(R.id.data);
                TextView hora = (TextView) view1.findViewById(R.id.hora);
                TextView texto = (TextView) view1.findViewById(R.id.texto);

                data.setText(mesagems.get(position).getData());
                hora.setText(mesagems.get(position).getHora());
                texto.setText(mesagems.get(position).getMensagem());

                MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                        .title(mesagems.get(position).getTitulo())
                        .positiveText("Fechar")
                        .positiveColor(Color.RED)
                        .customView(view1,false)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        });





                 dialog = builder.build();
                dialog.show();
            }
        });



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}

