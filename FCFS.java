import java.util.*;

/*
FCFS:
T1: p1(0)
T12: p2(2)
T14: p3(4)
T17: p4(1)
T19: p5(5)

Process Turnaround Time Waiting Time
p1      11              1
p2      13              12
p3      16              14
p4      18              17
p5      24              19
*/
/*
DISP: 1
END

ID: p1
Arrive: 0
ExecSize: 10
Priority: 0


ID: p2
Arrive: 0
ExecSize: 1
Priority: 2


ID: p3
Arrive: 0
ExecSize: 2
Priority: 4


ID: p4
Arrive: 0
ExecSize: 1
Priority: 1


ID: p5
Arrive: 0
ExecSize: 5
Priority: 5

*/


//In charge of running full FCS simulation
public class FCFS {
    //Simulates CPU clock time
    int currentTime = 0;
    //Stores the Dispatcher Time
    int dispSize = 0;
    //List of all processes in the input file
    List<Process> allPs = new ArrayList<Process>();
    //List of all finished processes. (Where there are no more executes to complete)
    List<Process> finPs = new ArrayList<Process>();
    //List of all processes that are ready of the CPU and have arrived
    List<Process> readyPs = new ArrayList<Process>();
    //The dispatcher change log. Whenever it swaps something/a process starts.
    String changelog = "";

    //Variable to prevent duplicate arrivals if the time hasn't moved. E.g. if dispSize is 0
    int lastArrivalCheck = -1;
    //Cpu object
    Cpu cpu = new Cpu();

    public FCFS(int dispSize){
        this.dispSize = dispSize;
    }

    public String simulate(List<Process> ps){
        boolean complete = false;
        this.allPs = ps;
        currentTime = 0;
        
        //Input 1st item into the processor
        checkForNewArrivals();
        dispatcher(true);
        //currentTime += dispSize; //Fake the dispatch at the start. It will catch in the loop, as the CPU isn't busy, and there will be a new arrival.
        while(complete == false){
            //finished when finSize = allSize

            if (currentTime > 500){
                return "Error, Timeout to prevent infinite loop more than 500";
            }
            //TODO:: Check for new Arrivals --> Gives problems when dispatch timer is 0
            if (checkForNewArrivals() == true){
                //If there are new arrivals (possibly multiple). Use dispatcher to determine which one to pick. Don't increment time as there is no swap.
                if(cpu.isCpuBusy() == false){
                    dispatcher(false);
                }
                
            };


            //Check if the current process is finished.
            cpu.run();
            currentTime++;
            incrementWaiting(1);

            //Only invoke dispatcher when the current process is done.
            if(cpu.isCpuBusy() == true){
                if (cpu.isProcessFinished()){
                    cpu.getProcess().setFinishTime(currentTime);
                    finPs.add(cpu.getProcess());
                    cpu.clear();
                    if(finPs.size() == allPs.size()){
                        complete = true;
                    }else{
                        //if there are processes waiting
                        if (readyPs.size() > 0){
                            dispatcher(true);
                        }
                        
                    }
                    
                }

            }
        }
        return generateOutput();
    }

    //Invoked whenever something occurs that might lead to blocking of a process
    //In FCFS, invoked when a process is finished.
    private void dispatcher(boolean inclTime){
        //Choose Program to put in processor
        
        Process chosenP = readyPs.get(0);

        //For all the waiting processes
        for(Process p : readyPs){
            //FCFS algorithm
            //Search for the one that came first
            if (chosenP.getArrive() >= p.getArrive()){
                //Tie breaker with names
                // 1 < 2


                if(p.getId().compareTo(chosenP.getId())< 0){
                    chosenP = p;
                }
                
            }
        }

        //add dispatcher time
        if(inclTime == true){
            currentTime += dispSize;
            incrementWaiting(dispSize);

        } 
        //Remove process from waiting
        readyPs.remove(chosenP);
        //set process in cpu
        cpu.setProcess(chosenP);

        logProcessChange(chosenP);
        return;

    }

    //Checks if a new process has arrived.
    //If a new process has arrive, move to correct variable, return true
    //If no changes, return false
    private boolean checkForNewArrivals(){
        if (lastArrivalCheck != currentTime){ //BUG FIX --> When dispatcher t = 0.
            lastArrivalCheck = currentTime;
            Boolean ret = false;
            for (Process p : allPs){
                if (p.getArrive() == currentTime){
                    readyPs.add(p);
                    ret = true;
                }
            }
            return ret;
        }
        return false;


    }

    private void incrementWaiting(int wait){
        for (Process p: readyPs){
            p.addWait(wait);
        }
    }

    private void logProcessChange(Process p){
        changelog += "T" + currentTime + " " + p + System.lineSeparator();
    }


    private String generateOutput(){
        String output = changelog;
        output += System.lineSeparator();
        output += "Process Turnaround Time Waiting Time" + System.lineSeparator();

        for(Process p: allPs){
            output += p.getId() + "      " + p.getTurnAround() + "              " + p.getTimeWaiting() + System.lineSeparator();
        }


        return output;
    }


}