package DangGuDANAWA.Algorithm;


import DangGuDANAWA.Activity.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class MainView extends SurfaceView implements SurfaceHolder.Callback{
	
	private SurfaceHolder m_Holder;
	public DrawThread m_Thread;
	private Bitmap m_bitmap;
	
	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		m_Holder = this.getHolder();
		m_Holder.addCallback(this);
		Resources res = this.getResources();
		BitmapDrawable bd = (BitmapDrawable) res.getDrawable(R.drawable.biliardpan);
		m_bitmap = bd.getBitmap();
	}
	
	public MainView(Context context) {
		super(context);
		m_Holder = this.getHolder();
		m_Holder.addCallback(this);
		Resources res = this.getResources();
		BitmapDrawable bd = (BitmapDrawable) res.getDrawable(R.drawable.biliardpan);
		m_bitmap = bd.getBitmap();
	}
	
	//ǥ�� ������ �׸��� ����
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		m_Thread = new DrawThread(m_Holder);
		m_Thread.start();
		//m_Thread.drawDangu();
		
	}
	// ǥ���� ����� �� ũ�⸦ ���
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if(m_Thread != null)
			m_Thread.SizeChange(width, height);
	}
	
	//ǥ�� �ı� �� �׸��� ����
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		m_Thread.m_bExit = true;
		
	}
	/**
	 * ǥ�鿡 �׽�Ʈ�� ���� �׸��⸦ ����ϴ� Ŭ����
	 * @author �����
	 */
	class DrawThread extends Thread {
		SurfaceHolder m_thdHolder;
		int Width, Height;
		public Rect PanSize;
		boolean m_bExit;
		private BiliardsController controller; 
		public BiliardsController getController() {
			return controller;
		}
		public Canvas canvas;
		public DrawThread(SurfaceHolder _Holder) {
			this.m_thdHolder = _Holder;
			this.m_bExit = false;
			PanSize = new Rect(11,11,151, 251);
		}
		

		/**
		 * @author �����
		 * @param _Width	���� ũ��
		 * @param _Height	���� ũ��
		 * ǥ���� ũ�Ⱑ ����Ǹ� �� ����� ������ �ʱ�ȭ �ϴ� �Լ�
		 */
		public void SizeChange(int _Width, int _Height)
		{
			this.Width = _Width;
			this.Height = _Height;
			//this.Width = m_bitmap.getWidth();
			//this.Height = m_bitmap.getHeight();
			PanSize.set(25,25,m_bitmap.getWidth()-25, m_bitmap.getHeight()-25);	
		}
		public void drawDangu()
		{
			controller = new BiliardsController(this);
			synchronized (m_thdHolder) {
				canvas = m_thdHolder.lockCanvas();
				canvas.drawBitmap(m_bitmap, 0, 0, null);

				controller.DrawBalls(canvas);			//���� �׸���

				controller.DrawShotPoint(canvas); 		// ���� ������ �����ش�.

				controller.DrawCue(canvas);			//ġ�� ���� ������ �� �ִ� ť�� �׸���
				controller.DrawImageBall(canvas); 
				controller.MoveBalls();

				m_thdHolder.unlockCanvasAndPost(canvas);
			}
		}

		public void run() 
		{
			controller = new BiliardsController(this);
			controller.FindRoadAlgorithm();
			//controller.m_Cue.setAngle(controller.m_arrResult.get(0).Angle);
			while(m_bExit == false){
				synchronized (m_thdHolder) {
					canvas = m_thdHolder.lockCanvas();
					if (canvas == null) 
						break;
					canvas.drawBitmap(m_bitmap, 0, 0, null);
					
					controller.DrawBalls(canvas);			//���� �׸���
					
					controller.DrawShotPoint(canvas); 		// ���� ������ �����ش�.

					if(controller.m_bShowWay)
					{
						controller.DrawCue(canvas);			//ġ�� ���� ������ �� �ִ� ť�� �׸���
						controller.DrawImageBall(canvas); 
					}
					controller.MoveBalls();
					
					m_thdHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}