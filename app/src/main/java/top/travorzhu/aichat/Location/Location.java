package top.travorzhu.aichat.Location;

import android.app.Activity;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;

import top.travorzhu.aichat.Ai.PostAi;

public class Location {
    //声明mLocationOption对象
    private AMapLocationClient mLocationClient;

    public Location(Activity activity) {
        mLocationClient = new AMapLocationClient(activity);
    }

    public void FindLocation() {

        mLocationClient.startLocation();
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                //异步获取定位结果
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        String province = amapLocation.getProvince();//省信息
                        String city = amapLocation.getCity();//城市信息
                        String street = amapLocation.getStreet();//街道信息
                        if (PostAi.aiPostBean.getPerception().getSelfInfo().getLocation() == null)
                            PostAi.aiPostBean.newLocation(city, province, street);
                        else
                            PostAi.aiPostBean.setLocation(city, province, street);
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }

        });

    }
}
