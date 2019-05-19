package at.fhv.itb.ss19.busmaster.domain;

public enum DayType {
	WORKDAY ("Workday"), SUNDAYANDHOLIDAY ("Sunday and Holiday"), SATURDAY ("Saturday"), SCHOOLDAY ("Schoolday");
	
	private String _value;
	
	private DayType (String value) {
		_value = value;
	}
	
	public String toString() {
		return _value;
	}
}
