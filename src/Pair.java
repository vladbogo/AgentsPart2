
public class Pair {
	private int i, j;
	
	public Pair(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
	
	public String toString() {
		String res = "";
		res += "(" + i + "," + j +")";
		return res;
	}
	
	public boolean equals(Pair p) {
		return (i == p.getI() && j == p.getJ());
	}
}
