package ead.elite.app.br.com.appelite.ead.dominio;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 07/03/2016.
 */
public class Aluno implements Parcelable {
    private int id;
    private String foto;
    private String nome;
    private String nomec;
    private String telefone;
    private String email;
    private String senha;
    private String status;

    public Aluno(int id, String foto, String nome, String nomec, String telefone, String email, String senha, String status) {
        this.id = id;
        this.foto = foto;
        this.nome = nome;
        this.nomec = nomec;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.status = status;
    }

    protected Aluno(Parcel in) {
        id = in.readInt();
        foto = in.readString();
        nome = in.readString();
        nomec = in.readString();
        telefone = in.readString();
        email = in.readString();
        senha = in.readString();
        status = in.readString();
    }

    public static final Creator<Aluno> CREATOR = new Creator<Aluno>() {
        @Override
        public Aluno createFromParcel(Parcel in) {
            return new Aluno(in);
        }

        @Override
        public Aluno[] newArray(int size) {
            return new Aluno[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomec() {
        return nomec;
    }

    public void setNomec(String nomec) {
        this.nomec = nomec;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(foto);
        parcel.writeString(nome);
        parcel.writeString(nomec);
        parcel.writeString(telefone);
        parcel.writeString(email);
        parcel.writeString(senha);
        parcel.writeString(status);
    }
}
