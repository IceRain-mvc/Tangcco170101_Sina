package tangcco.jcplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nostra13.universalimageloader.core.ImageLoader;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends AppCompatActivity {
    private ImageLoader mImageLoader;
    private JCVideoPlayer mJcVideoPlayer;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mJcVideoPlayer = (JCVideoPlayer) findViewById(R.id.myJcPlayer);
        mImageLoader = ImageLoader.getInstance();

        mJcVideoPlayer.setUp("http://flv2.bn.netease.com/videolib3/1505/20/SPgFQ4591/SD/SPgFQ4591-mobile.mp4",
                "http://vimg2.ws.126.net/image/snapshot/2015/5/J/P/VAP4I10JP.jpg",
                "恶搞视频");
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JcListActivity.class);
                startActivity(intent);
            }
        });
    }
}
