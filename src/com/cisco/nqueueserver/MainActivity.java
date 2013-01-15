package com.cisco.nqueueserver;

//import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.annotation.TargetApi;
import android.app.Activity;
import android.nfc.NfcAdapter;
//import android.nfc.NfcAdapter.CreateNdefMessageCallback;
//import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
//import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
//import android.os.Parcelable;
//import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import java.nio.charset.Charset;


@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MainActivity extends Activity {
	NfcAdapter mNfcAdapter;
	TextView server_text_view;
	TextView restaurant_id_view;
	TextView restaurant_name_view;
	
	//TextView port_text_view;
	Button beam_button;
	Restaurant restaurant;
	
	//String restaurant_id = "1234";
	
	@Override
	protected void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_main);
		server_text_view = (TextView) findViewById(R.id.web_server);
		restaurant_id_view = (TextView) findViewById(R.id.restaurant_id);
		restaurant_name_view = (TextView) findViewById(R.id.restaurant_name);
		//port_text_view = (TextView) findViewById(R.id.portTextView);
		beam_button = (Button) findViewById(R.id.beam_button);
		
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		
		Log.i("wocao", "1");
		if (mNfcAdapter == null){
			Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
			Log.i("wocao", "2");
			finish ();
			return;
		}
		
		beam_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				String serverAddr = server_text_view.getText().toString();
				//int port = Integer.parseInt((String) port_text_view.getText());
				
				restaurant = new Restaurant("nfc_restaurant", serverAddr);
				
				NdefMessage msg = createNdefMessage();
				mNfcAdapter.setNdefPushMessage(msg, MainActivity.this);
			}});
		
		String serverAddr = server_text_view.getText().toString();
		
		restaurant = new Restaurant("nfc_restaurant", serverAddr);
		
		NdefMessage msg = createNdefMessage();
		mNfcAdapter.setNdefPushMessage(msg, MainActivity.this);
	
		//NdefMessage msg = createNdefMessage();
		//mNfcAdapter.setNdefPushMessage(msg, this);
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu){
		//getMenuInfater().inflate(R.menu.activity_main, menu);
		return true;
	}*/

	public NdefRecord getNdefRecord(String payload ){
		
		NdefRecord mimeRecord = new NdefRecord(
			    NdefRecord.TNF_MIME_MEDIA ,
			    "application/com.cisco.nqueue".getBytes(Charset.forName("US-ASCII")),
			    new byte[0], payload.getBytes(Charset.forName("US-ASCII")));
		return mimeRecord;
	}
	
	public NdefMessage createNdefMessage( ){
		NdefRecord[] recordList = new NdefRecord[3];
		NdefRecord record_0 = getNdefRecord(server_text_view.getText().toString());
		NdefRecord record_1 = getNdefRecord(restaurant_id_view.getText().toString());
		NdefRecord record_2 = getNdefRecord(restaurant_name_view.getText().toString());
		
		//NdefRecord record_2 = getNdefRc
		//NdefRecord record_AAR = NdefRecord.createApplicationRecord("com.cisco.nqueue");
		recordList[0] = record_0;
		recordList[1] = record_1;
		recordList[2] = record_2;
		//recordList[2] = record_AAR;
		NdefMessage msg = new NdefMessage(recordList);
		return msg;
	}
	
	/*@Override
	public NdefMessage createNdefMessage(NfcEvent arg0) {
		// TODO Auto-generated method stub
		String text = ("Beam me up, Android!\n\n" +
                "Beam Time: " + System.currentTimeMillis());
		NdefMessage msg = new NdefMessage(
				new NdefRecord[] { createMime(
				"application/vnd.com.example.android.beam", text.getBytes())
				}
				
				);
		return msg;
	}
	*/
	
}