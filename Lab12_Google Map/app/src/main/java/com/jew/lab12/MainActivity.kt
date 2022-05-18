package com.jew.lab12

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val REQUEST_PERMISSIONS = 1
    //權限要求結果
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (grantResults.isEmpty()) return
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                for (result in grantResults)
                //若使用者拒絕給予權限則關閉APP
                    if (result != PackageManager.PERMISSION_GRANTED)
                        finish() //若使用者拒絕給予權限則關閉APP
                    else{
                        //連接MapFragment物件
                        val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                        map.getMapAsync(this)
                    }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //檢查使用者是否已授權定位權限，向使用者要求權限
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS)
        else{
            //Step1：連接MapFragment元件
            val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            //Step2：執行map的非同步方法
            map.getMapAsync(this)
        }
    }

    //Step3：取得GoogleMap
    override fun onMapReady(map: GoogleMap){
        //檢查使用者是否已授權定位權限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        map.isMyLocationEnabled = true //顯示目前位置與定位按鈕
        //1)建立MarkerOptions物件
        val marker = MarkerOptions()
        //2)設定Marker座標
        marker.position(LatLng(25.033611, 121.565000))
        //3)設定Marker標題
        marker.title("台北101")
        //4)設定是否可拖曳
        marker.draggable(true)
        //5)將Marker加入Map並顯示
        map.addMarker(marker)

        marker.position(LatLng(25.047924, 121.517081))
        marker.title("台北車站")
        marker.draggable(true)
        map.addMarker(marker)
        //I.建立PolylineOptions物件
        val polylineOpt = PolylineOptions()
        //II.加入座標到PolylineOptions
        polylineOpt.add(LatLng(25.033611, 121.565000))
        polylineOpt.add(LatLng(25.032728, 121.565137))
        polylineOpt.add(LatLng(25.047924, 121.517081))
        //III.設定PolylineOptions顏色
        polylineOpt.color(Color.BLUE)
        //IV.將PolylineOptions加入Map並顯示
        val polyline = map.addPolyline(polylineOpt)
        //V.設定Polyline寬度
        polyline.width = 10f
        //移動鏡頭（畫面）到指定座標與深度
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.033611, 121.565000), 15f))//(座標),深度
    }
}