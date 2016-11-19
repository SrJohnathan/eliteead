package ead.app.br.com.appelite.ead.dominio;

import java.io.Serializable;

/**
 * Created by PC on 07/03/2016.
 */
public class Mesagem implements Serializable {

    private int id;
    private String titulo;
    private String mensagem;
    private String hora;
    private  String data;
    private boolean leu;

    public Mesagem(int id, String titulo, String mensagem, String hora, String data, boolean leu) {
        this.id = id;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.hora = hora;
        this.data = data;
        this.leu = leu;
    }

    public Mesagem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isLeu() {
        return leu;
    }

    public void setLeu(boolean leu) {
        this.leu = leu;
    }
}
