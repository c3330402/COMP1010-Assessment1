


public class Cpu {
    Process currentProcess;

    public void run(){
        if(isCpuBusy() == true){
            currentProcess.tick();
        }
    }

    public void setProcess(Process newProcess){
        currentProcess = newProcess;
    }
    public Process getProcess(){
        return currentProcess;
    }

    public boolean isProcessFinished(){
        if (currentProcess.getExecsRemaining() == 0){
            return true;
        }
        return false;
    }
    public boolean isCpuBusy(){
        if(currentProcess == null){
            return false;
        }
        return true;
    }
    public void clear(){
        currentProcess = null;
    }
}