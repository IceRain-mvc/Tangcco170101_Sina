package tangcco.jcplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class JcListActivity extends AppCompatActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jc_list);
        mListView = (ListView) findViewById(R.id.myListView);
        mListView.setAdapter(new VideoListAdapter(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //释放资源
        JCVideoPlayer.releaseAllVideos();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
