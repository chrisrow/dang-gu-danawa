package DangGuDANAWA.Activity;


import DangGuDANAWA.ImageProcessing.Drawer;
import DangGuDANAWA.ImageProcessing.Preview;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public final class CameraConfiguration extends Activity {

	private Preview mPreview;
	public Preview getmPreview() {
		return mPreview;
	}

	private Drawer mDrawer;
	
	private LinearLayout mLinearMain;
	private Button btnCancel;
	//private Button btnSimulate;
	private Button btnRoadChange;
	private Context context;
	
	private LinearLayout mLinearPreview;
	private Button btnSnapShot;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Hide the window title.
		context = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mDrawer = new Drawer(this);
		InitDrawerLayout();
		
		// ī�޶� �����並 �����ϰ� �׸������� ��ü�� �ѱ��
		mPreview = new Preview(this, mDrawer);
		InitPreviewLayout();
		
		setContentView(mLinearPreview, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		addContentView(mLinearMain, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		
		// Ŭ�� �����ʸ� ���
		btnCancel.setOnClickListener(m_ClickListener);
		btnRoadChange.setOnClickListener(m_ClickListener);
		//btnSimulate.setOnClickListener(m_ClickListener);
		btnSnapShot.setOnClickListener(m_ClickListener);
	}
	
	private void InitPreviewLayout()
	{
		mLinearPreview = new LinearLayout(this);
		mLinearPreview.setOrientation(LinearLayout.HORIZONTAL);
		
		// drawer�� ���̾ƿ��� ���δ�
		mLinearPreview.addView(mPreview, new android.widget.LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f) );
		
		// ���ο� ���Ͼ� ���̾ƿ��� ��ư�� ���� ���δ�.
		LinearLayout linearSecond = new LinearLayout(this);
		linearSecond.setOrientation(LinearLayout.VERTICAL);
		linearSecond.setGravity(Gravity.CENTER);
		
		btnSnapShot = new Button(this);
		btnSnapShot.setBackgroundDrawable( this.getResources().getDrawable(DangGuDANAWA.Activity.R.drawable.stylecamera) );
		linearSecond.addView(btnSnapShot, new android.widget.LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT) );
	
		// Preview���̾ƿ��� ������ ���Ͼ�̾ƿ��� ���δ�.
		mLinearPreview.addView(linearSecond, new android.widget.LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 9.0f) );
	}
	
	private void InitDrawerLayout()
	{
		mLinearMain = new LinearLayout(this);
		mLinearMain.setOrientation(LinearLayout.HORIZONTAL);
		
		// drawer�� ���̾ƿ��� ���δ�
		mLinearMain.addView(mDrawer, new android.widget.LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f) );
		
		// ���ο� ���Ͼ� ���̾ƿ��� ��ư�� ���� ���δ�.
		LinearLayout linearSecond = new LinearLayout(this);
		linearSecond.setOrientation(LinearLayout.VERTICAL);
		linearSecond.setGravity(Gravity.CENTER);
//		
//		btnSimulate = new Button(this);
//		btnSimulate.setBackgroundDrawable( this.getResources().getDrawable(DangGuDANAWA.Activity.R.drawable.stylesimulation) );
//		btnSimulate.setText("�ù�");
//		btnSimulate.setVisibility(Button.INVISIBLE);
//		linearSecond.addView(btnSimulate, new android.widget.LinearLayout.LayoutParams(
//				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT) );
		
		btnCancel = new Button(this);
		btnCancel.setBackgroundDrawable( this.getResources().getDrawable(DangGuDANAWA.Activity.R.drawable.stylecancel) );
		btnCancel.setVisibility(Button.INVISIBLE);
		linearSecond.addView(btnCancel, new android.widget.LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT) );
		
		btnRoadChange = new Button(this);
		btnRoadChange.setBackgroundDrawable( this.getResources().getDrawable(DangGuDANAWA.Activity.R.drawable.stylechange) );
		btnRoadChange.setVisibility(Button.INVISIBLE);
		linearSecond.addView(btnRoadChange, new android.widget.LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT) );
		
		// main���̾ƿ��� ������ ���Ͼ�̾ƿ��� ���δ�.
		mLinearMain.addView(linearSecond, new android.widget.LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 9.0f) );
	}
	
	private View.OnClickListener m_ClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if( v.equals(btnCancel))
			{
				mPreview.RestartPreview();
				mDrawer.setM_bDrawFlag(true);
			}
			else if( v.equals(btnRoadChange))
			{
				int size = mDrawer.m_controller.m_arrResult.size();
				if( ++(mDrawer.m_controller.m_nResultCount) == size && size != 0)
				{
					mDrawer.m_controller.m_nResultCount = 0;
				}
//				Toast.makeText(context, "redX:" + mDrawer.m_controller.m_Balls[1].getX() +
//						"redY:" + mDrawer.m_controller.m_Balls[1].getY() , Toast.LENGTH_SHORT).show();
				mDrawer.setM_bDrawFlag(false);
				mDrawer.invalidate();
			}
//			else if( v.equals(btnSimulate))
//			{
//				mDrawer.setResultData();
//				Toast.makeText(v.getContext(), "����!!", Toast.LENGTH_SHORT).show();
//				finish();
//			}
			else if( v.equals(btnSnapShot))
			{
				mPreview.SnapShot();
			}
		}
	};
	
	public void VisibleButton()
	{
		//btnSimulate.setVisibility(Button.VISIBLE);
		btnCancel.setVisibility(Button.VISIBLE);
		btnRoadChange.setVisibility(Button.VISIBLE);
		btnSnapShot.setVisibility(Button.INVISIBLE);
	}
	public void InvisibleButton()
	{
		//btnSimulate.setVisibility(Button.INVISIBLE);
		btnCancel.setVisibility(Button.INVISIBLE);
		btnRoadChange.setVisibility(Button.INVISIBLE);
		btnSnapShot.setVisibility(Button.VISIBLE);
	}
}
