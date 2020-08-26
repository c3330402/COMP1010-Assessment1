

public class Process{
    //Input Data
	private String id = "";
	private int arrive = 0;
	private int execSize = 0;
	private int priority = -1;


	//Data that will be modified throughout
	private int execsRemaining = 0;
	private int timeWaiting = 0;
	private int finishTime = 0;


	public Process(String id, int arrive, int execSize, int priority){
		this.id = id;
		this.arrive = arrive;
		this.execSize = execSize;
		this.priority = priority;

		execsRemaining = execSize;
	}

	public int calculateTurnAround(int finishTime){
		return finishTime - arrive;
	}

	public void tick(){
		execsRemaining--;
	}

	public int getExecsRemaining(){
		return execsRemaining;
	}

	public void addWait(int wait){
		timeWaiting += wait;
	}
	public int getTimeWaiting(){
		return timeWaiting;
	}


	public int getArrive(){
		return arrive;
	}

	public String getId(){
		return id;
	}
	public String toString(){
		String output = id;

		if (priority != -1){
			output += "(" + priority + ")";
		}

		return output;
	}

	public int getTurnAround(){
		return finishTime - arrive;
	}
	public void setFinishTime(int time){
		finishTime = time;
	}

}
