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
import android.view.View;
import android.view.View.OnClickListener;
//import android.view.View.OnClickListener;
import android.widget.Button;
//import android.os.Parcelable;
//import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import java.nio.charset.Charset;


@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MainActivity extends Activity {
	NfcAdapter mNfcAdapter;
	TextView ip_text_view;
	TextView port_text_view;
	Button button1;
	Restaurant restaurant;
	
	
	@Override
	protected void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_main);
		ip_text_view = (TextView) findViewById(R.id.ipTextView);
		port_text_view = (TextView) findViewById(R.id.portTextView);
		button1 = (Button) findViewById(R.id.button1);
		
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null){
			Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
			finish ();
			return;
		}
		
		button1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				String ip = (String)ip_text_view.getText();
				int port = Integer.parseInt((String) port_text_view.getText()); 
				restaurant = new Restaurant("nfc_restaurant", ip, port);
				NdefMessage msg = createNdefMessage();
				mNfcAdapter.setNdefPushMessage(msg, MainActivity.this);
			}});
		
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
			    "text/plain".getBytes(Charset.forName("US-ASCII")),
			    new byte[0], 
			    (payload).getBytes(Charset.forName("US-ASCII")));
		return mimeRecord;
	}
	
	public NdefMessage createNdefMessage( ){
		NdefRecord[] recordList = new NdefRecord[1];
		NdefRecord record_0 = getNdefRecord("localhost");
		NdefRecord record_1 = getNdefRecord("8080");
		recordList[0] = record_0;
		recordList[1] = record_1;
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