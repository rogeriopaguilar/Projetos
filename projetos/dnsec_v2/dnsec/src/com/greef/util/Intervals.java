package com.greef.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A set of ordered intervals to which you can add/remove Intervals without
 * duplication.
 * @author Adrian BER
 */
public class Intervals {

    /** The underlying collection of intervals.*/
    private List intervals = new ArrayList();

    /** Adds an interval.
     * @param interval the interval to be added, if null nothing happens
     */
    public void add(Interval interval) {
        if (interval == null) {
            return;
        }
        int n = intervals.size();
        int idx = -1;
        int i = 0;
        for (Iterator iterator = intervals.iterator(); iterator.hasNext(); i++) {
            Comparable intervalFrom = interval.getFrom();
            Comparable intervalTo = interval.getTo();
            Interval itv = (Interval) iterator.next();

            int tf = intervalTo.compareTo(itv.getFrom());
            if (tf < 0) {
                idx = i;
                break;
            }

            int ft = intervalFrom.compareTo(itv.getTo());
            if (ft > 0) {
                continue;
            }

            int ff = intervalFrom.compareTo(itv.getFrom());
            int tt = intervalTo.compareTo(itv.getTo());
            if (ff == 0 && tt == 0) {
                return;
            }

            if (ff <= 0) {
                if (tt < 0) {
                    if (idx < 0) {
                        idx = i;
                    }
                    iterator.remove();
                    interval = new Interval(intervalFrom, itv.getTo());
                    break;
                } else {
                    if (idx < 0) {
                        idx = i;
                    }
                    iterator.remove();
                }
            } else {
                if (tt <= 0) {
                    return;
                } else {
                    if (idx < 0) {
                        idx = i;
                    }
                    iterator.remove();
                    interval = new Interval(itv.getFrom(), intervalTo);
                }
            }
        }

        if (idx < 0) {
            idx = n;
        }
        intervals.add(idx, interval);
    }

    /** Removes an interval.
     * @param interval the interval to be removed, if null nothing happens
     */
    public void remove(Interval interval) {
        if (interval == null) {
            return;
        }
        List toAdd = new ArrayList();
        int idx = -1;
        int i = 0;
        Comparable intervalTo = interval.getTo();
        Comparable intervalFrom = interval.getFrom();
        for (Iterator iterator = intervals.iterator(); iterator.hasNext(); i++) {
            Interval itv = (Interval) iterator.next();

            int tf = intervalTo.compareTo(itv.getFrom());
            if (tf < 0) {
                break;
            }

            int ft = intervalFrom.compareTo(itv.getTo());
            if (ft > 0) {
                continue;
            }

            int ff = intervalFrom.compareTo(itv.getFrom());
            int tt = intervalTo.compareTo(itv.getTo());
            if (ff == 0 && tt == 0) {
                iterator.remove();
                break;
            }

            if (ff <= 0) {
                if (tt < 0) {
                    if (idx < 0) {
                        idx = i;
                    }
                    iterator.remove();
                    toAdd.add(new Interval(intervalTo, itv.getTo()));
                    break;
                } else {
                    iterator.remove();
                }
            } else {
                if (tt < 0) {
                    if (idx < 0) {
                        idx = i;
                    }
                    iterator.remove();
                    toAdd.add(new Interval(itv.getFrom(), intervalFrom));
                    toAdd.add(new Interval(intervalTo, itv.getTo()));
                } else {
                    if (idx < 0) {
                        idx = i;
                    }
                    iterator.remove();
                    toAdd.add(new Interval(itv.getFrom(), intervalFrom));
                }
            }
        }

        if (toAdd.size() > 0) {
            intervals.addAll(idx, toAdd);
        }
    }

    /** Returns if this collection of intervals contains the given value. */
    public boolean contains(Comparable value) {
        for (Iterator iterator = intervals.iterator(); iterator.hasNext();) {
            Interval itv = (Interval) iterator.next();
            if (itv.contains(value)) {
                return true;
            }
        }
        return false;
    }

    /** Returns if this collection of intervals contains all the values
     * in the given interval. */
    public boolean contains(Interval interval) {
        for (Iterator iterator = intervals.iterator(); iterator.hasNext();) {
            Interval itv = (Interval) iterator.next();
            if (itv.contains(interval)) {
                return true;
            }
        }
        return false;
    }

    /** Returns the intervals as a list. */
    public List getIntervals() {
        return new ArrayList(intervals);
    }

    /** Returns the intersection between this set of intervals and the given interval.
     * @param interval the given interval
     * @return the intervals intersection
     */
    public Intervals intersect(Interval interval) {
        Intervals x = new Intervals();
        for (Iterator iterator = intervals.iterator(); iterator.hasNext();) {
            Interval itv = (Interval) iterator.next();
            if (itv.getTo().compareTo(interval.getFrom()) < 0) {
                continue;
            }
            Interval aux = interval.intersect(itv);
            if (aux == null) {
                continue;
            }
            x.add(aux);
        }
        return x;
    }

    /** Returns the minimmum value in the given interval. */
    public Comparable getMinValue(Comparable from, Comparable to) {
        for (Iterator iterator = intervals.iterator(); iterator.hasNext();) {
            Interval itv = (Interval) iterator.next();
            if (to.compareTo(itv.getFrom()) < 0) {
                continue;
            }
            if (from.compareTo(itv.getTo()) > 0) {
                break;
            }
        }
        return null;
    }

    /** Removes all the intervals in this collection. */
    public void clear() {
        intervals.clear();
    }

    /** Returns if this collection of intervals is empty. */
    public boolean isEmpty() {
        return intervals.isEmpty();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Intervals)) return false;

        final Intervals intervals1 = (Intervals) o;

        if (!intervals.equals(intervals1.intervals)) return false;

        return true;
    }

    public int hashCode() {
        return intervals.hashCode();
    }
}
