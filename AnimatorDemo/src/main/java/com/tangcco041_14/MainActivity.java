package com.tangcco041_14;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView img_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img_iv = (ImageView) findViewById(R.id.imageView);
        img_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "点击了小图片", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void click(View view) {
//        TranslateAnimation translateAnimation = new TranslateAnimation(0, 200, 0, 200);
//        //设置时间
//        translateAnimation.setDuration(2000);
//        //设置是否停留在动画结束的位置
//        translateAnimation.setFillAfter(true);
//        //开始动画
//        img_iv.startAnimation(translateAnimation);
        //3.0
//        ObjectAnimator  ;70% 属性
//        ValueAnimator   ;30% 值
        //链表 ofFloat-->精度更高
        //异步形式 -->线程-->
//        ObjectAnimator.ofFloat(img_iv, "translationX", 0, 200).setDuration(2000).start();
//        ObjectAnimator.ofFloat(img_iv, "translationY", 0, 200).setDuration(2000).start();
//        ObjectAnimator.ofFloat(img_iv, "alpha", 0, 1).setDuration(2000).start();
//        ObjectAnimator.ofFloat(img_iv, "rotation", 0, 370).setDuration(4000).start();
//        ObjectAnimator.ofFloat(img_iv, "scaleX", 0, 3).setDuration(4000).start();
//        ObjectAnimator.ofFloat(img_iv, "scaleY", 0, 2).setDuration(4000).start();

        //更好的方式
        //更好的方式
//        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("translationX", 0, 200);
//        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("translationY", 0, 200);
//        PropertyValuesHolder p3 = PropertyValuesHolder.ofFloat("alpha", 0, 200);
//        PropertyValuesHolder p4 = PropertyValuesHolder.ofFloat("scaleX", 0, 2);
//        PropertyValuesHolder p5 = PropertyValuesHolder.ofFloat("rotation", 0, 720);
//
//        ObjectAnimator.ofPropertyValuesHolder(img_iv, p1, p2, p3, p4, p5).setDuration(2000).start();

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(img_iv, "translationX", 0, 200);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(img_iv, "translationY", 0, 200);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(img_iv, "alpha", 0, 1);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(img_iv, "rotation", 0, 370);
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(img_iv, "scaleX", 0, 3);
        ObjectAnimator animator6 = ObjectAnimator.ofFloat(img_iv, "scaleY", 0, 2);
        animator4.setInterpolator(new CycleInterpolator(1));
        AnimatorSet set = new AnimatorSet();
        set.setDuration(3000);
        set.playTogether(animator1,animator2,animator3,animator4,animator5,animator6);
//        set.setInterpolator(new CycleInterpolator(2));
        set.start();
    }

    public void click01(final View view) {
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(view, "rotation", 0, 720);
        animator4.setDuration(2000);
        animator4.start();
        animator4.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((Button)view).setText("动画结束了,请欣赏下一个动画");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
