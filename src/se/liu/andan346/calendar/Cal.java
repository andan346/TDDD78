package se.liu.andan346.calendar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Cal
{
    private List<Appointment> appointments;

    public Cal() {
	this.appointments = new ArrayList<>();
    }

    public void show() {
	List<Appointment> sortedAppointments = appointments.stream()
		.sorted(Comparator.comparingInt((Appointment app) -> app.getDate().getYear())
					    .thenComparingInt(app -> app.getDate().getDay())
					    .thenComparingInt(app -> app.getDate().getMonth().getNumber())
					    .thenComparingInt(app -> app.getTimeSpan().getStart().toMinutes())
					    .thenComparing(Appointment::getSubject))
		.toList();

	for (Appointment appointment : sortedAppointments) {
	    System.out.println(appointment);
	}
    }

    public void book(int year, String month, int day,
		     int startHour, int startMinute, int endHour,
		     int endMinute, String subject)
    {
	TimePoint start = new TimePoint(startHour, startMinute);
	TimePoint end = new TimePoint(endHour, endMinute);
	TimeSpan timeSpan = new TimeSpan(start, end);
	Month newMonth = new Month(month, Month.getMonthNumber(month), Month.getMonthDays(month));
	SimpleDate date = new SimpleDate(year, newMonth, day);
	Appointment appointment = new Appointment(subject, date, timeSpan);

	if (year <= 1970)
	    throw new IllegalArgumentException("Invalid year provided. Expected year > 1970.");
	if (!start.isValid())
	    throw new IllegalArgumentException("Invalid starting timepoint provided. Expected hour and minute within the span [0, 23] and [0, 59] respectively.");
	if (!end.isValid())
	    throw new IllegalArgumentException("Invalid ending timepoint provided. Expected hour and minute within the span [0, 23] and [0, 59] respectively.");
	int monthDays = Month.getMonthDays(month);
	if (monthDays < day) {
	    if (monthDays == -1)
		throw new IllegalArgumentException("Invalid month provided. Expected january, february, ..., december");
	    else
	    	throw new IllegalArgumentException(String.format("Invalid day number provided. Expected value within the span [0,%d].", newMonth.getDays()));
	}

	this.appointments.add(appointment);
    }

    public static void main(String[] args) {
	Cal cal = new Cal();
	cal.book(2054, "june", 22, 0, 0, 23, 59, "50th birthday");
	cal.book(2025, "january", 27, 8, 15, 10, 0, "Java lab");
	cal.book(2025, "february", 25, 20, 0, 22, 0, "Concert");
	cal.book(2027, "april", 1, 14, 0, 15, 0, "Troll 2");
	cal.book(2027, "april", 1, 11, 5, 12, 45, "Troll 1");
	cal.book(2004, "june", 22, 2, 50, 3, 15, "Be born or something idk");
	cal.show();
    }

}
