package m.ashutosh.graphicalpasswordauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

import m.ashutosh.toastertoast.Toaster;

public class PasswordActivity extends AppCompatActivity {

    private final ImageView[][] buttons = new ImageView[4][4];
    private StringBuffer sb;
    private FirebaseAuth firebaseAuth;
    private boolean fruitImg = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        getSupportActionBar().setTitle("Enter Password");


        firebaseAuth = FirebaseAuth.getInstance();
        sb = new StringBuffer("");


        for (int i=0;i<4;i++) {
            for (int j = 0; j < 4; j++) {
                String btnID = "btn_pass_" + i + j;
                int resID = getResources().getIdentifier(btnID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setTag("xy");
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                setTags(i,j);
                setFruitImage(i,j);
                final int x = i, y = j;
                buttons[x][y].setOnClickListener(view -> sb.append(buttons[x][y].getTag().toString()));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_password,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.password_submit) {
            if(!sb.toString().equals(""))
                checkPassword();
        }else if(id == R.id.swap_img) {
            swapImages();
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkPassword() {

        Bundle extra = getIntent().getExtras();
        if(extra!=null) {

            if(validPassword(sb.toString())) {
                String val = extra.getString("key");

                String networkState = NetworkUtil.getNetworkState(this);
                if (networkState.equals("Connected")) {
                    ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Checking Password");
                    progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(val, sb.toString()).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(PasswordActivity.this, HomeActivity.class));
                            progressDialog.dismiss();
                            finish();
                        } else {
                            progressDialog.dismiss();
                            new AlertDialog.Builder(PasswordActivity.this)
                                    .setTitle("Incorrect password")
                                    .setCancelable(true)
                                    .setIcon(R.drawable.ic_baseline_warning)
                                    .setPositiveButton("ok", (dialogInterface, i) -> dialogInterface.dismiss())
                                    .create().show();
                            sb.delete(0, sb.length());
                        }
                    });
                } else
                    Toaster.makeToast(this, "No Internet Connection", Toaster.LENGTH_SHORT, Toaster.ERROR);
                //
            }else {
                Toaster.makeToast(this,"Select at least 3 images",Toaster.LENGTH_SHORT,Toaster.DEFAULT);
            }


        } else {
            if(validPassword(sb.toString())) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("pass", sb.toString());
                setResult(RESULT_OK, returnIntent);
                finish();
            }else {
                Toaster.makeToast(this,"Select at least 3 images",Toaster.LENGTH_SHORT,Toaster.DEFAULT);
            }
        }
    }

    private void setFruitImage(int i,int j) {
        String imgID = buttons[i][j].getTag().toString();
        switch (imgID) {
            case "00":
                buttons[i][j].setImageResource(R.drawable.img_00);
                break;
            case "01":
                buttons[i][j].setImageResource(R.drawable.img_01);
                break;
            case "02":
                buttons[i][j].setImageResource(R.drawable.img_02);
                break;
            case "03":
                buttons[i][j].setImageResource(R.drawable.img_03);
                break;
            case "10":
                buttons[i][j].setImageResource(R.drawable.img_10);
                break;
            case "11":
                buttons[i][j].setImageResource(R.drawable.img_11);
                break;
            case "12":
                buttons[i][j].setImageResource(R.drawable.img_12);
                break;
            case "13":
                buttons[i][j].setImageResource(R.drawable.img_13);
                break;
            case "20":
                buttons[i][j].setImageResource(R.drawable.img_20);
                break;
            case "21":
                buttons[i][j].setImageResource(R.drawable.img_21);
                break;
            case "22":
                buttons[i][j].setImageResource(R.drawable.img_22);
                break;
            case "23":
                buttons[i][j].setImageResource(R.drawable.img_23);
                break;
            case "30":
                buttons[i][j].setImageResource(R.drawable.img_30);
                break;
            case "31":
                buttons[i][j].setImageResource(R.drawable.img_31);
                break;
            case "32":
                buttons[i][j].setImageResource(R.drawable.img_32);
                break;
            case "33":
                buttons[i][j].setImageResource(R.drawable.img_33);
                break;


        }
    }

    private void setAlphaImage(int i,int j) {
        String imgID = buttons[i][j].getTag().toString();
        switch (imgID) {
            case "00":
                buttons[i][j].setImageResource(R.drawable.ic_00);
                break;
            case "01":
                buttons[i][j].setImageResource(R.drawable.ic_01);
                break;
            case "02":
                buttons[i][j].setImageResource(R.drawable.ic_02);
                break;
            case "03":
                buttons[i][j].setImageResource(R.drawable.ic_03);
                break;
            case "10":
                buttons[i][j].setImageResource(R.drawable.ic_10);
                break;
            case "11":
                buttons[i][j].setImageResource(R.drawable.ic_11);
                break;
            case "12":
                buttons[i][j].setImageResource(R.drawable.ic_12);
                break;
            case "13":
                buttons[i][j].setImageResource(R.drawable.ic_13);
                break;
            case "20":
                buttons[i][j].setImageResource(R.drawable.ic_20);
                break;
            case "21":
                buttons[i][j].setImageResource(R.drawable.ic_21);
                break;
            case "22":
                buttons[i][j].setImageResource(R.drawable.ic_22);
                break;
            case "23":
                buttons[i][j].setImageResource(R.drawable.ic_23);
                break;
            case "30":
                buttons[i][j].setImageResource(R.drawable.ic_30);
                break;
            case "31":
                buttons[i][j].setImageResource(R.drawable.ic_31);
                break;
            case "32":
                buttons[i][j].setImageResource(R.drawable.ic_32);
                break;
            case "33":
                buttons[i][j].setImageResource(R.drawable.ic_33);
                break;


        }
    }

    private void setTags(int i,int j) {

        while (true) {
            boolean tagUsed = false;
            String tag = genRand();
            for(int a=0;a<4;a++) {
                for (int b = 0; b < 4; b++) {
                    if(buttons[a][b].getTag().toString().equals(tag)){
                        tagUsed = true;
                    }
                }
            }
            if(!tagUsed) {
                buttons[i][j].setTag(tag);
                return;
            }

        }
    }

    private String genRand() {
        Random random = new Random();
        int i = random.nextInt(4);
        int j = random.nextInt(4);
        return ""+i+j;
    }


    private void swapImages() {
        if(fruitImg) {
            // set alpha
            for(int i=0;i<4;i++)
                for(int j=0; j<4; j++)
                    setAlphaImage(i,j);
            fruitImg = false;
        }else {
            //set fruit
            for(int i=0;i<4;i++)
                for(int j=0; j<4; j++)
                    setFruitImage(i,j);
            fruitImg = true;
        }
    }

    private boolean validPassword(String password) {
        return password.length() >= 6;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}