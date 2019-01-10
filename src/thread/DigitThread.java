package thread;

public class DigitThread extends Thread {
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int c=0;c<=9;c++) {
			//System.out.print("["+getId()+"]"+c);
			System.out.print(c);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
