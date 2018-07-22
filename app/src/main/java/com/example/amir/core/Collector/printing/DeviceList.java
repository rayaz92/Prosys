package com.example.amir.core.Collector.printing;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amir.core.R;

public class DeviceList extends Activity implements
		View.OnClickListener {
	// Debugging
	private static final String TAG = "DeviceListActivity";

	// Return Intent extra
	public static String EXTRA_DEVICE_ADDRESS = "device_address";

	// Member fields
	private BluetoothAdapter mBtAdapter;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	private ListView pairedListView;
	private Button scanButton;

	// @SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.colldevice_list);

		scanButton = (Button) findViewById(R.id.button_scan1);
		scanButton.setOnClickListener(this);
		setResult(Activity.RESULT_CANCELED);
		// Initialize array adapters. One for already paired devices and
		// one for newly discovered devices
		mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.colldevice_name);

		// mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,
		// R.layout.device_name);

		// Find and set up the ListView for paired devices
		pairedListView = (ListView) findViewById(R.id.paired_devices12);
		pairedListView.setAdapter(mPairedDevicesArrayAdapter);
		pairedListView.setOnItemClickListener(mDeviceClickListener);

		// Get the local Bluetooth adapter
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();

		// Get a set of currently paired devices
		Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

		// If there are paired devices, add each one to the ArrayAdapter
		if (pairedDevices.size() > 0) {
			// findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
			for (BluetoothDevice device : pairedDevices) {

				Object x = device.getBluetoothClass();

				mPairedDevicesArrayAdapter.add(device.getName() + " (Paired)"
						+ x.toString() + "\n" + device.getAddress());
			}
		}
	}

	// The on-click listener for all devices in the ListViews
	private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
			// Cancel discovery because it's costly and we're about to connect
			mBtAdapter.cancelDiscovery();

			// Get the device MAC address, which is the last 17 chars in the
			// View
			String info = ((TextView) v).getText().toString();
			String address = info.substring(info.length() - 17);

			// Create the result Intent and include the MAC address
			Intent intent = new Intent();
			intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

			// Set result and finish this Activity
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	};

	private void doDiscovery() {
		setProgressBarIndeterminateVisibility(true);
		setTitle("Scaning...");
		if (mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}
		// Request discover from BluetoothAdapter
		mBtAdapter.startDiscovery();
	}

	// The BroadcastReceiver that listens for discovered devices and
	// changes the title when discovery is finished
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// If it's already paired, skip it, because it's been listed
				// already
				if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
					pairedListView.setEnabled(true);
					mPairedDevicesArrayAdapter.add(device.getName()
							+ "No paired" + "\n" + device.getAddress());
				}
				// When discovery is finished, change the Activity title
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				setProgressBarIndeterminateVisibility(false);
				// setTitle(R.string.select_device);
				if (mPairedDevicesArrayAdapter.getCount() == 0) {
					String noDevices = "NoDevice";
					mPairedDevicesArrayAdapter.add(noDevices);
					pairedListView.setEnabled(false);
				}
			}
		}
	};

	@Override
	public void onClick(View v) {
		doDiscovery();
		v.setVisibility(View.GONE);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onPause();
		super.onStop();
		// Make sure we're not doing discovery anymore
		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
		}
		// Unregister broadcast listeners
		this.unregisterReceiver(mReceiver);
	}

	@Override
	public void onBackPressed() {
		Toast.makeText(this, "Plase Select Device ...", Toast.LENGTH_SHORT)
				.show();
		super.onBackPressed();
	}

	// @Override
	// protected void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	// // Register for broadcasts when a device is discovered and discovery has
	// // finished
	// IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	// filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
	// registerReceiver(mReceiver, filter);
	// }
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// Register for broadcasts when a device is discovered and discovery has
		// finished
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(mReceiver, filter);
	}
}// End Class
