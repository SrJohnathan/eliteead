package ead.app.br.com.appelite.ead.componets.libs;

import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pc on 12/11/2016.
 */

public class CustonRadioGroup {

    private List<Macar> buttons;
    int n = 1000;

    public CustonRadioGroup() {
        buttons = new ArrayList<>();

    }

    public void add(RadioButton button, int position) {
        buttons.add(new Macar(button, position));


    }

    public void commitCick(int position) {

        if(n != 1000){
            buttons.get(n).getRadioButton().setChecked(false);

        }
        n = position;
        buttons.get(position).getRadioButton().setChecked(true);





    }

    class Macar {
        private RadioButton radioButton;
        private int positin;

        public Macar(RadioButton radioButton, int positin) {
            this.radioButton = radioButton;
            this.positin = positin;
        }

        public RadioButton getRadioButton() {
            return radioButton;
        }

        public int getPositin() {
            return positin;
        }
    }
}
