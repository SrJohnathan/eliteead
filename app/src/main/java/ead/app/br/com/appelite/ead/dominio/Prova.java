package ead.app.br.com.appelite.ead.dominio;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 07/03/2016.
 */
public class Prova implements Parcelable {

    private int id;
    private String questao;
    private String pergunta;
    private String resA;
    private String resB;
    private String resC;
    private String resD;
    private String resCerta;
    private int incurso;
    private int iduser;
    private int acerto;

    public Prova(int id, String questao, String pergunta, String resA, String resB, String resC, String resD, String resCerta, int incurso, int iduser, int acerto) {
        this.id = id;
        this.questao = questao;
        this.pergunta = pergunta;
        this.resA = resA;
        this.resB = resB;
        this.resC = resC;
        this.resD = resD;
        this.resCerta = resCerta;
        this.incurso = incurso;
        this.iduser = iduser;
        this.acerto = acerto;
    }

    protected Prova(Parcel in) {
        id = in.readInt();
        questao = in.readString();
        pergunta = in.readString();
        resA = in.readString();
        resB = in.readString();
        resC = in.readString();
        resD = in.readString();
        resCerta = in.readString();
        incurso = in.readInt();
        iduser = in.readInt();
        acerto = in.readInt();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestao(String questao) {
        this.questao = questao;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public void setResA(String resA) {
        this.resA = resA;
    }

    public void setResB(String resB) {
        this.resB = resB;
    }

    public void setResC(String resC) {
        this.resC = resC;
    }

    public void setResD(String resD) {
        this.resD = resD;
    }

    public void setResCerta(String resCerta) {
        this.resCerta = resCerta;
    }

    public void setIncurso(int incurso) {
        this.incurso = incurso;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public void setAcerto(int acerto) {
        this.acerto = acerto;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(questao);
        dest.writeString(pergunta);
        dest.writeString(resA);
        dest.writeString(resB);
        dest.writeString(resC);
        dest.writeString(resD);
        dest.writeString(resCerta);
        dest.writeInt(incurso);
        dest.writeInt(iduser);
        dest.writeInt(acerto);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Prova> CREATOR = new Creator<Prova>() {
        @Override
        public Prova createFromParcel(Parcel in) {
            return new Prova(in);
        }

        @Override
        public Prova[] newArray(int size) {
            return new Prova[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getQuestao() {
        return questao;
    }

    public String getPergunta() {
        return pergunta;
    }

    public String getResA() {
        return resA;
    }

    public String getResB() {
        return resB;
    }

    public String getResC() {
        return resC;
    }

    public String getResD() {
        return resD;
    }

    public String getResCerta() {
        return resCerta;
    }

    public int getIncurso() {
        return incurso;
    }

    public int getIduser() {
        return iduser;
    }

    public int getAcerto() {
        return acerto;
    }
}
