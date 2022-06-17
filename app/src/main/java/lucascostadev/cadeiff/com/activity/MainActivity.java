package lucascostadev.cadeiff.com.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import lucascostadev.cadeiff.com.R;
import lucascostadev.cadeiff.com.configuracoes.ConfiguracaoFirebase;
import lucascostadev.cadeiff.com.helper.Permissoes;
import lucascostadev.cadeiff.com.helper.UsuarioFirebase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    //Criando um Array de String para trabalhar com as permissoes.
    //Uma vez que eu trabalho as permissões na MainActivity, nao é preciso utilizar em todas as futuras classes de maps ou firebase
    private String [] permissoes = new String[]{

            Manifest.permission.ACCESS_FINE_LOCATION
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Escondendo nossa ActionBar da MainActivity
        getSupportActionBar().hide();

        //Validando as permissoes
        Permissoes.validarPermissoes(permissoes, this, 1);

        //Recuperando metodo para deslogar um usuário
       /* autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signOut();*/
    }



        //Instanciado nossas duas telas de cadastro na Activity principal
        //Eu faço a chamada pelo onClick e starto ao clique para que a tela selecionada seja Startada
        public void abrirTelaLogin(View view){
                startActivity(new Intent(this, LoginActivity.class));
        }

        public void abrirTelaCadastro(View view){
               startActivity(new Intent(this, CadastroActivity.class));

    }

    //Realizando configuraçã para que o usuario que estiver logado não precise logar novamente
    //Metodo onStart Reservado


    @Override
    protected void onStart() {
        super.onStart();
        UsuarioFirebase.redirecionandoUsuarioLogado(MainActivity.this);
    }

    //Metodo onRequestPermissions para validação das permissões passando como Parametro o array e grantResults
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int permissaoResultado : grantResults){
            if(permissaoResultado == PackageManager.PERMISSION_DENIED){
                alertaValidarPermissao();
            }
        }
    }

    //Metodo alertaValidarPermissao que esta sendo invocado no onRequest
    private void alertaValidarPermissao(){
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o aplicativo é necessário que você aceite as Permissõs em tempo de Execução. Estamos tratando" +
                "os seus dados com toda segurança e responsabilidade possível!");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}