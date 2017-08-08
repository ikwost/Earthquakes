package ru.inno.earthquakes.presentation.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.inno.earthquakes.R;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.info_got_it)
    void onReadyAction(){
        finish();
    }

    public static Intent getStartInfo(Context callingContext) {
        return new Intent(callingContext, InfoActivity.class);
    }
}
