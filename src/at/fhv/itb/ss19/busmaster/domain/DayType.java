package at.fhv.itb.ss19.busmaster.domain;

import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public enum DayType {
	WORKDAY ("Workday"), SUNDAYANDHOLIDAY ("Sunday and Holiday"), SATURDAY ("Saturday"), SCHOOLDAY ("Schoolday");
	
	private String _value;
	
	private DayType (String value) {
		_value = value;
	}
	
	public String toString() {
		return _value;
	}

	public static DayType getDayTypeOfDate(LocalDate day) {
		Set<LocalDate> _holidays = new HashSet<>();
		HolidayManager hm = HolidayManager.getInstance(HolidayCalendar.AUSTRIA);
		hm.getHolidays(LocalDate.now().getYear()).forEach(holiday -> _holidays.add(holiday.getDate()));
		DayType type = DayType.WORKDAY;

		if (day.getDayOfWeek() == DayOfWeek.SUNDAY || _holidays.contains(day)) {
			type = DayType.SUNDAYANDHOLIDAY;
		} else if (day.getDayOfWeek() == DayOfWeek.SATURDAY) {
			type = DayType.SATURDAY;
		}

		return type;
	}
}
