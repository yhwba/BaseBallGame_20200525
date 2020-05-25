package kr.co.yhw.baseballgame_20200525;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupEvents();
        setVaules();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setVaules() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(mContext, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        },2000);
    }


//백버튼 눌러도 앱 안꺼지게
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
