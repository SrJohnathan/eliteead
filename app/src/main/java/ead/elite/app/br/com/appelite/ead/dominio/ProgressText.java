package ead.elite.app.br.com.appelite.ead.dominio;

/**
 * Created by Pc on 26/09/2016.
 */

public class ProgressText {
    private String nameClass;
    private String progrs;

    public ProgressText(String nameClass, String progrs) {
        this.nameClass = nameClass;
        this.progrs = progrs;
    }

    public ProgressText() {
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getProgrs() {
        return progrs;
    }

    public void setProgrs(String progrs) {
        this.progrs = progrs;
    }
}
