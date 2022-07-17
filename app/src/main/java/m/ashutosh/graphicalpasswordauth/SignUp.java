package m.ashutosh.graphicalpasswordauth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import m.ashutosh.toastertoast.Toaster;

public class SignUp extends AppCompatActivity {

    private EditText et,name;
    private String res = null;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button setPass = findViewById(R.id.btn_set_pass);
        Button btnRegister = findViewById(R.id.btn_register);
        et = findViewById(R.id.signup_email);
        name = findViewById(R.id.signup_name);

        firebaseAuth = FirebaseAuth.getInstance();

        setPass.setOnClickListener(view -> {
            if(TextUtils.isEmpty(et.getText().toString().trim()))
                et.setError("Required");
            else if(TextUtils.isEmpty(name.getText().toString().trim()))
                name.setError("Required");
            else {
                Intent i = new Intent(SignUp.this,PasswordActivity.class);
                startActivityForResult(i,191);
            }
        });

        btnRegister.setOnClickListener(view -> {

            String networkState = NetworkUtil.getNetworkState(SignUp.this);
            if (networkState.equals("Connected")) {
                if (res != null) {

                    progressDialog = new ProgressDialog(SignUp.this);
                    progressDialog.setCancelable(true);
                    progressDialog.setMessage("Please Wait");
                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(et.getText().toString().trim(), res).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendVerificationEmail();
                            } else {
                                Toaster.makeToast(SignUp.this, "An Error Occurred", Toaster.LENGTH_LONG, Toaster.ERROR);
                                progressDialog.dismiss();
                            }
                        }
                    });

                } else {
                    Toast.makeText(this, "Set Password first", Toast.LENGTH_SHORT).show();
                }
            } else
                Toaster.makeToast(this,"No Internet Connection",Toaster.LENGTH_SHORT,Toaster.DEFAULT);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 191) {
            if(resultCode == RESULT_OK) {
                if(data!=null) {
                    res = data.getStringExtra("pass");
                    new AlertDialog.Builder(this)
                            .setMessage("Password has been set. You can now register")
                            .setCancelable(false)
                            .setPositiveButton("ok", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                } else {
                    Toaster.makeToast(SignUp.this,"-74- SignUp",Toaster.LENGTH_LONG,Toaster.ERROR);
                }
            }
        }


    }

    private void sendVerificationEmail() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(task -> {
                progressDialog.dismiss();
                new AlertDialog.Builder(SignUp.this)
                        .setTitle("Register User")
                        .setIcon(R.drawable.ic_baseline_email)
                        .setMessage("A verification email has been sent to "+et.getText().toString().trim()+"\nVerify the email and then login")
                        .setCancelable(false)
                        .setPositiveButton("ok", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                            SignUp.this.finish();
                        }).create().show();
                firebaseAuth.signOut();
            });
        }
    }
}