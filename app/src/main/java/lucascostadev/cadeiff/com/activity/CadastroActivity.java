package lucascostadev.cadeiff.com.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import lucascostadev.cadeiff.com.R;
import lucascostadev.cadeiff.com.configuracoes.ConfiguracaoFirebase;
import lucascostadev.cadeiff.com.helper.UsuarioFirebase;
import lucascostadev.cadeiff.com.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    //Declaranando minhas variaveis
    private TextInputEditText campoNome, campoEmail, campoSenha;
    private Switch switchTipoUsuario;

    //Variavel do tipo Firebase instancia no metodo cadastroUsuario
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //inicliazando e referenciando os componentes (varaiveis)
         campoNome = findViewById(R.id.editCadastroNome);
         campoEmail = findViewById(R.id.editCadastroEmail);
         campoSenha = findViewById(R.id.editCadastroSenha);
         switchTipoUsuario = findViewById(R.id.switchTipoUsuario);


    }

         //Metodo para validar o cadastro do usuario
    public void validarCadastroUsuario(View view) {
        //Recuperando os texto dos campos
        String textoNome = campoNome.getText().toString();
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        //Validando se os campos est??o vazios ou n??o...
        if (!textoNome.isEmpty()) {
            if (!textoEmail.isEmpty()) {
                if (!textoSenha.isEmpty()) {


                    //Instanciando o objeto Usuario
                    Usuario usuario = new Usuario();
                    usuario.setNome(textoNome);
                    usuario.setEmail(textoEmail);
                    usuario.setSenha(textoSenha);
                    //Definindo o tipo de usu??rio atrav??s do swicth
                    //Por padr??o ele ?? um passageiro, caso sinalize o swicth o mesmo ?? um motorista
                    usuario.setTipo(verificaTipoUsuario());

                    //Cadastrando usu??rio...
                     cadastrarUsuario(usuario);



                    //Eibindo uma mensagem caso o usuario esteja tentando cadastrar sem ter preenchido o campo Nome.
                } else {
                    Toast.makeText(CadastroActivity.this,
                            "Preencha a Senha!",
                            Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(CadastroActivity.this,
                        "Preencha o E-mail!",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(CadastroActivity.this,
                    "Preencha o Nome",
                    Toast.LENGTH_SHORT).show();
        }
    }


      //Esse metodo realiza o cadastro do usuario
      //Como parametro eu retorno o usu??rio e com isso ?? poss??vel cadastrar o usu??rio no Firebase
    public void cadastrarUsuario(Usuario usuario){

         autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
         autenticacao.createUserWithEmailAndPassword(
                 usuario.getEmail(),
                 usuario.getSenha()
         ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull  Task<AuthResult> task) {

                 //Verificando se obteve a persistncia do cadastro do usuario
                 //Tratando a persistencia dentro do tratamento de exce????s try e cacth
                 if ((task.isSuccessful())){
                   try {
                       String idUsuario = task.getResult().getUser().getUid();
                       usuario.setId(idUsuario);
                       usuario.salvar();


                       //Atualizando nome do Usuario no UserProfile do firbase
                       /*UserProfile do firebase ?? um recurso que nos permite recuperar
                       de forma muito mais simples os dados do usu??rio para exibirmos na tela
                       pois ?? muito custoso termos que trabalhar toda vez com requisi????es diretamente
                       no banco de dados. Com isso nossos dados ficam salvos e mais proximos
                       para recuperarmos
                        */
                       UsuarioFirebase.atualizarNomeUsuario(usuario.getNome());


                       //Validando o tipo de usuario
                       //Se o usuario for do tipo pasageiro invocaremos activity Maps
                       //Se n??o for, invocamos activity requisi????es
                       if(verificaTipoUsuario() =="P"){

                           startActivity(new Intent(CadastroActivity.this, PassageiroActivity.class));
                           finish();
                           Toast.makeText(CadastroActivity.this,
                                   "Parab??ns passageiro! Voc?? foi cadastrado no freeRIDE.",
                                   Toast.LENGTH_SHORT).show();

                       }else{

                           startActivity(new Intent(CadastroActivity.this, RequisicoesActivity.class));
                           finish();
                           Toast.makeText(CadastroActivity.this,
                                   "Parab??ns Motorista! Voc?? foi cadastrado no freeRIDE.",
                                   Toast.LENGTH_SHORT).show();
                       }


                   }catch (Exception e) {
                    e.printStackTrace();
                   }
                 // Relacionando meu tratamento de excess??es aos usu??rio
                 //Esse tratamento ?? feito de acordo com as parametriza????es padr??s do Firebase
                 }else{
                     String execao = "";
                     try {
                         throw task.getException();
                     }catch (FirebaseAuthWeakPasswordException e){
                         execao = "Digite uma senha mais forte!";
                     }catch (FirebaseAuthInvalidCredentialsException e){
                         execao = "Por favor, digite um -mail v??lido";
                     }catch (FirebaseAuthUserCollisionException e){
                         execao = "J?? existe uma conta com esses dados!";
                     }catch (Exception e){
                         execao = "Aconteceu um erro ao se cadastrar! Tente novamente." + e.getMessage();
                         e.printStackTrace();
                     }

                     Toast.makeText(CadastroActivity.this,
                             execao,
                             Toast.LENGTH_SHORT).show();
                 }
             }
         });

    }

     //Esse metodo verifica e retorna o tipo de usuario
     public String verificaTipoUsuario(){
          return switchTipoUsuario.isChecked() ? "M" : "P"; //ternario de condi????o para o return

     }
}