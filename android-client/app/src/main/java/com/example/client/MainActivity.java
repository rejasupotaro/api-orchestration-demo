package com.example.client;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.client.models.User;
import com.example.client.services.ServiceProvider;
import com.example.client.services.UserService;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

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
        readInputHistories();

        ServiceProvider.get(UserService.class, "http://192.168.3.12:3000")
                .subscribe(new Action1<UserService>() {
                    @Override
                    public void call(UserService userService) {
                        userService.get(1)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnError(new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        Log.e(TAG, throwable.getMessage());
                                    }
                                })
                                .subscribe(new Action1<User>() {
                                    @Override
                                    public void call(User user) {
                                        Log.e(TAG, "id: " + user.getId() + ", name: " + user.getName());
                                    }
                                });
                    }
                });
    }

    @Override
    public void onStop() {
        saveInputHistories();
        super.onStop();
    }

    private void readInputHistories() {
        InputHistoryManager.read(this).subscribe(setupInputHistoryAdapter);
    }

    private void saveInputHistories() {
        List<String> inputHistories = new ArrayList<>();
        for (int i = 0; i < inputHistoryAdapter.getCount(); i++) {
            String item = inputHistoryAdapter.getItem(i).trim();
            if (!TextUtils.isEmpty(item)) {
                inputHistories.add(item);
            }
        }
        InputHistoryManager.write(this, inputHistories);
    }

    private void clearInputHistories() {
        InputHistoryManager.clear(this);
        inputHistoryAdapter.clear();
        inputHistoryAdapter.notifyDataSetChanged();
    }

    private Action1<List<String>> setupInputHistoryAdapter = new Action1<List<String>>() {
        @Override
        public void call(List<String> t) {
            inputHistoryAdapter = new ArrayAdapter<>(
                    MainActivity.this,
                    android.R.layout.simple_dropdown_item_1line,
                    t);
            urlTextView.setAdapter(inputHistoryAdapter);
        }
    };

    @OnClick(R.id.submit_button)
    void onSubmitButtonClick() {
        final String url = urlTextView.getText().toString();
        if (!URLUtil.isHttpUrl(url)) {
            return;
        }

        if (inputHistoryAdapter != null) {
            inputHistoryAdapter.add(url);
            inputHistoryAdapter.notifyDataSetChanged();
        }

        urlTextView.setText("");
        outputTextView.setText("Connecting...");

        ApiClient.createGetRequest(url)
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
        if (id == R.id.action_clear) {
            clearInputHistories();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
