/*
 *  ReportServer
 *  Copyright (c) 2007 - 2020 InfoFabrik GmbH
 *  http://reportserver.net/
 *
 *
 * This file is part of ReportServer.
 *
 * ReportServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package net.sf.jxls.parser;

/**
 * @author Curtis Stanford
 * @author Leonid Vysochyn
 */
public abstract class Aggregator {
    public static Aggregator getInstance(String function) {
        if (function.equalsIgnoreCase("sum")) return new Sum();
        else if (function.equalsIgnoreCase("avg")) return new Average();
        else if (function.equalsIgnoreCase("min")) return new Minimum();
        else if (function.equalsIgnoreCase("max")) return new Maximum();
        else if (function.equalsIgnoreCase("count")) return new Count();
        else return null;
    }

    public void add(Object o) {
    }

    public Object getResult() {
        return null;
    }

    static class Sum extends Aggregator {
        private double total = 0.0;

        public void add(Object o) {
            if (o instanceof Number) total += ((Number) o).doubleValue();
        }

        public Object getResult() {
            return new Double(total);
        }
    }

    static class Average extends Aggregator {
        private double total = 0.0;
        private double count = 0.0;

        public void add(Object o) {
            if (o instanceof Number) {
                total += ((Number) o).doubleValue();
                count++;
            }
        }

        public Object getResult() {
            if (count == 0.0) return null;
            return new Double(total / count);
        }
    }

    static class Minimum extends Aggregator {
        private Object min = null;

        public void add(Object o) {
            if (o != null) {
                if (min == null) min = o;
                else if (o instanceof Comparable && (((Comparable) o).compareTo(min) < 0)) {
                    min = o;
                }
            }
        }

        public Object getResult() {
            return min;
        }
    }

    static class Maximum extends Aggregator {
        private Object max = null;

        public void add(Object o) {
            if (o != null) {
                if (max == null) max = o;
                else if (o instanceof Comparable && (((Comparable) o).compareTo(max) > 0) ) {
                    max = o;
                }
            }
        }

        public Object getResult() {
            return max;
        }
    }

    static class Count extends Aggregator {
        private int count = 0;

        public void add(Object o) {
            if (o != null) count++;
        }

        public Object getResult() {
            return count;
        }
    }
}
