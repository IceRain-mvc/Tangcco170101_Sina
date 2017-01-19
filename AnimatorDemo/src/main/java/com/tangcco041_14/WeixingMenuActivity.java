package com.tangcco041_14;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class WeixingMenuActivity extends AppCompatActivity {
    private List<ImageView> imageViewList;
    private int[] imageArray = new int[]{
            R.id.iv_a,
            R.id.iv_b,
            R.id.iv_c, R.id.iv_d,
            R.id.iv_e, R.id.iv_f,
            R.id.iv_g, R.id.iv_h};
    private boolean isStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixing_menu);
        imageViewList = new ArrayList<>();
        for (int i : imageArray) {
            ImageView imageView;
            imageView = (ImageView) findViewById(i);
            imageViewList.add(imageView);
        }
    }

    public void click03(View view) {
        switch (view.getId()) {
            case R.id.iv_a:
                //开启动画
                if (isStart) {
                    closeAnim();
                    isStart = false;
                } else {
                    startAnim();
                    isStart = true;
                }
                break;
        }
    }

    private void closeAnim() {
        for (int i = 1; i < imageViewList.size(); i++) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", i * 100, 0);
            animator.setDuration(700);
            animator.setStartDelay(i * 400);
            animator.setInterpolator(new BounceInterpolator());
            animator.start();
        }

    }

    private void startAnim() {
        for (int i = 1; i < imageViewList.size(); i++) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", 0, i * 100);
            animator.setDuration(700);
            animator.setStartDelay(i * 400);
            animator.setInterpolator(new AnticipateOvershootInterpolator());
            animator.start();
        }
    }
}
