package com.yzs.zx.uav;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button btn_lacation, btn_photo, btn_takeoff, btn_link,
            btn_setting, btn_add, btn_sub;
    TextView tv_electric, tv_height, tv_speed, tv_distance;
    final int CAMERA_RESULT = 0;//声明一个常量，代表结果码
    ImageView imageView;//声明一个ImageView对象
    //private  MySurfaceView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
        //设置无标题
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //       WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(new MySurfaceView(this));
        setContentView(R.layout.main);
        //获取控件

        btn_lacation = (Button) findViewById(R.id.btn_lacation);
        btn_photo = (Button) findViewById(R.id.btn_photo);
        btn_takeoff = (Button) findViewById(R.id.btn_takeoff);
        btn_link = (Button) findViewById(R.id.btn_link);
        btn_setting = (Button) findViewById(R.id.btn_setting);
//        btn_add = (Button) findViewById(R.id.btn_add);
//        btn_sub = (Button) findViewById(R.id.btn_sub);
        imageView = (ImageView) findViewById(R.id.imageView);
        tv_electric = (TextView) findViewById(R.id.tv_electric);
        tv_height = (TextView) findViewById(R.id.tv_height);
        tv_speed = (TextView) findViewById(R.id.tv_speed);
        tv_distance = (TextView) findViewById(R.id.tv_distance);



        btn_takeoff.setOnLongClickListener(new OnLongClick());
        btn_link.setOnLongClickListener(new OnLongClick());


        this.registerReceiver(this.mBatteryReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));
    }

    /**
     * 用onActivityResult()接收传回的图像，当用户拍完照片，或者取消后，系统都会调用这个函数
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            Bundle extras = intent.getExtras();//从Intent中获取附加值
            Bitmap bitmap = (Bitmap) extras.get("data");//从附加值中获取返回的图像
            imageView.setImageBitmap(bitmap);//显示图像
        }
    }

    /**
     * 2 秒内按两次 back 键就退出程序
     */

    @Override
    public void onBackPressed() {
        long startTime = 0;
        long currentTime = System.currentTimeMillis();
        if ((currentTime - startTime) >= 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            startTime = currentTime;
        } else {
            finish();
        }
    }

    /**
     * 获取电池电量
     */

    private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            int level = arg1.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = arg1.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            int levelPercent = (int) (((float) level / scale) * 100);
            tv_electric.setText("电量：" + levelPercent + "%");
            if (level <= 10) {
                tv_electric.setTextColor(Color.RED);
            } else {
                tv_electric.setTextColor(Color.GREEN);
            }
        }
    };


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_lacation:
                Toast.makeText(MainActivity.this, "定位", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_photo:
                //实例化Intent对象,使用MediaStore的ACTION_IMAGE_CAPTURE常量调用系统相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //开启相机，传入上面的Intent对象
                startActivityForResult(intent, CAMERA_RESULT);
                Toast.makeText(MainActivity.this, "抓拍", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_takeoff:
                Toast.makeText(MainActivity.this, "起飞", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_link:
                //点击连接。启动线程，发送连接指令
                new Thread(new ConnThread()).start();
                // Toast.makeText(MainActivity.this, "连接", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_setting:
                // Toast.makeText(MainActivity.this,"设置",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(MainActivity.this, Setting.class);
                startActivity(intent1);
                break;
//                case R.id.btn_add:
//                    Toast.makeText(MainActivity.this, "加速", Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.btn_sub:
//                    Toast.makeText(MainActivity.this, "减速", Toast.LENGTH_SHORT).show();
//                    break;
        }
    }
};

/**
 * 长按连接 退出应用
 */
class OnLongClick implements View.OnLongClickListener {

    @Override
    public boolean onLongClick(View view) {

        return true;
    }
}
