package lucascostadev.cadeiff.com.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import lucascostadev.cadeiff.com.R;
import lucascostadev.cadeiff.com.configuracoes.ConfiguracaoFirebase;
import lucascostadev.cadeiff.com.helper.UsuarioFirebase;
import lucascostadev.cadeiff.com.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText campoEmail, campoSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Incializando os componentes
        campoEmail = findViewById(R.id.editLoginEmail);
        campoSenha =  findViewById(R.id.editLoginSenha);

    }

    public void validarLoginUsuario(View view){

        //Recuperando os textos dos campos.
        String textEmail = campoEmail.getText().toString();
        String textSenha = campoSenha.getText().toString();

        //Verificar se o e-mail estiver vazio
        if (!textEmail.isEmpty()){
            if (!textSenha.isEmpty()){
                Usuario usuario = new Usuario();
                usuario.setEmail(textEmail);
                usuario.setSenha(textSenha);

        //Chamando o metodo usuario passando o objeto usuario como parametro.
                logarUsuario(usuario);

            }else {
                Toast.makeText(LoginActivity.this,
                        "Por favor, preencha a sua senha!",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(LoginActivity.this,
                    "Por favor, preencha o -mail!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para autenticar o usuario já existente que deseja fazer o login
    public void logarUsuario(Usuario usuario){

          autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
          autenticacao.signInWithEmailAndPassword(
                  usuario.getEmail(), usuario.getSenha()
          ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              //Verificar se o usuario autenticou e o redireciona para sua atividade de acordo com o tipo de usuario
              @Override
              public void onComplete(@NonNull  Task<AuthResult> task) {
                  //Realizando atenticacao dentro de um tratamento de exceções
                 if (task.isSuccessful()){

                     //Se  o usuario for autenticado com sucesso, verificamos o tipo de usuario
                     //Chamando o metodo para verificação
                     UsuarioFirebase.redirecionandoUsuarioLogado(LoginActivity.this);

                 }else{
                       String excecao = "";
                       try {
                           throw task.getException();
                       }catch (FirebaseAuthInvalidUserException e){
                           excecao = "Esse usuário não esta cadastrado";
                       }catch (FirebaseAuthInvalidCredentialsException e){
                           excecao =  "O e-mail digitado não correspondi a nenhuma conta.";
                       }catch (Exception e){
                           excecao = "Erro para cadastrar o usuario" + e.getMessage();
                           e.printStackTrace();
                       }
                       Toast.makeText(LoginActivity.this,
                               excecao,
                               Toast.LENGTH_SHORT).show();

                 }
              }
          });
    }
}