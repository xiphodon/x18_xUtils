package com.example.x18_xutils;

import java.io.File;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	String path = "http://192.168.1.101:8080/PowerWord.exe";
	private TextView tv_failMsg;
	private ProgressBar pb;
	private TextView tv_progress;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_failMsg = (TextView) findViewById(R.id.tv_failMsg);
        pb = (ProgressBar) findViewById(R.id.pb);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
    }

    
    
    public String getFileName(String path) {
		int index = path.lastIndexOf("/");
		return path.substring(index + 1);
	}
    
    public void click(View v){
    	HttpUtils http = new HttpUtils();
    	//         //源文件地址                  //目标地址                              //是否支持断点续传    //自动获取文件名     //请求回调
    	http.download(path, "sdcard/" + getFileName(path), true, true, new RequestCallBack<File>() {
			//下载成功后调用
			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, arg0.result.getPath(), Toast.LENGTH_SHORT).show();
			}
			//下载失败后调用
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				tv_failMsg.setText(arg1);
			}
			//进度条
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				// TODO Auto-generated method stub
				super.onLoading(total, current, isUploading);
				//设置进度条
				pb.setMax((int)total);
				pb.setProgress((int)current);
				tv_progress.setText(current * 100 / total + "%");
			}
		});
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
