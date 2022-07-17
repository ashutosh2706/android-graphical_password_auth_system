package m.ashutosh.graphicalpasswordauth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import m.ashutosh.toastertoast.Toaster;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Toast backToast;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.email);
        Button signup = findViewById(R.id.btn_signup);
        Button login = findViewById(R.id.btn_login);


/*
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        try {
            if (firebaseUser.isEmailVerified())
                Toast.makeText(this, "Verified email", Toast.LENGTH_SHORT).show();
        }catch (NullPointerException e) {
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }

 */
        signup.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,SignUp.class)));

        login.setOnClickListener(view -> {

            String status = NetworkUtil.getNetworkState(this);
            if(status.equals("Connected")) {

                if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                    editText.setError("Required");
                } else {
                    String mail = editText.getText().toString().trim();

                    Intent i = new Intent(MainActivity.this, PasswordActivity.class);
                    i.putExtra("key", mail);
                    startActivity(i);
                    finish();
                }
            }else
                Toaster.makeToast(this,"No Internet Connection",Toaster.LENGTH_SHORT,Toaster.ERROR);
        });
    }

    public void forgot_pass(View view) {

        new AlertDialog.Builder(this)
                .setTitle("Forgot Password ?")
                .setCancelable(false)
                .setMessage("Contact project maintainer Ashutosh ( imashutosh2706@gmail.com ) to get your password reset")
                .setPositiveButton("ok", (dialogInterface, i) -> {
                }).create().show();
/*
        View v = getLayoutInflater().inflate(R.layout.email_field, null);
        EditText et = v.findViewById(R.id.email_field_layout);

        new AlertDialog.Builder(this)
                .setTitle("Enter email")
                .setView(v)
                .setCancelable(false)
                .setPositiveButton("ok", (dialogInterface, i) -> {
                    String email = et.getText().toString().trim();
                    if(!test.equals(noEmail)) {
                        Intent intent = new Intent(MainActivity.this,PasswordActivity.class);
                        intent.putExtra("key",email);
                        intent.putExtra("resetMail",true);
                        startActivity(intent);
                    }
                    else {
                        Toaster.makeToast(MainActivity.this, "Enter valid email.", Toaster.LENGTH_SHORT, Toaster.DEFAULT);
                    }
                }).setNegativeButton("cancel", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
*/
    }

    @Override
    public void onBackPressed() {
        if (time + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(),"Press back again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }
        time = System.currentTimeMillis();
    }
}