public class Sus_res_stop implements Runnable{

    Thread Th;
    boolean suspend_flag,stop_flag;
    Sus_res_stop(String tN){
        Th=new Thread(this,tN);
        suspend_flag = false;
        stop_flag =  false;
        Th.start();
    }
    public void run(){
        try{
            int j=1;
            while(++j<20){
                synchronized (this){
                    while (suspend_flag){
                        wait();
                    }
                    if(stop_flag){
                        break;
                    }
                }
            }
        }
        catch(InterruptedException IE){
            System.out.println("Thread Interrupted");
        }
    }
    synchronized  void my_suspend(){
        suspend_flag = true;
    }
    synchronized void my_resume(){
        suspend_flag = false;
        notify();
    }
    synchronized void my_stop(){
        suspend_flag = false;
        stop_flag = true;
        notify();
    }
    public static class eg_SRS{
        public static void main(String[] args) {
            try{
                Sus_res_stop S_R_S_T = new Sus_res_stop("SRS");
                System.out.println("Thread S_R_S_T is created and started ");
                Thread.sleep(2000);
                S_R_S_T.my_suspend();
                System.out.println("Thread S_R_S_T is suspended ");
                Thread.sleep(2000);
                S_R_S_T.my_resume();
                System.out.println("Thread S_R_S_T is resumed ");
                Thread.sleep(2000);
                S_R_S_T.my_suspend();
                System.out.println("Thread S_R_S_T is suspended");
                Thread.sleep(2000);

                S_R_S_T.my_resume();
                System.out.println("Thread S_R_S_T is resumed ");
                S_R_S_T.my_stop();
                System.out.println("Thread S_R_S_T is stopped ");
            }
            catch(InterruptedException IE){
                System.out.println("Generated Interrupted exception");
            }
        }
    }
}
