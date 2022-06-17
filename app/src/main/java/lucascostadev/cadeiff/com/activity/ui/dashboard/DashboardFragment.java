package lucascostadev.cadeiff.com.activity.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.WidgetContainer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.zip.Inflater;

import lucascostadev.cadeiff.com.R;
import lucascostadev.cadeiff.com.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    //Variavel para instanciar nosso WebView
    public WebView mWebView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;


    }

    //Incializando o metodo para inflar o WebView
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String url = "https://g1.globo.com/";
        WebView web = (WebView) view.findViewById(R.id.webviewum);

        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setPluginState(WebSettings.PluginState.ON);
        web.setWebViewClient(new WebViewClient());

        web.loadUrl(url);

    }
    //Fim Incializando o metodo para inflar o WebView



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}