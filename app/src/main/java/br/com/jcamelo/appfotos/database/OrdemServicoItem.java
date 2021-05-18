package br.com.jcamelo.appfotos.database;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;
import io.realm.annotations.Required;

public class OrdemServicoItem extends RealmObject{
    @PrimaryKey
    @Required
    private String id = UUID.randomUUID().toString();
    @Required
    private String codigoOs;
    @Required
    private String arquivo;
    @Required
    private String siglaEquipamento;

    public OrdemServicoItem() {
    }

    public OrdemServicoItem(String id, String codigoOs, String arquivo, String siglaEquipamento) {
        this.id = id;
        this.codigoOs = codigoOs;
        this.arquivo = arquivo;
        this.siglaEquipamento = siglaEquipamento;
    }

    public OrdemServicoItem(String codigo_os, String arquivo, String siglaEquipamento) {
        this.codigoOs = codigo_os;
        this.arquivo = arquivo;
        this.siglaEquipamento = siglaEquipamento;
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

    public void save(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.copyToRealm(this);

        realm.commitTransaction();
        realm.close();
    }

    public void delete(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        OrdemServicoItem refClient = realm.where(OrdemServicoItem.class).equalTo("id",this.getId()).findFirst();
        refClient.deleteFromRealm();

        realm.commitTransaction();
        realm.close();
    }

    public static OrdemServicoItem findByID(String id){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        OrdemServicoItem c = realm.where(OrdemServicoItem.class).equalTo("id",id).findFirst();

        OrdemServicoItem novoCliente = new OrdemServicoItem(c.getId(), c.getCodigoOs(), c.getArquivo(), c.getSiglaEquipamento());

        realm.commitTransaction();
        realm.close();
        return novoCliente;
    }

    public static OrdemServicoItem findByName(String name){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        OrdemServicoItem c = realm.where(OrdemServicoItem.class).equalTo("arquivo", name).findFirst();

        OrdemServicoItem novoCliente = new OrdemServicoItem(c.getId(), c.getCodigoOs(), c.getArquivo(), c.getSiglaEquipamento());

        realm.commitTransaction();
        realm.close();
        return novoCliente;
    }

    public static OrdemServicoItem findByCodigoOs(String codigoOs){
        OrdemServicoItem novoCliente = null;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        OrdemServicoItem c = realm.where(OrdemServicoItem.class).equalTo("codigo_os", codigoOs).findFirst();

        if(c != null) {
            novoCliente = new OrdemServicoItem(c.getId(), c.getCodigoOs(), c.getArquivo(), c.siglaEquipamento);
        }

        realm.commitTransaction();
        realm.close();

        return novoCliente;
    }

    public static List<OrdemServicoItem> getAllByOS(String codigoOs, String siglaEquipamento){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<OrdemServicoItem> clients = realm.where(OrdemServicoItem.class)
                .equalTo("codigoOs", codigoOs)
                .equalTo("siglaEquipamento", siglaEquipamento)
                .findAll();
        List<OrdemServicoItem> OrderServicoItemList = new ArrayList<>();

        for (int i = 0; i < clients.size();i++){
            OrdemServicoItem c = new OrdemServicoItem(clients.get(i).getId(),clients.get(i).getCodigoOs(),clients.get(i).getArquivo(), clients.get(i).getSiglaEquipamento());
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
                '}';
    }
}