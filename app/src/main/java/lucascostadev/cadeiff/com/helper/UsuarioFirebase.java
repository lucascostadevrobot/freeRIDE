package lucascostadev.cadeiff.com.helper;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import lucascostadev.cadeiff.com.activity.PassageiroActivity;
import lucascostadev.cadeiff.com.activity.RequisicoesActivity;
import lucascostadev.cadeiff.com.configuracoes.ConfiguracaoFirebase;
import lucascostadev.cadeiff.com.model.Usuario;

public class UsuarioFirebase {

    //Recuperando o usuario atual da sessão
    public static FirebaseUser getUsuarioAtual(){
        FirebaseAuth usuario = ConfiguracaoFirebase.getFirebaseAutenticacao();
        return  usuario.getCurrentUser();
    }

    public static boolean atualizarNomeUsuario(String nome){
      try {
          //Recuperar o firebaseUser
          FirebaseUser user = getUsuarioAtual();
          UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                  .setDisplayName(nome)
                  .build();

          user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull  Task<Void> task) {
                  //Exibir uma mensagem caso ocorrar um erro ao atualizar o nome do usuario atual
                  if (!task.isSuccessful()){
                      Log.d("Perfil", "Erro ao atualizar o nome do perfil do usuário!");

                  }

              }
          });
          return  true;
      }catch (Exception e){
          e.printStackTrace();
          return  false;
      }

    }

    //Metodo que invocamos no LoginActivity para redirecionar o usuario de acordo com seu tipo para atividade
    public static void redirecionandoUsuarioLogado(Activity activity){

        //Validando se o usuario esta logado referenciado no onStart MainActivity
        //Se usuario não estiver logado, nenhum redirecionamento é feito
        FirebaseUser user = getUsuarioAtual();
        if (user != null){
            //Realizando uma consulta no firebase
            DatabaseReference usuarioRef = ConfiguracaoFirebase.getFirebaseDatabase()
                    .child("usuarios")
                    .child(getIdentificadorUsuario());
            //Realizando a pesquisa para buscar o valor
            usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange( DataSnapshot dataSnapshot ) {
                    //Metodo que recupera um usuario
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    String tipoUsuario = usuario.getTipo();

                    if (tipoUsuario.equals("M")) {

                        Intent i = new Intent(activity, RequisicoesActivity.class);
                        activity.startActivity(i);


                    } else {
                        Intent i = new Intent(activity, PassageiroActivity.class);
                        activity.startActivity(i);

                    }
                }

                @Override
                public void onCancelled(@NonNull  DatabaseError error) {

                }
            });
        }







    }

    //Identificando o usario no Firebase
    //Neste metodo diferente do Cadastro Usuario, retornamos apenas o Id do Usuario vara validar autenticação.
    public static String getIdentificadorUsuario(){

        return  getUsuarioAtual().getUid();
    }


}
