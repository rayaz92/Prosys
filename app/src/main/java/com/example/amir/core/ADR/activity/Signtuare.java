package com.example.amir.core.ADR.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.amir.core.R;

public class Signtuare extends Activity {

	public LinearLayout mContent;
	public signature mSignature;

	public Button mClear, mGetSign, mCancel;
	public static String tempDir;
	public int count = 1;
	public static String current = null;
	public static Bitmap mBitmap;
	public View mView;
	public File mypath;
	public static String Filepath;
	private String uniqueId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.adrsigntuare);
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/GLP_Images");

		if (!directory.exists())
			directory.mkdir(); // directory is created;
		// prepareDirectory();
		uniqueId = getTodaysDate() + "_" + getCurrentTime();
		current = uniqueId + ".bmp";

		mypath = new File(directory, current);
		Filepath = directory + "/" + current;
		mContent = (LinearLayout) findViewById(R.id.linearLayoutsigntuare);
		mSignature = new signature(this, null);
		mSignature.setBackgroundColor(Color.WHITE);
		mContent.addView(mSignature, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		mClear = (Button) findViewById(R.id.clearsigntuare);
		mGetSign = (Button) findViewById(R.id.getsignsigntuare);
		mGetSign.setEnabled(false);
		mCancel = (Button) findViewById(R.id.cancelsigntuare);
		mView = mContent;

		// yourName = (EditText) findViewById(R.id.yourName);

		mClear.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.v("log_tag", "Panel Cleared");
				mSignature.clear();
				mGetSign.setEnabled(false);
			}
		});

		mGetSign.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			public void onClick(View v) {
				Log.v("log_tag", "Panel Saved");
				// boolean error = captureSignature();
				// if(error){
				mView.setDrawingCacheEnabled(true);
				mSignature.save(mView);
				// Bundle b = new Bundle();
				// b.putString("status", "done");
				// Intent intent = new Intent(Signtuare.this,
				// ReNewAppointment.class);

				// intent.putExtra("imagePath",mypath);
				// startActivity(intent);
				// intent.putExtra("status", "done");
				// setResult(RESULT_OK,intent);
				Intent result = new Intent();
				Calculate_Result.imageViewSigntuare.buildDrawingCache();
				Calculate_Result.imageViewSigntuare.setImageBitmap(mBitmap);
				// result.putExtra("Image", mBitmap);
				// if (mBitmap == null) {
				// Log.d("Image Null ", "5raaaaaaaaaaaaaaaaaaaaaaaaaa");
				// } else {
				// Log.d("Image Null ", "00000000");
				// result.putExtra("Image", mBitmap);
				// setResult(100);
				// setResult(100, result);
				// }
				// if (getParent() == null) {
				// setResult(100, result);
				// } else
				// getParent().setResult(100, result);
				// result.putExtra("Image", mBitmap);

				// setResult(100, result);
				// Calculate_Result.imageViewSigntuare.buildDrawingCache();
				// Calculate_Result.imageViewSigntuare.setImageBitmap(mBitmap);
				if (mypath == null) {
				} else
					mypath.delete();
				// Signtuare.this.finish();
				finish();
				// }
			}
		});

		mCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.v("log_tag", "Panel Canceled");
				Bundle b = new Bundle();
				b.putString("status", "cancel");
				Intent intent = new Intent();
				intent.putExtras(b);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

	}

	private String getTodaysDate() {

		final Calendar c = Calendar.getInstance();
		int todaysDate = (c.get(Calendar.YEAR) * 10000)
				+ ((c.get(Calendar.MONTH) + 1) * 100)
				+ (c.get(Calendar.DAY_OF_MONTH));
		Log.w("DATE:", String.valueOf(todaysDate));
		return (String.valueOf(todaysDate));

	}

	private String getCurrentTime() {

		final Calendar c = Calendar.getInstance();
		int currentTime = (c.get(Calendar.HOUR_OF_DAY) * 10000)
				+ (c.get(Calendar.MINUTE) * 100) + (c.get(Calendar.SECOND));
		Log.w("TIME:", String.valueOf(currentTime));
		return (String.valueOf(currentTime));

	}

	private boolean makedirs() {
		File tempdir = new File(tempDir);
		if (!tempdir.exists())
			tempdir.mkdirs();

		if (tempdir.isDirectory()) {
			File[] files = tempdir.listFiles();
			for (File file : files) {
				if (!file.delete()) {
					System.out.println("Failed to delete " + file);
				}
			}
		}
		return (tempdir.isDirectory());
	}

	public class signature extends View {
		private static final float STROKE_WIDTH = 5f;
		private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
		private Paint paint = new Paint();
		private Path path = new Path();

		private float lastTouchX;
		private float lastTouchY;
		private final RectF dirtyRect = new RectF();

		public signature(Context context, AttributeSet attrs) {
			super(context, attrs);
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeWidth(STROKE_WIDTH);
		}

		public void save(View v) {
			Log.v("log_tag", "Width: " + v.getWidth());
			Log.v("log_tag", "Height: " + v.getHeight());
			if (mBitmap == null) {
				mBitmap = Bitmap.createBitmap(mContent.getWidth(),
						mContent.getHeight(), Bitmap.Config.RGB_565);
			}
			Canvas canvas = new Canvas(mBitmap);
			try {

				FileOutputStream mFileOutStream = new FileOutputStream(mypath);

				v.draw(canvas);
				mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
				mFileOutStream.flush();
				mFileOutStream.close();

				// String url = Images.Media.insertImage(getContentResolver(),
				// mBitmap, "title", null);
				// Log.v("log_tag","url: " + url);

				// //In case you want to delete the file
				// boolean deleted = mypath.delete();
				// Log.v("log_tag","deleted: " + mypath.toString() + deleted);
				// //If you want to convert the image to string use base64
				// converter

			} catch (Exception e) {
				Log.v("log_tag", e.toString());
			}
		}

		public void clear() {
			path.reset();
			invalidate();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawPath(path, paint);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float eventX = event.getX();
			float eventY = event.getY();
			mGetSign.setEnabled(true);

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				path.moveTo(eventX, eventY);
				lastTouchX = eventX;
				lastTouchY = eventY;
				return true;

			case MotionEvent.ACTION_MOVE:

			case MotionEvent.ACTION_UP:

				resetDirtyRect(eventX, eventY);
				int historySize = event.getHistorySize();
				for (int i = 0; i < historySize; i++) {
					float historicalX = event.getHistoricalX(i);
					float historicalY = event.getHistoricalY(i);
					expandDirtyRect(historicalX, historicalY);
					path.lineTo(historicalX, historicalY);
				}
				path.lineTo(eventX, eventY);
				break;

			default:
				debug("Ignored touch event: " + event.toString());
				return false;
			}

			invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
					(int) (dirtyRect.top - HALF_STROKE_WIDTH),
					(int) (dirtyRect.right + HALF_STROKE_WIDTH),
					(int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

			lastTouchX = eventX;
			lastTouchY = eventY;

			return true;
		}

		private void debug(String string) {
		}

		private void expandDirtyRect(float historicalX, float historicalY) {
			if (historicalX < dirtyRect.left) {
				dirtyRect.left = historicalX;
			} else if (historicalX > dirtyRect.right) {
				dirtyRect.right = historicalX;
			}

			if (historicalY < dirtyRect.top) {
				dirtyRect.top = historicalY;
			} else if (historicalY > dirtyRect.bottom) {
				dirtyRect.bottom = historicalY;
			}
		}

		private void resetDirtyRect(float eventX, float eventY) {
			dirtyRect.left = Math.min(lastTouchX, eventX);
			dirtyRect.right = Math.max(lastTouchX, eventX);
			dirtyRect.top = Math.min(lastTouchY, eventY);
			dirtyRect.bottom = Math.max(lastTouchY, eventY);
		}
	}
}// End Class
