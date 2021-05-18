package br.com.jcamelo.appfotos.database;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class OrdemServico extends RealmObject{
    @PrimaryKey
    @Required
    private String id = UUID.randomUUID().toString();
    @Required
    private String codigo;
    @Required
    private String tecnico;

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

        OrdemServico refClient = realm.where(OrdemServico.class).equalTo("id",this.getId()).findFirst();
        refClient.deleteFromRealm();

        realm.commitTransaction();
        realm.close();
    }

    public void update(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        OrdemServico refClient = realm.where(OrdemServico.class).equalTo("id",this.getId()).findFirst();
        //refClient.setName(this.getName());
        //refClient.setEmail(this.getEmail());
        //refClient.setCpf(this.getCpf());
        realm.commitTransaction();
        realm.close();
    }

    public static OrdemServico findByID(String id){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        OrdemServico c = realm.where(OrdemServico.class).equalTo("id",id).findFirst();

        OrdemServico novoCliente = new OrdemServico(c.getId(), c.getCodigo(), c.getTecnico());

        realm.commitTransaction();
        realm.close();
        return novoCliente;
    }

    public static OrdemServico findByCodigo(String codigo){
        OrdemServico novoCliente = null;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        OrdemServico c = realm.where(OrdemServico.class).equalTo("codigo", codigo).findFirst();

        if(c != null) {
            novoCliente = new OrdemServico(c.getId(), c.getCodigo(), c.getTecnico());
        }

        realm.commitTransaction();
        realm.close();

        return novoCliente;
    }

    public static List<OrdemServico> findAll(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<OrdemServico> clients = realm.where(OrdemServico.class).findAll();
        List<OrdemServico> OrderServicoList = new ArrayList<>();

        for (int i = 0; i < clients.size();i++){
            OrdemServico c = new OrdemServico(clients.get(i).getId(),clients.get(i).getCodigo(),clients.get(i).getTecnico());
            OrderServicoList.add(c);
        }

        realm.commitTransaction();
        realm.close();

        return OrderServicoList;
    }

    public String getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public OrdemServico(){

    }

    public OrdemServico(String codigo, String tecnico) {
        this.codigo = codigo;
        this.tecnico = tecnico;
    }

    public OrdemServico(String id, String codigo, String tecnico) {
        this.id = id;
        this.codigo = codigo;
        this.tecnico = tecnico;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", codigo='" + codigo + '\'' +
                ", tecnico='" + tecnico + '\'' +
                '}';
    }
}