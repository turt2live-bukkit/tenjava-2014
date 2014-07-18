/*******************************************************************************
 * Copyright (C) 2014 Travis Ralston (turt2live)
 *
 * This software is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package com.turt2live.survive.util;

/**
 * Represents a point in 3D space
 *
 * @author turt2live
 */
public final class Point3D {

    private int x = 0;
    private int y = 0;
    private int z = 0;

    /**
     * Creates a new 3D point at the specified location
     *
     * @param x the x coordinate
     * @param y the Y coordinate
     * @param z the Z coordinate
     */
    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a new 3D point at (0, 0, 0)
     */
    public Point3D() {
        this(0, 0, 0);
    }

    /**
     * Adds a series of coordinate values to this point, returning a
     * new Point3D object.
     *
     * @param x the x value to add
     * @param y the y value to add
     * @param z the z value to add
     *
     * @return the new Point3D that represents the value of these points
     */
    public Point3D add(int x, int y, int z) {
        return new Point3D(x + this.x, y + this.y, z + this.z);
    }

    /**
     * Gets the distance from this point to another point as a
     * squared value. This will always return a 0 or positive
     * value.
     *
     * @param point the test point
     *
     * @return the distance between the points, or 0 if the supplied point is null
     */
    public double distanceSquared(Point3D point) {
        if (point == null) return 0; // Can't measure distance to nothing

        int xd = point.x - x;
        int yd = point.y - y;
        int zd = point.z - z;

        xd = xd < 0 ? -xd : xd;
        yd = yd < 0 ? -yd : yd;
        zd = zd < 0 ? -zd : zd;

        return (xd * xd) + (yd * yd) + (zd * zd);
    }

    /**
     * Gets the X coordinate
     *
     * @return the coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y coordinate
     *
     * @return the coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the Z coordinate
     *
     * @return the coordinate
     */
    public int getZ() {
        return z;
    }

    /**
     * Sets the X coordinate
     *
     * @param x the new coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the Y coordinate
     *
     * @param y the new coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets the Z coordinate
     *
     * @param z the new coordinate
     */
    public void setZ(int z) {
        this.z = z;
    }

    /**
     * Sets all three components of this Point3D in one shot
     *
     * @param x the new x coordinate
     * @param y the new y coordinate
     * @param z the new z coordinate
     */
    public void set(int x, int y, int z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3D)) return false;

        Point3D point3D = (Point3D) o;

        if (x != point3D.x) return false;
        if (y != point3D.y) return false;
        if (z != point3D.z) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
