package br.com.jcamelo.appfotos.database;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class OrdemServicoItem extends RealmObject {
    @PrimaryKey
    @Required
    private String id = UUID.randomUUID().toString();
    @Required
    private String codigoOs;
    @Required
    private String arquivo;
    @Required
    private String siglaEquipamento;
    private Integer tipo; //1 = Foto, 2 = Videos, 3 = arquivos de texto

    public OrdemServicoItem() {
    }

    public OrdemServicoItem(String id, String codigoOs, String arquivo, String siglaEquipamento, Integer tipo) {
        this.id = id;
        this.codigoOs = codigoOs;
        this.arquivo = arquivo;
        this.siglaEquipamento = siglaEquipamento;
        this.tipo = tipo;
    }

    public OrdemServicoItem(String codigo_os, String arquivo, String siglaEquipamento, Integer tipo) {
        this.codigoOs = codigo_os;
        this.arquivo = arquivo;
        this.siglaEquipamento = siglaEquipamento;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigoOs() {
        return codigoOs;
    }

    public void setCodigoOs(String codigoOs) {
        this.codigoOs = codigoOs;
    }

    public String getSiglaEquipamento() {
        return siglaEquipamento;
    }

    public void setSiglaEquipamento(String siglaEquipamento) {
        this.siglaEquipamento = siglaEquipamento;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public void save() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.copyToRealm(this);

        realm.commitTransaction();
        realm.close();
    }

    public void delete() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        OrdemServicoItem refClient = realm.where(OrdemServicoItem.class).equalTo("id", this.getId()).findFirst();
        refClient.deleteFromRealm();

        realm.commitTransaction();
        realm.close();
    }

    public static OrdemServicoItem findByID(String id) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        OrdemServicoItem c = realm.where(OrdemServicoItem.class).equalTo("id", id).findFirst();

        OrdemServicoItem novoCliente = new OrdemServicoItem(c.getId(), c.getCodigoOs(), c.getArquivo(), c.getSiglaEquipamento(), c.getTipo());

        realm.commitTransaction();
        realm.close();
        return novoCliente;
    }

    public static OrdemServicoItem findByName(String name) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        OrdemServicoItem c = realm.where(OrdemServicoItem.class)
                .equalTo("arquivo", name)
                .findFirst();

        OrdemServicoItem novoCliente = new OrdemServicoItem(c.getId(), c.getCodigoOs(), c.getArquivo(), c.getSiglaEquipamento(), c.getTipo());

        realm.commitTransaction();
        realm.close();
        return novoCliente;
    }

    public static OrdemServicoItem findByCodigoOs(String codigoOs) {
        OrdemServicoItem novoCliente = null;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        OrdemServicoItem c = realm.where(OrdemServicoItem.class)
                .equalTo("codigo_os", codigoOs)
                .findFirst();

        if (c != null) {
            novoCliente = new OrdemServicoItem(c.getId(), c.getCodigoOs(), c.getArquivo(), c.getSiglaEquipamento(), c.getTipo());
        }

        realm.commitTransaction();
        realm.close();

        return novoCliente;
    }

    public static List<OrdemServicoItem> findAll() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<OrdemServicoItem> list = realm.where(OrdemServicoItem.class)
                .findAll();
        List<OrdemServicoItem> orderServicoItemList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            OrdemServicoItem c = new OrdemServicoItem(
                    list.get(i).getId(),
                    list.get(i).getCodigoOs(),
                    list.get(i).getArquivo(),
                    list.get(i).getSiglaEquipamento(),
                    list.get(i).getTipo()
            );
            orderServicoItemList.add(c);
        }

        realm.commitTransaction();
        realm.close();

        return orderServicoItemList;
    }

    public static List<OrdemServicoItem> getAllByOS(String codigoOs, String siglaEquipamento, Integer tipo) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<OrdemServicoItem> list = realm.where(OrdemServicoItem.class)
                .equalTo("codigoOs", codigoOs)
                .equalTo("tipo", tipo)
                .equalTo("siglaEquipamento", siglaEquipamento)
                .findAll();
        List<OrdemServicoItem> OrderServicoItemList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            OrdemServicoItem c = new OrdemServicoItem(
                    list.get(i).getId(),
                    list.get(i).getCodigoOs(),
                    list.get(i).getArquivo(),
                    list.get(i).getSiglaEquipamento(),
                    list.get(i).getTipo()
            );
            OrderServicoItemList.add(c);
        }

        realm.commitTransaction();
        realm.close();

        return OrderServicoItemList;
    }

    @Override
    public String toString() {
        return "OrdemServicoItem{" +
                "id='" + id + '\'' +
                ", codigoOs='" + codigoOs + '\'' +
                ", arquivo='" + arquivo + '\'' +
                ", siglaEquipamento='" + siglaEquipamento + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}