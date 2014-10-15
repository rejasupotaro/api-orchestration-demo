package com.example.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainActivity extends Activity {
    private List<String> inputHistory = new ArrayList<>();
    private ArrayAdapter<String> inputHistoryAdapter;

    @InjectView(R.id.url_text)
    AutoCompleteTextView urlTextView;
    @InjectView(R.id.output_text)
    TextView outputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        inputHistoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                inputHistory);
        urlTextView.setAdapter(inputHistoryAdapter);
    }

    @OnClick(R.id.submit_button)
    void onSubmitButtonClick() {
        final String url = urlTextView.getText().toString();
        if (!URLUtil.isHttpUrl(url)) {
            return;
        }

        inputHistoryAdapter.add(url);
        inputHistoryAdapter.notifyDataSetChanged();

        urlTextView.setText("");
        outputTextView.setText("Connecting...");

        ApiClient.createGetRequest(url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(handleError)
                .subscribe(updateOutputText);
    }

    private Action1<Throwable> handleError = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private Action1<String> updateOutputText = new Action1<String>() {
        @Override
        public void call(String output) {
            outputTextView.setText(output);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
