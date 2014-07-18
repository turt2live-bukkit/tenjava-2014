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

import org.junit.Assert;
import org.junit.Test;

public class Point3DTest {

    private static final double DELTA = 1e-15;

    @Test
    public void TestConstructorEmpty() {
        Point3D point = new Point3D();

        Assert.assertEquals(0, point.getX());
        Assert.assertEquals(0, point.getY());
        Assert.assertEquals(0, point.getZ());
    }

    @Test
    public void TestConstructor() {
        Point3D point = new Point3D(1, 2, 3);

        Assert.assertEquals(1, point.getX());
        Assert.assertEquals(2, point.getY());
        Assert.assertEquals(3, point.getZ());
    }

    @Test
    public void TestSetters() {
        Point3D point = new Point3D();

        point.setX(13);
        Assert.assertEquals(13, point.getX());

        point.setY(14);
        Assert.assertEquals(14, point.getY());

        point.setZ(15);
        Assert.assertEquals(15, point.getZ());
    }

    @Test
    public void TestAdd() {
        Point3D point = new Point3D(10, 10, 10);

        Point3D added = point.add(10, 10, 10);
        Assert.assertNotEquals(point, added);

        Assert.assertEquals(20, added.getX());
        Assert.assertEquals(20, added.getY());
        Assert.assertEquals(20, added.getZ());

        Assert.assertEquals(10, point.getX());
        Assert.assertEquals(10, point.getY());
        Assert.assertEquals(10, point.getZ());
    }

    @Test
    public void TestSubtract() {
        Point3D point = new Point3D(20, 20, 20);

        Point3D added = point.add(-10, -10, -10);
        Assert.assertNotEquals(point, added);

        Assert.assertEquals(10, added.getX());
        Assert.assertEquals(10, added.getY());
        Assert.assertEquals(10, added.getZ());

        Assert.assertEquals(20, point.getX());
        Assert.assertEquals(20, point.getY());
        Assert.assertEquals(20, point.getZ());
    }

    @Test
    public void TestDistance() {
        Point3D point1 = new Point3D(1, 1, 1);
        Point3D point2 = new Point3D(2, 2, 2);

        Assert.assertEquals(3.0, point1.distanceSquared(point2), DELTA);
        Assert.assertEquals(3.0, point2.distanceSquared(point1), DELTA);

        point1 = new Point3D(10, 18, -50);
        point2 = new Point3D(45, 80, 1);

        Assert.assertEquals(7670.0, point1.distanceSquared(point2), DELTA);
        Assert.assertEquals(7670.0, point2.distanceSquared(point1), DELTA);
    }

    @Test
    public void TestSet3() {
        Point3D point = new Point3D();
        point.set(8, 9, 7);

        Assert.assertEquals(8, point.getX());
        Assert.assertEquals(9, point.getY());
        Assert.assertEquals(7, point.getZ());
    }

}
