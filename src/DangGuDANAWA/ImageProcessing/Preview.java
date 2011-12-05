package DangGuDANAWA.ImageProcessing;

import java.io.IOException;

import DangGuDANAWA.Activity.CameraConfiguration;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Preview extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
	private Camera mCamera;
	private Drawer mDrawer;
	private boolean click = false; 
	private CameraConfiguration cameraActivity;
	
	public Preview(Context context, Drawer drawer) {
		super(context);
		// SurfaceHolder.Callback�� ���������ν� Surface�� ����/�Ҹ�Ǿ�����
		// �� �� �ֽ��ϴ�.
		cameraActivity = (CameraConfiguration) context;
		this.mDrawer = drawer;
		this.mHolder = getHolder();
		this.mHolder.addCallback(this);
		this.mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void SnapShot()
	{
		click = true;
		Log.i("Camera", "Shot OK" + click);
	}
	
	public void RestartPreview()
	{
		cameraActivity.InvisibleButton();
		mCamera.startPreview();
		click = false;
		mDrawer.data = null;			
		mDrawer.invalidate();
		mCamera.setPreviewCallback(mPreveListner); 
		Log.i("Camera", "Restart OK");
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		// Surface�� �����Ǿ��ٸ�, ī�޶��� �ν��Ͻ��� �޾ƿ� �� ī�޶���
		// Preview �� ǥ���� ��ġ�� �����մϴ�.
		mCamera = Camera.open();
		try 
		{
			mCamera.setPreviewDisplay(holder);
			mCamera.setPreviewCallback(mPreveListner); 
		} 
		catch (IOException exception) 
		{
			mCamera.release();
			mCamera = null;
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// �ٸ� ȭ������ ���ư���, Surface�� �Ҹ�˴ϴ�. ���� ī�޶��� Preview��
		// �����ؾ� �մϴ�. ī�޶�� ������ �� �ִ� �ڿ��� �ƴϱ⿡, ������� ����
		// ��� -��Ƽ��Ƽ�� �Ͻ����� ���°� �� ��� �� - �ڿ��� ��ȯ�ؾ��մϴ�.
		mCamera.setPreviewCallback(null);
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	};

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// ǥ���� ������ ũ�⸦ �˾����Ƿ� �ش� ũ��� Preview�� �����մϴ�.
		Camera.Parameters parameters = mCamera.getParameters();
		parameters.setPreviewSize(w, h);
		mCamera.setParameters(parameters);
		mCamera.startPreview();
	};

	private Camera.PreviewCallback mPreveListner = new Camera.PreviewCallback() {
		public void onPreviewFrame(byte[] data, Camera camera) {
			Size previewSize = camera.getParameters().getPreviewSize();
			Log.i("Camera", "PreviewCallback : " + click);
			if(click)
			{
				mCamera.stopPreview();
				click = false;
				mDrawer.previewSize = previewSize;
				mDrawer.data = data;
				mDrawer.invalidate();
				Log.i("Camera", "PreviewCallback OK");
			}
		}
	};
}
