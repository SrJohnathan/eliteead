package ead.elite.app.br.com.appelite.ead.dominio;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 07/03/2016.
 */
public class Curso implements Parcelable {
    private int id;
    private String nome;
    private String categoria;
    private String horas;
    private String intrutor;
    private int foto;
    private String sku;
    private String fotoinstrutor;
    private String descrisao;
    private String sumario;
    private String publico;
    private String preco;
    private int inscritos;
    private boolean pago;

    public Curso() {
    }

    public Curso(int id, String sku, String nome, String categoria, String horas, String intrutor, int foto, String fotoinstrutor, String descrisao, String sumario, String publico, String preco, int inscritos,boolean pago) {
        this.id = id;
        this.nome = nome;
        this.sku = sku;
        this.categoria = categoria;
        this.horas = horas;
        this.intrutor = intrutor;
        this.foto = foto;
        this.fotoinstrutor = fotoinstrutor;
        this.descrisao = descrisao;
        this.sumario = sumario;
        this.publico = publico;
        this.preco = preco;
        this.inscritos = inscritos;
        this.pago = pago;
    }


    protected Curso(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        categoria = in.readString();
        horas = in.readString();
        intrutor = in.readString();
        foto = in.readInt();
        sku = in.readString();
        fotoinstrutor = in.readString();
        descrisao = in.readString();
        sumario = in.readString();
        publico = in.readString();
        preco = in.readString();
        inscritos = in.readInt();
        pago = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeString(categoria);
        dest.writeString(horas);
        dest.writeString(intrutor);
        dest.writeInt(foto);
        dest.writeString(sku);
        dest.writeString(fotoinstrutor);
        dest.writeString(descrisao);
        dest.writeString(sumario);
        dest.writeString(publico);
        dest.writeString(preco);
        dest.writeInt(inscritos);
        dest.writeByte((byte) (pago ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Curso> CREATOR = new Creator<Curso>() {
        @Override
        public Curso createFromParcel(Parcel in) {
            return new Curso(in);
        }

        @Override
        public Curso[] newArray(int size) {
            return new Curso[size];
        }
    };

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getIntrutor() {
        return intrutor;
    }

    public void setIntrutor(String intrutor) {
        this.intrutor = intrutor;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getFotoinstrutor() {
        return fotoinstrutor;
    }

    public void setFotoinstrutor(String fotoinstrutor) {
        this.fotoinstrutor = fotoinstrutor;
    }

    public String getDescrisao() {
        return descrisao;
    }

    public void setDescrisao(String descrisao) {
        this.descrisao = descrisao;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public String getPublico() {
        return publico;
    }

    public void setPublico(String publico) {
        this.publico = publico;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public int getInscritos() {
        return inscritos;
    }

    public void setInscritos(int inscritos) {
        this.inscritos = inscritos;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }
}
