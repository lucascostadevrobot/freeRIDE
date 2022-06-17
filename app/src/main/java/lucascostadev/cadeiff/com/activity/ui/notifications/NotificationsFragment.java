package lucascostadev.cadeiff.com.activity.ui.notifications;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import lucascostadev.cadeiff.com.R;
import lucascostadev.cadeiff.com.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }



    //Incializando o metodo para inflar o WebView
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String url = "hhttps://www.bbc.com/news/world";
        WebView web = (WebView) view.findViewById(R.id.webviewtres);

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