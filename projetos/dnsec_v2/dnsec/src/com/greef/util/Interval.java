package com.greef.util;

/**
 * A class used as an interval of two values.
 * This is an immutable object.
 * @author Adrian BER
 */
public class Interval implements Comparable {

    /** The interval lower limit. */
    private Comparable from = null;
    /** The interval upper limit. */
    private Comparable to = null;

    /** Creates a new interval. */
    public Interval(Comparable from, Comparable to) {
        if (from != null && to != null && from.compareTo(to) > 0) {
            throw new IllegalArgumentException("From value must be smaller than to value");
        }
        this.from = from;
        this.to = to;
    }

    /** Creates a new interval that includes only one given value. */
    public Interval(Comparable value) {
        this.from = this.to = value;
    }

    /** Returns the lower limit of the interval. */
    public Comparable getFrom() {
        return from;
    }

    /** Returns the upper limit of the interval. */
    public Comparable getTo() {
        return to;
    }

    /** Returns true if the given value is in this interval. */
    public boolean contains(Comparable value) {
        return ((from == null) || (from.compareTo(value) <= 0))
                && ((to == null) || (to.compareTo(value) >= 0));
    }

    /** Returns true if the given interval is in this interval. */
    public boolean contains(Interval interval) {
        return ((from == null) || (from.compareTo(interval.from) <= 0))
                && ((to == null) || (to.compareTo(interval.to) >= 0));
    }

    /** Returns the intersection between this interval and the given one.
     * If the intersection is empty it returns null.
     */
    public Interval intersect(Interval that) {
        if ((this.from != null && that.to != null && this.from.compareTo(that.to) > 0)
            || (this.to != null && that.from != null && this.to.compareTo(that.from) < 0)) {
            return null;
        }
        if (this.from == null || (that.from != null && this.from.compareTo(that.from) <= 0)) {
            if (this.to != null && (that.to == null || this.to.compareTo(that.to) <= 0)) {
                return new Interval(that.from, this.to);
            } else {
                return that;
            }
        } else {
            if (this.to.compareTo(that.to) <= 0) {
                return this;
            } else {
                return new Interval(this.from, that.to);
            }
        }
    }

    public String toString() {
        return from.toString() + " -> " + to.toString();
    }

    public int compareTo(Object o) {
        if (o instanceof Interval) {
            return this.from.compareTo(((Interval)o).from);
        }
        throw new IllegalArgumentException("You cannot compare this object with an interval");
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interval)) return false;

        final Interval interval = (Interval) o;

        if (from != null ? !from.equals(interval.from) : interval.from != null) return false;
        if (to != null ? !to.equals(interval.to) : interval.to != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (from != null ? from.hashCode() : 0);
        result = 29 * result + (to != null ? to.hashCode() : 0);
        return result;
    }
}