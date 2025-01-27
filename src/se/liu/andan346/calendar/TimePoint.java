package se.liu.andan346.calendar;

public class TimePoint
{
    private int hour;
    private int minute;

    public TimePoint(final int hour, final int minute) {
	this.hour = hour;
	this.minute = minute;
    }

    public int getHour() {
	return hour;
    }

    public int getMinute() {
	return minute;
    }

    @Override public String toString() {
	return String.format("%02d:%02d", getHour(), getMinute());
    }

    public int toMinutes() {
	return getHour() * 60 + getMinute();
    }

    public int compareTo(TimePoint other) {
	return Math.clamp(this.toMinutes() - other.toMinutes(), -1, 1);
    }

    public boolean isValid() {
	return (0 <= getHour() && getHour() <= 23) && (0 <= getMinute() && getMinute() <= 59);
    }

}
