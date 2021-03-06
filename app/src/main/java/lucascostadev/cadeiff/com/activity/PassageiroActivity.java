package lucascostadev.cadeiff.com.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import lucascostadev.cadeiff.com.R;
import lucascostadev.cadeiff.com.configuracoes.ConfiguracaoFirebase;

public class PassageiroActivity extends  AppCompatActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passageiro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Iniciar uma viagem");
        setSupportActionBar(toolbar);   //Verificar porque está aparecendo duas actionbAR

        //Configurações iniciais
        //Criando e passando Objeto de autenticacao FirebaseAutenticacao
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    //Chamando metodo referenciar nosso menu e inflando o mesmo...
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Metodo onde estamos criando o Switch case para trabalharmos com um menu estruturado de forma correta
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuSair :
                autenticacao.signOut(); //Deslogar
                finish(); //Finalizo a interface
                break;

            case R.id.menuNoticias:
                startActivity(new Intent(this, NoticiasRide.class));
                finish();
                break;

                /*Remover
                fragment_dashboard.xml
                fragment_home.xml
                fragment_notification_ride.xml

                activity_noticias.xml
                activity_notifications.xml

                Pasta ui e suas activitys

                 */


        }



        return super.onOptionsItemSelected(item);
    }

}
