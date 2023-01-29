package dagidagi.toddlercards.animals.full;



class CardSetConfig {
	public static final String APP_PREFERENCES = "com.dagidagi.toddlercards.animals.settings";
	
	private static String[] SetName = { "pets", "farm" , "forest" , "africa", "asia", "australia" , "polar"};
	private static int[] SetSize =    { 12,      12,	 17,         16,	   10,     10,			 11};
	
	private static int current = -1;

	public static void setCurrent(int index){
		current = index;
	}
	
	public static int getCurrent(){
		return current;
	}
	
	public static void next(){
		if (current < SetName.length-1) {
			current++;
		} else {
			current = 0;
		}		
	}
	
	public static String getName() {
		return SetName[current];
	}
	public static int getSize() {
		return SetSize[current];
	}
	
	public static int getSize(int index) {
		return SetSize[index];
	}
	
	public static String getName(int index) {
		return SetName[index];
	}
	
	public static int getTotalSize() {
		int sum = 0;
		for(int d : SetSize)
		    sum += d;
		return sum;		
	}
	
}