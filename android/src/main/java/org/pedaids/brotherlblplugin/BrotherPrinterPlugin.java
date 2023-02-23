package org.pedaids.brotherlblplugin;

import android.Manifest;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.graphics.pdf.PdfRenderer;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;


import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.brotherdll.BrotherActivity;
import com.example.brotherdll.BrotherWifiActivity;
import com.example.brotherdll.BrotherUSBActivity;

import androidx.core.app.ActivityCompat;


/*@NativePlugin(
        requestCodes={BrotherPrinterPlugin.REQUEST_CONTACTS}
)*/
@CapacitorPlugin(name = "BrotherPrinter")

public class BrotherPrinterPlugin extends Plugin {

    BrotherWifiActivity BrotherDll_wifi = new BrotherWifiActivity();
    BrotherActivity BrotherDll_BT = new BrotherActivity();
    BrotherUSBActivity BrotherDll_USB = new BrotherUSBActivity();

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private static UsbManager mUsbManager;
    private static PendingIntent mPermissionIntent;
    private static boolean hasPermissionToCommunicate = false;
    private static UsbDevice device;
    protected static final int REQUEST_CONTACTS = 12345; // Unique request code

    private BrotherPrinter implementation = new BrotherPrinter();
    ArrayList<JSONObject> contacts = new ArrayList<>();


    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            hasPermissionToCommunicate = true;
                        }
                    }
                }
            }
        }
    };


    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod()
    public void getContacts(PluginCall call) {

        String value = call.getString("filter");
        ArrayList<Map> contactList = new ArrayList<>();

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            call.reject("no bluetooth");
            return;
        }
        String macAddress = mBluetoothAdapter.getAddress();
        Label_BT(macAddress, call);

        //mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        //mPermissionIntent = PendingIntent.getBroadcast(this.getContext(), 0, new Intent(ACTION_USB_PERMISSION), 0);
        //IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        //registerReceiver(mUsbReceiver, filter);


        /*
        ContentResolver cr = this.getContext().getContentResolver();


        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                Map<String,String> map =  new HashMap<String, String>();

                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));

                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                map.put("firstName", name);
                map.put("lastName", "");

                String contactNumber = "";

                if (cur.getInt(cur.getColumnIndex( ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    pCur.moveToFirst();
                    contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.i("phoneNUmber", "The phone number is "+ contactNumber);
                }
                map.put("telephone", contactNumber);
                contactList.add(map);
            }
        }
        if (cur != null) {
            cur.close();
        }

        JSONArray jsonArray = new JSONArray(contactList);
        JSObject ret = new JSObject();
        ret.put("results", jsonArray);
        call.resolve(ret);
        // Filter based on the value if want
        // saveCall(call);
        //pluginRequestPermission(Manifest.permission.READ_CONTACTS, REQUEST_CONTACTS);
        */

    }

    void Label_BT(String bt_mac, PluginCall call)
    {
        try {

            BrotherDll_BT.openport(bt_mac);
            //BrotherDll_BT.downloadpcx(this, Uri.fromFile(file_PCX), PCXName);
            //BrotherDll_BT.downloadbmp(this, Uri.fromFile(file_BMP), BMPName);

            BrotherDll_BT.setup(50,50,2,15,0,0,0);
            BrotherDll_BT.clearbuffer();
            BrotherDll_BT.nobackfeed();
            BrotherDll_BT.barcode(10,10,"128",40,1,0,2,2,"1234567");
            //BrotherDll_BT.windowsfont(10, 100, 24, path_TTF, "windowsfont");
            BrotherDll_BT.printerfont(10,150,"0",0,10,10,"printerfont");
            //BrotherDll_BT.sendcommand("PUTPCX 145,15,\"UL.PCX\"\r\n");
            //BrotherDll_BT.sendcommand("PUTBMP 10,190,\"LOGO.BMP\"\r\n");

            BrotherDll_BT.printlabel(1, 1);
            BrotherDll_BT.sendcommand("FEED 200\r\n");
            //BrotherDll_BT.sendfile(this,Uri.fromFile(file_TXT));
            Log.v("tsd_dll_test",BrotherDll_BT.printerstatus(1000));
            BrotherDll_BT.formfeed();
            BrotherDll_BT.closeport(2000);
            ArrayList<Map> contactList = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(contactList);
            JSObject ret = new JSObject();
            ret.put("results", jsonArray);
            call.resolve(ret);
        }
        catch (Exception ex)
        {

        }
    }

       



}
