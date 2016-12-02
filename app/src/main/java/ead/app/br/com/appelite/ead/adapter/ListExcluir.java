package ead.app.br.com.appelite.ead.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.List;

import ead.app.br.com.appelite.ead.R;
import ead.app.br.com.appelite.ead.dominio.MenuExcluir;


/**
 * Created by Pc on 18/06/2016.
 */
public class ListExcluir extends BaseAdapter {

    private List<MenuExcluir> excluirs;
    private Context context;
    private LayoutInflater inflater;

    public ListExcluir(Context context ) {

        MenuExcluir menuExcluir = new MenuExcluir("SIM",new IconicsDrawable(context).color(Color.GREEN).icon(CommunityMaterial.Icon.cmd_check).sizeDp(24),Color.GREEN);
        MenuExcluir menuExcluir2 = new MenuExcluir("NÃO",new IconicsDrawable(context).color(Color.RED).icon(CommunityMaterial.Icon.cmd_close).sizeDp(24),Color.RED);
        excluirs = new ArrayList();
        excluirs.add(menuExcluir);
        excluirs.add(menuExcluir2);

        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return excluirs.size();
    }

    @Override
    public Object getItem(int position) {
        return excluirs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyAdapter mydapter ;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.contextmenu,parent,false);
            mydapter = new MyAdapter();
            convertView.setTag(mydapter);
            mydapter.icom = (ImageView) convertView.findViewById(R.id.icon);
            mydapter.texo = (TextView) convertView.findViewById(R.id.texto);

        }else {
            mydapter = (MyAdapter) convertView.getTag();
        }


        mydapter.icom.setImageDrawable(excluirs.get(position).getIcom());
        mydapter.texo.setText(excluirs.get(position).getTexto());
        mydapter.texo.setTextColor(excluirs.get(position).getColor());




        return convertView;
    }

 private static class MyAdapter {
         TextView texo;
         ImageView icom;

    }
}
