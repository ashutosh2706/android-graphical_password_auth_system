package m.ashutosh.graphicalpasswordauth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import m.ashutosh.toastertoast.Toaster;

public class HomeActivity extends AppCompatActivity {

    private Toast backToast;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toaster.makeToast(this,"Welcome User",Toaster.LENGTH_LONG,Toaster.SUCCESS);
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