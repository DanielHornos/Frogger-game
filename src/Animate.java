
public class Animate implements Runnable{

	MainPanel mp;
	SelectCharacterPanel scp;
	boolean fromMainPanel = false;
	
	Animate(MainPanel b){
		mp = b;
		fromMainPanel = true;
	}
	Animate(SelectCharacterPanel p){
		scp = p;
	}
	
	@Override
	public void run() {
		while(true) {
			if(fromMainPanel==true) {
			mp.update();
			} else {
			scp.update();
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}

}
