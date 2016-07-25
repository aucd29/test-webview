package net.sarangnamu.testwebview;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private boolean mToggle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CoordinatorLayout base = (CoordinatorLayout) findViewById(R.id.base_layout);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mToggle ? "http://m.naver.com" : "http://m.daum.net";
                mToggle = !mToggle;

                WebView view = getWebView();
                view.loadUrl(url);

                base.addView(view);
            }
        });

        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cnt = base.getChildCount();
                if (cnt == 0) {
                    return ;
                }

                View view = base.getChildAt(cnt - 1);
                if (view instanceof WebView) {
                    base.removeView(view);
                }
            }
        });
    }

    private WebView getWebView() {
        WebView view = new WebView(this);

        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setDomStorageEnabled(true);
        view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        return view;
    }

    @Override
    public void onBackPressed() {
        CoordinatorLayout base = (CoordinatorLayout) findViewById(R.id.base_layout);
        int cnt = base.getChildCount();
        if (cnt == 0) {
            super.onBackPressed();
            return ;
        }

        WebView view = (WebView) base.getChildAt(cnt - 1);
        view.goBack();
    }
}
