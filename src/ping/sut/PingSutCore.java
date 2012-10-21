package ping.sut;

import java.util.Random;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PingSutCore extends Activity {
	private Button bJempol;
	private Button bTelunjuk;
	private Button bKelingking;
	private Button bAbout;
	static final int DF_RESULT = 0;
	static final int DF_ABOUT = 1;
	private String userAct = null;
	private String androidAct = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ping_sut_core);
        
        bJempol = (Button) findViewById(R.id.bJempol);
        bTelunjuk = (Button) findViewById(R.id.bTelunjuk);
        bKelingking = (Button) findViewById(R.id.bKelingking);
        bAbout = (Button) findViewById(R.id.bAbout);
        bAbout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(DF_ABOUT);
			}
        });
        
        bJempol.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				sutGameCore("jempol");
			}
        });
        bTelunjuk.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				sutGameCore("telunjuk");
			}
        });
        bKelingking.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				sutGameCore("kelingking");
			}
        });
    }
    
    protected void sutGameCore(String option) {
    	int androidGen = genRandomNumber();
    	String sutGen = getGenSut(androidGen);
    	userAct = option;
    	androidAct = sutGen; 
    	//System.out.println(option+" | "+sutGen);
    	showDialog(DF_RESULT);
	}

	private String getGenSut(int input) {
		String result = null;
    	switch(input){
    	case 1:
    		result = "jempol";
    		break;
    	case 2:
    		result = "telunjuk";
    		break;
    	case 3:
    		result = "kelingking";
    		break;
    	}
    	return result;
	}

	private int genRandomNumber() {
		int START = 1;
        int END = 3;
        Random random = new Random();
        //get the range, casting to long to avoid overflow problems
        long range = (long)END - (long)START + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * random.nextDouble());
        int randomNumber =  (int)(fraction + START);
        return randomNumber;
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DF_RESULT:
			AlertDialog.Builder resDialogBuilder = new AlertDialog.Builder(PingSutCore.this);
			LayoutInflater li0 = LayoutInflater.from(PingSutCore.this);
			resDialogBuilder.setView(li0.inflate(R.layout.ping_sut_result_dialog, null));
			final AlertDialog resDialog = resDialogBuilder.create();
			resDialog.setIcon(R.drawable.dialog_icon);
			resDialog.setTitle("Hasil PingSut");
			resDialog.setButton("Tutup", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					resDialog.cancel();
				}
            });
			return resDialog;
        
		case DF_ABOUT:
			AlertDialog.Builder abtDialogBuilder = new AlertDialog.Builder(PingSutCore.this);
			abtDialogBuilder.setIcon(R.drawable.about);
			abtDialogBuilder.setTitle("Tentang PingSut");
			abtDialogBuilder.setMessage("PingSut adalah aplikasi untuk simulasi permainan pingsut\n\nDibuat oleh Ardhi Wijayanto\n2012");
			final AlertDialog abtDialog = abtDialogBuilder.create();
			abtDialog.setButton("OKe", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					abtDialog.cancel();
				}
			});
			return abtDialog;
			
		default:
    	    return super.onCreateDialog(id);
    	}
    }
	
	protected void onPrepareDialog(final int id, final Dialog dialog){
		switch (id) {
		case DF_RESULT:
			AlertDialog resDialog = (AlertDialog) dialog;
			ImageView imgUser = (ImageView) resDialog.findViewById(R.id.ivUser);
			ImageView imgAndroid = (ImageView) resDialog.findViewById(R.id.ivAndroid);
			TextView tvResult = (TextView) resDialog.findViewById(R.id.tvResult); 
			if(userAct.equals("jempol")){
				imgUser.setImageResource(R.drawable.jempol);
			} else if(userAct.equals("telunjuk")){
				imgUser.setImageResource(R.drawable.telunjuk);
			} else {
				imgUser.setImageResource(R.drawable.kelingking);
			}
			
			if(androidAct.equals("jempol")){
				imgAndroid.setImageResource(R.drawable.jempol);
			} else if(androidAct.equals("telunjuk")){
				imgAndroid.setImageResource(R.drawable.telunjuk);
			} else {
				imgAndroid.setImageResource(R.drawable.kelingking);
			}
			
			if((userAct.equals("jempol") && androidAct.equals("telunjuk")) || 
					(userAct.equals("telunjuk") && androidAct.equals("kelingking")) ||
					(userAct.equals("kelingking") && (androidAct.equals("jempol")))){
				tvResult.setText("Kamu MENANG");
			} else if(userAct.equals(androidAct)){
				tvResult.setText("SERI");
			} else {
				tvResult.setText("Kamu KALAH");
			}
			
			break;
		}
		super.onPrepareDialog(id, dialog);
	}
	
}
