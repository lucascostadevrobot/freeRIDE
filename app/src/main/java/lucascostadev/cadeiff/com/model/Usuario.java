package lucascostadev.cadeiff.com.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import lucascostadev.cadeiff.com.configuracoes.ConfiguracaoFirebase;

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String tipo;


    //Incializando o construtor vazio da Classe Usuario
    public Usuario() {
    }

    //Metodo salvar | Para salvar os dados recuperados no database
    public void  salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase(); //Referencia ao database do firebase
        DatabaseReference usuarios = firebaseRef.child("usuarios").child(getId()); //Referencia ao usuario para salvar no nó usuario database

        usuarios.setValue(this); //Setando os valores que eu preciso salvar neste caso usei o this porque quero salvar todos os dados

    }


   //Gerando os Geters e Seters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude    //Anotação de exclusão. Como usei o this no metodo salvar para todos os dados. Aqui digo que não preciso salvar a senha
    public String getSenha() {
        return senha;
    }


    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
