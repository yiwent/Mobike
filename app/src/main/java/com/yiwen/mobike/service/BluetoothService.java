//package com.yiwen.mobike.service;
//
//import android.app.Service;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothGatt;
//import android.bluetooth.BluetoothGattCallback;
//import android.bluetooth.BluetoothGattCharacteristic;
//import android.bluetooth.BluetoothGattDescriptor;
//import android.bluetooth.BluetoothGattService;
//import android.bluetooth.BluetoothManager;
//import android.content.Intent;
//import android.os.Binder;
//import android.os.Build;
//import android.os.Handler;
//import android.os.IBinder;
//import android.support.v4.content.LocalBroadcastManager;
//import android.text.TextUtils;
//
//import java.util.UUID;
//
///**
// * Created by yiwen (https://github.com/yiwent)
// * Date:2017/6/16
// * Time: 9:58
// */
//
//public class BluetoothService extends Service
//{
//    public static final long   a = 5000L;
//    public static final long   b = 10000L;
//    public static final int    d = 1;
//    public static final int    e = 2;
//    public static final int    f = 3;
//    public static final int    g = 4;
//    public static final int    h = 5;
//    public static final int    i = 6;
//    public static final int    j = 7;
//    public static final int    k = 8;
//    public static final String l = "com.mobike.ACTION_GATT_CONNECTED";
//    public static final String m = "com.mobike.ACTION_GATT_DISCONNECTED";
//    public static final String n = "com.mobike.ACTION_GATT_SERVICES_DISCOVERED";
//    public static final String o = "com.mobike.ACTION_DATA_AVAILABLE";
//    public static final String p = "com.mobike.ACTION_DATA_WRITE_DONE";
//    public static final String q = "com.mobike.EXTRA_DATA";
//    public static final String r = "com.mobike.DEVICE_DOES_NOT_SUPPORT_UART";
//    public static final UUID   s = UUID.fromString("A000FAA0-0047-005A-0052-6D6F62696B65");
//    public static final UUID   t = UUID.fromString("A000FEE0-0047-005A-0052-6D6F62696B65");
//    public static final UUID   u = UUID.fromString("A000FEE1-0047-005A-0052-6D6F62696B65");
//    public static final UUID   v = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
//    private BluetoothAdapter A;
//    private String           B;
//    private BluetoothGatt    C;
//   // private       a                     D = a.a;
//    private final BluetoothGattCallback E = new BluetoothGattCallback()
//    {
//        public void onCharacteristicChanged(BluetoothGatt paramBluetoothGatt, BluetoothGattCharacteristic paramBluetoothGattCharacteristic)
//        {
//           // if (RideManager.a().u())
//            {
//                BluetoothService.b(BluetoothService.this).postDelayed(b.a(this, paramBluetoothGattCharacteristic), 0L);
//                return;
//            }
//            BluetoothService.a(BluetoothService.this, "com.mobike.ACTION_DATA_AVAILABLE", paramBluetoothGattCharacteristic);
//        }
//
//        public void onCharacteristicRead(BluetoothGatt paramBluetoothGatt, BluetoothGattCharacteristic paramBluetoothGattCharacteristic, int paramInt)
//        {
//            if (paramInt == 0)
//                BluetoothService.a(BluetoothService.this, "com.mobike.ACTION_DATA_AVAILABLE", paramBluetoothGattCharacteristic);
//        }
//
//        public void onCharacteristicWrite(BluetoothGatt paramBluetoothGatt, BluetoothGattCharacteristic paramBluetoothGattCharacteristic, int paramInt)
//        {
//            if (paramInt == 0)
//            {
//                if (BluetoothService.this.c)
//                //    RideManager.a().b(false);
//                BluetoothService.a(BluetoothService.this, "com.mobike.ACTION_DATA_WRITE_DONE");
//            }
//        }
//
//        public void onConnectionStateChange(BluetoothGatt paramBluetoothGatt, int paramInt1, int paramInt2)
//        {
//            if (paramInt2 == 2)
//            {
//                BluetoothService.a(BluetoothService.this, BluetoothService.a.c);
//                BluetoothService.a(BluetoothService.this, "com.mobike.ACTION_GATT_CONNECTED");
//                BluetoothService.a(BluetoothService.this).discoverServices();
//            }
//            do
//                return;
//            while (paramInt2 != 0);
//            BluetoothService.a(BluetoothService.this, BluetoothService.a.a);
//            //RideManager.a().C();
//            BluetoothService.a(BluetoothService.this, "com.mobike.ACTION_GATT_DISCONNECTED");
//        }
//
//        public void onDescriptorWrite(BluetoothGatt paramBluetoothGatt, BluetoothGattDescriptor paramBluetoothGattDescriptor, int paramInt)
//        {
//            if (paramInt == 0)
//                BluetoothService.a(BluetoothService.this, "com.mobike.ACTION_GATT_SERVICES_DISCOVERED");
//        }
//
//        public void onServicesDiscovered(BluetoothGatt paramBluetoothGatt, int paramInt)
//        {
//            if (paramInt == 0)
//                BluetoothService.this.d();
//        }
//    };
//    public        boolean               c = false;
//    private final String                TAG = BluetoothService.class.getSimpleName();
//    private final Handler               mHandler = new Handler();
//    private final IBinder               y = new MyBinder();
//    private BluetoothManager z;
//
//    private void a(String paramString, BluetoothGattCharacteristic paramBluetoothGattCharacteristic)
//    {
//        Intent localIntent = new Intent(paramString);
//        if (u.equals(paramBluetoothGattCharacteristic.getUuid()))
//            localIntent.putExtra("com.mobike.EXTRA_DATA", paramBluetoothGattCharacteristic.getValue());
//        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
//    }
//
//    private void b(String paramString)
//    {
//        Intent localIntent = new Intent(paramString);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
//    }
//
//    public void a(boolean paramBoolean, BluetoothAdapter.LeScanCallback paramLeScanCallback)
//    {
//        if (this.A != null)
//        {
//            if (paramBoolean)
//            {
//                this.mHandler.postDelayed(a.a(this, paramLeScanCallback), 5000L);
//                this.A.startLeScan(paramLeScanCallback);
//            }
//        }
//        else
//            return;
//        this.A.stopLeScan(paramLeScanCallback);
//    }
//
//    public boolean a()
//    {
//        if (this.z == null)
//        {
//            this.z = ((BluetoothManager)getSystemService("bluetooth"));
//            if (this.z == null)
//                return false;
//        }
//        this.A = this.z.getAdapter();
//        return true;
//    }
//
//    public boolean a(String paramString)
//    {
//        if ((this.A == null) || (TextUtils.isEmpty(paramString)))
//            return false;
//        if ((this.B != null) && (paramString.equals(this.B)) && (this.C != null))
//        {
//            if (this.C.connect())
//            {
//                this.D = a.b;
//                return true;
//            }
//            return false;
//        }
//        BluetoothDevice localBluetoothDevice = this.A.getRemoteDevice(paramString);
//        if (localBluetoothDevice == null)
//        {
//           // MobclickAgent.c(this, "01001");
//            return false;
//        }
//        this.C = localBluetoothDevice.connectGatt(this, false, this.E);
//        this.B = paramString;
//        this.D = a.b;
//        return true;
//    }
//
//    public boolean a(byte[] paramArrayOfByte)
//    {
//        BluetoothGattService localBluetoothGattService = this.C.getService(s);
//        if (localBluetoothGattService == null)
//        {
//            b("com.mobike.DEVICE_DOES_NOT_SUPPORT_UART");
//            return false;
//        }
//        BluetoothGattCharacteristic localBluetoothGattCharacteristic = localBluetoothGattService.getCharacteristic(t);
//        if (localBluetoothGattCharacteristic == null)
//        {
//            b("com.mobike.DEVICE_DOES_NOT_SUPPORT_UART");
//            return false;
//        }
//        localBluetoothGattCharacteristic.setValue(paramArrayOfByte);
//        return this.C.writeCharacteristic(localBluetoothGattCharacteristic);
//    }
//
//    public void b()
//    {
//        if ((this.A == null) || (this.C == null))
//            return;
//        this.C.disconnect();
//    }
//
//    public void c()
//    {
//        if ((Build.VERSION.SDK_INT >= 18) && (this.C != null))
//        {
//            this.B = null;
//            this.C.close();
//            this.C = null;
//        }
//    }
//
//    public void d()
//    {
//        BluetoothGattService localBluetoothGattService = this.C.getService(s);
//        if (localBluetoothGattService == null)
//        {
//            b("com.mobike.DEVICE_DOES_NOT_SUPPORT_UART");
//            return;
//        }
//        BluetoothGattCharacteristic localBluetoothGattCharacteristic = localBluetoothGattService.getCharacteristic(u);
//        if (localBluetoothGattCharacteristic == null)
//        {
//            b("com.mobike.DEVICE_DOES_NOT_SUPPORT_UART");
//            return;
//        }
//        this.C.setCharacteristicNotification(localBluetoothGattCharacteristic, true);
//        BluetoothGattDescriptor localBluetoothGattDescriptor = localBluetoothGattCharacteristic.getDescriptor(v);
//        localBluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//        this.C.writeDescriptor(localBluetoothGattDescriptor);
//    }
//
//    @Override
//    public IBinder onBind(Intent paramIntent)
//    {
//        return this.y;
//    }
//
//    public boolean onUnbind(Intent paramIntent)
//    {
//        return super.onUnbind(paramIntent);
//    }
//
//    private static enum a
//    {
//        a,b,c;
////        static
////        {
////            a[] arrayOfa = new a[3];
////            arrayOfa[0] = a;
////            arrayOfa[1] = b;
////            arrayOfa[2] = c;
////            d = arrayOfa;
////        }
//    }
//
//    public class MyBinder extends Binder//b
//    {
//        public MyBinder()
//        {
//        }
//
//        public BluetoothService getBluetoothService()//a
//        {
//            return BluetoothService.this;
//        }
//    }
//}