package at.fhv.itb.ss19.busmaster.domain;

public enum ChangeStatus {
	OK("ok"), CHANGED("changed, not saved");
	
	private String _value;
	
	private ChangeStatus(String value) {
		_value = value;
	}
	
	public String toString() {
		return _value;
	}
}
