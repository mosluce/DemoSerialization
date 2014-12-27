package tw.ccmos.demo.serialization;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView displayNameTextView = (TextView) findViewById(R.id.displayNameTextView);
        final EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNameTextView.setText("正在登入中...");

                WebApiClient<LoginResult> client = new WebApiClient<LoginResult>("user/login", LoginResult.class);
                RequestParams params = new RequestParams();
                params.put("username", usernameEditText.getText().toString());
                params.put("password", passwordEditText.getText().toString());
                client.post(params, new WebApiClient.IWebApiResponseHandler<LoginResult>() {
                    @Override
                    public void success(WebApiClientResponse<LoginResult> response) {
                        displayNameTextView.setText(response.data.displayName);
                    }

                    @Override
                    public void failure(int statusCode, String errorMessage) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("錯誤")
                                .setMessage(String.format("%d - %s", statusCode, errorMessage))
                                .create().show();
                    }
                });
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class LoginResult {
        public String username;
        public String displayName;
    }
}
