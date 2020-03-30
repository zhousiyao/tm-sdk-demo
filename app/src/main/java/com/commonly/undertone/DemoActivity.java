package com.commonly.undertone;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class DemoActivity extends AppCompatActivity {


    private static final String TAG = "DemoActivity===";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);

//        getWifiInfo();
//        Log.d(TAG, "" + isRoot());
//        getWifiList();

        // 获取基站信息
//        getCellInfo();

        //信号强度
//        getMobileDbm();

        //cpu信息
//        final int cpuMaxFreqKHz = getCPUMaxFreqKHz();
//        final int numberOfCPUCores = getNumberOfCPUCores();
//        Log.d(TAG,numberOfCPUCores+"个");

    }


//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    public static String decrypt(String data, String key) {
//        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
//            // 对 data 解码
//            byte[] dataBytes = FoxBaseEncodeUtils.base64Decode(data);
//            // 对密钥解密
//            byte[] keyBytes = FoxBaseEncodeUtils.base64Decode(key.getBytes("UTF-8"));
//            // 取得私钥
//            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
//
//            // 对数据解密
//            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//            cipher.init(Cipher.DECRYPT_MODE, privateKey);
//            int MAX_DECRYPT_BLOCK = 128;
//            int inputLen = dataBytes.length;
//            int offSet = 0;
//            byte[] cache;
//            int i = 0;
//            // 对数据分段解密
//            while (inputLen - offSet > 0) {
//                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
//                    cache = cipher.doFinal(dataBytes, offSet, MAX_DECRYPT_BLOCK);
//                } else {
//                    cache = cipher.doFinal(dataBytes, offSet, inputLen - offSet);
//                }
//                out.write(cache, 0, cache.length);
//                i++;
//                offSet = i * MAX_DECRYPT_BLOCK;
//            }
//            return new String(out.toByteArray());
//        } catch (Exception e) {
//            return "";
//        }
//    }

    private void getCellInfo1() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        CellLocation cel = tel.getCellLocation();
        if (tel.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
            CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cel;
            int cid = cdmaCellLocation.getBaseStationId();
            int lac = cdmaCellLocation.getNetworkId();
        } else {//如果是移动和联通的话  移动联通一致
            GsmCellLocation gsmCellLocation = (GsmCellLocation) cel;
            int cid = gsmCellLocation.getCid();
            int lac = gsmCellLocation.getLac();
        }
        Log.d(TAG, "getCellInfo1: ");
    }

    private void getCellInfo() {
        // 获取邻区基站信息
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            // 返回值MCC + MNC
            String operator = mTelephonyManager.getNetworkOperator();
            int mcc = Integer.parseInt(operator.substring(0, 3));
            int mnc = Integer.parseInt(operator.substring(3));
            // 中国移动和中国联通获取LAC、CID的方式
            int lac;
            int cellId;
            if (mTelephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
                CdmaCellLocation location1 = (CdmaCellLocation) mTelephonyManager.getCellLocation();
                lac = location1.getNetworkId();
                cellId = location1.getBaseStationId();
            } else {
                GsmCellLocation location = (GsmCellLocation) mTelephonyManager.getCellLocation();
                lac = location.getLac();
                cellId = location.getCid();
            }
                //MCC，Mobile Country Code，移动国家代码（中国的为460）；
                //MNC，Mobile Network Code，移动网络号码（中国移动为0，中国联通为1，中国电信为2）； 
                //LAC，Location Area Code，位置区域码；
                //CID，Cell Identity，基站编号；
                //BSSS，Base station signal strength，基站信号强度。
            Log.i(TAG, " MCC = " + mcc + "\t MNC = " + mnc + "\t LAC = " + lac + "\t CID = " + cellId);
//            List<NeighboringCellInfo> infos = mTelephonyManager.getNeighboringCellInfo();
//            StringBuffer sb = new StringBuffer("总数 : " + infos.size() + "\n");
//            for (NeighboringCellInfo info1 : infos) { // 依据邻区总数进行循环
//                sb.append(" LAC : " + info1.getLac()); // 取出当前邻区的LAC
//                sb.append(" CID : " + info1.getCid()); // 取出当前邻区的CID
//                sb.append(" BSSS : " + (-113 + 2 * info1.getRssi()) + "\n"); // 获取邻区基站信号强度
//            }
//            Log.i(TAG, " 获取邻区基站信息:" + sb.toString());
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
    }


    public static final int DEVICEINFO_UNKNOWN = -1;
    public static int getCPUMaxFreqKHz() {
        int maxFreq = DEVICEINFO_UNKNOWN;
        try {
            for (int i = 0; i < getNumberOfCPUCores(); i++) {
                String filename =
                        "/sys/devices/system/cpu/cpu" + i + "/cpufreq/cpuinfo_max_freq";
                File cpuInfoMaxFreqFile = new File(filename);
                if (cpuInfoMaxFreqFile.exists()) {
                    byte[] buffer = new byte[128];
                    FileInputStream stream = new FileInputStream(cpuInfoMaxFreqFile);
                    try {
                        stream.read(buffer);
                        int endIndex = 0;
                        //Trim the first number out of the byte buffer.
                        while (buffer[endIndex] >= '0' && buffer[endIndex] <= '9'
                                && endIndex < buffer.length) endIndex++;
                        String str = new String(buffer, 0, endIndex);
                        Integer freqBound = Integer.parseInt(str);
                        if (freqBound > maxFreq) maxFreq = freqBound;
                    } catch (NumberFormatException e) {
                        //Fall through and use /proc/cpuinfo.
                    } finally {
                        stream.close();
                    }
                }
            }
            if (maxFreq == DEVICEINFO_UNKNOWN) {
                FileInputStream stream = new FileInputStream("/proc/cpuinfo");
                try {
                    int freqBound = parseFileForValue("cpu MHz", stream);
                    freqBound *= 1000; //MHz -> kHz
                    if (freqBound > maxFreq) maxFreq = freqBound;
                } finally {
                    stream.close();
                }
            }
        } catch (IOException e) {
            maxFreq = DEVICEINFO_UNKNOWN; //Fall through and return unknown.
        }
        return maxFreq;
    }

    /**
     * Helper method for reading values from system files, using a minimised buffer.
     *
     * @param textToMatch - Text in the system files to read for.
     * @param stream      - FileInputStream of the system file being read from.
     * @return A numerical value following textToMatch in specified the system file.
     * -1 in the event of a failure.
     */
    private static int parseFileForValue(String textToMatch, FileInputStream stream) {
        byte[] buffer = new byte[1024];
        try {
            int length = stream.read(buffer);
            for (int i = 0; i < length; i++) {
                if (buffer[i] == '\n' || i == 0) {
                    if (buffer[i] == '\n') i++;
                    for (int j = i; j < length; j++) {
                        int textIndex = j - i;
                        //Text doesn't match query at some point.
                        if (buffer[j] != textToMatch.charAt(textIndex)) {
                            break;
                        }
                        //Text matches query here.
                        if (textIndex == textToMatch.length() - 1) {
                            return extractValue(buffer, j);
                        }
                    }
                }
            }
        } catch (IOException e) {
            //Ignore any exceptions and fall through to return unknown value.
        } catch (NumberFormatException e) {
        }
        return DEVICEINFO_UNKNOWN;
    }

    private static int extractValue(byte[] buffer, int index) {
        while (index < buffer.length && buffer[index] != '\n') {
            if (buffer[index] >= '0' && buffer[index] <= '9') {
                int start = index;
                index++;
                while (index < buffer.length && buffer[index] >= '0' && buffer[index] <= '9') {
                    index++;
                }
                String str = new String(buffer, 0, start, index - start);
                return Integer.parseInt(str);
            }
            index++;
        }
        return DEVICEINFO_UNKNOWN;
    }



    /**
     * Reads the number of CPU cores from {@code /sys/devices/system/cpu/}.
     *
     * @return Number of CPU cores in the phone, or DEVICEINFO_UKNOWN = -1 in the event of an error.
     */
    public static int getNumberOfCPUCores() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            // Gingerbread doesn't support giving a single application access to both cores, but a
            // handful of devices (Atrix 4G and Droid X2 for example) were released with a dual-core
            // chipset and Gingerbread; that can let an app in the background run without impacting
            // the foreground application. But for our purposes, it makes them single core.
            return 1;
        }
        int cores;
        try {
            cores = new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER).length;
        } catch (SecurityException e) {
            cores = DEVICEINFO_UNKNOWN;
        } catch (NullPointerException e) {
            cores = DEVICEINFO_UNKNOWN;
        }
        return cores;
    }

    private static final FileFilter CPU_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            String path = pathname.getName();
            //regex is slow, so checking char by char.
            if (path.startsWith("cpu")) {
                for (int i = 3; i < path.length(); i++) {
                    if (path.charAt(i) < '0' || path.charAt(i) > '9') {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    };




    /**
     * 获取信息强度
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void getMobileDbm() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            int dbm = -1;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            List<CellInfo> cellInfoList = tm.getAllCellInfo();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (null != cellInfoList) {
                    for (CellInfo cellInfo : cellInfoList) {
                        if (cellInfo instanceof CellInfoGsm) {
                            CellSignalStrengthGsm cellSignalStrengthGsm = ((CellInfoGsm) cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthGsm.getDbm();
                            Log.e(TAG, "cellSignalStrengthGsm" + cellSignalStrengthGsm.toString());
                        } else if (cellInfo instanceof CellInfoCdma) {
                            CellSignalStrengthCdma cellSignalStrengthCdma = ((CellInfoCdma) cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthCdma.getDbm();
                            Log.e(TAG, "cellSignalStrengthCdma" + cellSignalStrengthCdma.toString());
                        } else if (cellInfo instanceof CellInfoWcdma) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                CellSignalStrengthWcdma cellSignalStrengthWcdma = ((CellInfoWcdma) cellInfo).getCellSignalStrength();
                                dbm = cellSignalStrengthWcdma.getDbm();
                                Log.e(TAG, "cellSignalStrengthWcdma" + cellSignalStrengthWcdma.toString());
                            }
                        } else if (cellInfo instanceof CellInfoLte) {
                            CellSignalStrengthLte cellSignalStrengthLte = ((CellInfoLte) cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthLte.getDbm();
                            Log.d(TAG, "cellSignalStrengthLte.getAsuLevel()\t" + cellSignalStrengthLte.getAsuLevel());
                            Log.d(TAG, "cellSignalStrengthLte.getDbm()\t " + cellSignalStrengthLte.getDbm());
                            Log.d(TAG, "cellSignalStrengthLte.getLevel()\t " + cellSignalStrengthLte.getLevel());
                            Log.d(TAG, "cellSignalStrengthLte.getTimingAdvance()\t " + cellSignalStrengthLte.getTimingAdvance());
                        }
                    }
                }
            }
        }
    }


    private void getWifiInfo() {
        try {
            WifiManager wifiMgr = (WifiManager) DemoActivity.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            int wifiState = wifiMgr.getWifiState();
            WifiInfo info = wifiMgr.getConnectionInfo();
            String wifiId = info != null ? info.getSSID() : null;
            Log.d(TAG, "getWifiInfo: " + info.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<ScanResult> getWifiList() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        List<ScanResult> scanWifiList = wifiManager.getScanResults();
        List<ScanResult> wifiList = new ArrayList<>();
        if (scanWifiList != null && scanWifiList.size() > 0) {
            HashMap<String, Integer> signalStrength = new HashMap<String, Integer>();
            for (int i = 0; i < scanWifiList.size(); i++) {
                ScanResult scanResult = scanWifiList.get(i);
                if (!scanResult.SSID.isEmpty()) {
                    String key = scanResult.SSID + "---" + scanResult.capabilities;
                    if (!signalStrength.containsKey(key)) {
                        signalStrength.put(key, i);
                        Log.d(TAG, "getWifiList: 搜索到wifi====" + key);
                        wifiList.add(scanResult);
                    }
                }
            }
        } else {

        }
        return wifiList;
    }

    /**
     * Return whether device is rooted.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/",
                "/system/sbin/", "/usr/bin/", "/vendor/bin/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取基站信息
     * <p>
     * 用到的权限：
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
     */
    public SCell getCellInfo(Context ctx) {
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        SCell cell = new SCell();
        TelephonyManager tm = null;
        try {
            tm = (TelephonyManager) ctx
                    .getSystemService(Context.TELEPHONY_SERVICE);
        } catch (Exception e) {
            return null;
        }

        String IMSI = tm.getSubscriberId();

        if (IMSI != null) {
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                cell.NETWORK_TYPE = "CHINA MOBILE";

                GsmCellLocation location = (GsmCellLocation) tm
                        .getCellLocation();
                if (location == null) {
                    cell = null;
                } else {
                    String operator = tm.getNetworkOperator();
                    if (operator.length() > 4) {
                        int mcc = Integer.parseInt(operator.substring(0, 3));
                        int mnc = Integer.parseInt(operator.substring(3));
                        int cid = location.getCid();
                        int lac = location.getLac();

                        cell.MCC = mcc;
                        cell.MNC = mnc;
                        cell.LAC = lac;
                        cell.CID = cid;
                    } else {
                        cell = null;
                    }
                }
            } else if (IMSI.startsWith("46001")) {
                cell.NETWORK_TYPE = "CHINA UNICOM";

                GsmCellLocation location = (GsmCellLocation) tm
                        .getCellLocation();
                if (location == null) {
                    cell = null;
                } else {
                    String operator = tm.getNetworkOperator();
                    if (operator.length() > 4) {
                        int mcc = Integer.parseInt(operator.substring(0, 3));
                        int mnc = Integer.parseInt(operator.substring(3));
                        int cid = location.getCid();
                        int lac = location.getLac();

                        cell.MCC = mcc;
                        cell.MNC = mnc;
                        cell.LAC = lac;
                        cell.CID = cid;
                    } else {
                        cell = null;
                    }
                }
            } else if (IMSI.startsWith("46003")) {
                cell.NETWORK_TYPE = "CHINA TELECOM";

                CdmaCellLocation location = (CdmaCellLocation) tm
                        .getCellLocation();
                if (location == null) {
                    cell = null;
                } else {
                    String operator = tm.getNetworkOperator();
                    if (operator.length() > 4) {
                        int mcc = Integer.parseInt(operator.substring(0, 3));
                        int mnc = Integer.parseInt(operator.substring(3));
                        int cid = location.getBaseStationId();
                        int lac = location.getNetworkId();

                        cell.MCC = mcc;
                        cell.MNC = mnc;
                        cell.LAC = lac;
                        cell.CID = cid;
                    } else {
                        cell = null;
                    }
                }
            } else {
                // cell.NETWORK_TYPE = "UNDENTIFIED";
                cell = null;
            }
        } else {
            cell = null;
        }
        return cell;
    }

    /**
     * 基站信息
     */
    class SCell {

        public String NETWORK_TYPE;

        public int MCC;

        public int MNC;

        public int LAC;

        public int CID;

        public JSONObject toJSON() throws JSONException {
            JSONObject json = new JSONObject();
            json.put("network_type", NETWORK_TYPE);
            json.put("mcc", MCC);
            json.put("MNC", MNC);
            json.put("LAC", LAC);
            json.put("CID", CID);
            return json;
        }
    }


}
