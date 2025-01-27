package se.liu.andan346.calendar;

public class SimpleDate
{
    private int year;
    private Month month;
    private int day;

    public SimpleDate(final int year, final Month month, final int day) {
	this.year = year;
	this.month = month;
	this.day = day;
    }

    public int getYear() {
	return year;
    }

    public Month getMonth() {
	return month;
    }

    public int getDay() {
	return day;
    }

    @Override public String toString() {
	String month = getMonth().getName();
	return month.substring(0, 1).toUpperCase() + month.substring(1) + " " + getDay() + ", " + getYear();
    }

}
