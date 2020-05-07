package eu.mytthew;

public class StatusInfo {
	String name;
	String status;

	public StatusInfo(String name, String status) {
		this.name = name;
		this.status = status;
	}

	@Override
	public String toString() {
		return "name = '" + name + '\''
				+ " status = " + status
				+ "\n";
	}
}
