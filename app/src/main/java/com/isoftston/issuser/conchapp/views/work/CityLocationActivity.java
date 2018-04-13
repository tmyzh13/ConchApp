package com.isoftston.issuser.conchapp.views.work;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.isoftston.issuser.conchapp.R;
import com.isoftston.issuser.conchapp.model.event.MyEvent;
import com.isoftston.issuser.conchapp.views.message.MessageFragment;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import butterknife.Bind;

import static android.widget.AdapterView.*;

public class CityLocationActivity extends BaseActivity {
    private static final String TAG = "CityLocationActivity";

    @Bind(R.id.sure_btn)
    Button sureBtn;
    @Bind(R.id.sp_province)
    Spinner spinnerProvince;
    @Bind(R.id.sp_city)
    Spinner spinnerCity;
    HashMap<String, String> provinceHash = new HashMap<String, String>();
    String[] provinceString = new String[34];

    HashMap<String, String> cityHash = new HashMap<String, String>();
    String[] cityString;

    String file;

    String cityNo = null;// 最重要的参数，选中的城市的cityNo

    private ArrayAdapter<String> proviceAdapter;
    private ArrayAdapter<String> cityAdapter;

    private String cityName = "";
    @Override
    protected int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_city_location;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
//        setFinishOnTouchOutside(true);
        file = readFile(); // 读取txt文件
        getProvinces(file); // 得到省的列表

//         设置spinner
        proviceAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, provinceString);
        proviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉风格
        spinnerProvince.setAdapter(proviceAdapter); // 将adapter 添加到spinner中
        spinnerProvince.setOnItemSelectedListener(new ProvinceSelectedListener(CityLocationActivity.this));// 添加监听
        spinnerProvince.setVisibility(View.VISIBLE);// 设置默认值
        sureBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MyEvent(cityName));
                finish();
            }
        });
    }

    /**
     * 读取文件
     *
     * @return
     */
    public String readFile() {
        InputStream myFile = null;
        myFile = getResources().openRawResource(R.raw.ub_city);
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(myFile, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String temp;
        String str = "";
        try {
            while ((temp = br.readLine()) != null) {
                str = str + temp;
            }
            br.close();
            myFile.close();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /*
     * 从json字符串中得到省的信息
     */
    public void getProvinces(String jsonData) {

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < 34; i++) {
                // 获取了34个省市区信息
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String guid = jsonObject.getString("guid");
                String cityName = jsonObject.getString("cityName");
//                 Log.i(TAG, i+guid+cityName);
                provinceHash.put(cityName, guid);
                provinceString[i] = cityName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 此方法用于查找一个省下的所有城市
    */
    public String[] getCitys(String guid, String jsonData) {

        // 初始化hashmap
        cityHash.clear();
        // 暂时存放城市的数组
        String[] cityBuffer = new String[21];
        int j = 0;

        // 解析数据
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonData);
            int length = jsonArray.length();
            int i = 33;
            int continuous = 0;// 这个变量用于判断是否连续几次没有找到，如果是，则该省信息全部找到了
            boolean isFind = false;

            while (i < length) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String fGuid = jsonObject.getString("fGuid");
                String cityName = jsonObject.getString("cityName");
                String cityNo = jsonObject.getString("cityNo");
                if (fGuid.equals(guid)) {
                    isFind = true;
                    cityHash.put(cityName, cityNo);
                    cityBuffer[j] = cityName;
                    j++;
                } else {
                    if (isFind == true) {
                        continuous += 1;

                        if (continuous > 5) {
                            Log.i(TAG, "城市数:" + j);
                            break;
                        }
                    }
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] citys = new String[j];
        for (int i = 0; i < j; i++) {
            citys[i] = cityBuffer[i];
        }
        return citys;
    }

    class ProvinceSelectedListener implements OnItemSelectedListener {
        Context context;

        // 省被选择的监听器
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            String provinceName = provinceString[arg2];
//            Toast.makeText(context, provinceName, Toast.LENGTH_LONG).show();
            String guid = provinceHash.get(provinceName);
            cityString = getCitys(guid, file);

            // 省被选中后，初始化城市Spinner
            cityAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, cityString);
            cityAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置下拉风格
            spinnerCity.setAdapter(cityAdapter); // 将adapter 添加到spinner中
            spinnerCity.setOnItemSelectedListener(new CitySelectedListener());// 添加监听
            spinnerCity.setVisibility(View.VISIBLE);// 设置默认
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }

        public ProvinceSelectedListener(Context context) {
            this.context = context;
        }

    }

    class CitySelectedListener implements OnItemSelectedListener {
        // 城市被点击的监听事件
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            Log.e("dp", "==arg1=" + arg1 + ",arg2=" + arg2 + ",arg3=" + arg3);
            cityName = cityString[arg2];
            if (cityName.equals("") || cityName == null) {
                cityName = cityString[0];
                cityNo = cityHash.get(cityName);
            } else {
                cityNo = cityHash.get(cityName);
                Log.i(TAG, "cityNo" + cityName);
            }
            Log.e(TAG,"---CITY:"+cityName);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
