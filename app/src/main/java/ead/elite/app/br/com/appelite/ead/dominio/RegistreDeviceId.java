package ead.elite.app.br.com.appelite.ead.dominio;

/**
 * Created by PC on 16/04/2016.
 */
public class RegistreDeviceId {

    public int id;
    public String regiter;

    public RegistreDeviceId(int id, String regiter) {
        this.id = id;
        this.regiter = regiter;
    }

    public int getId() {
        return id;
    }

    public String getRegiter() {
        return regiter;
    }
}
