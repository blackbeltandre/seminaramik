package tulisandigital.com.swipe;

/**
 * Created by PDAK-Dev on 11/8/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MateriFragment extends Fragment{
    public WebView mWebView;
    public MateriFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.materiinterface, container, false);
        mWebView = (WebView) v.findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String pdfURL = "http://amikmobile.tulisandigital.com/api/materi.pptx";
        mWebView.loadUrl(
                "http://docs.google.com/gview?embedded=true&url=" + pdfURL);
        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new Callback());
        return v;
    }
    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return(false);
        }
    }
}