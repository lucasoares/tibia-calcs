/*
 * Tibia Calcs GPL Source Code
 * Copyright (C) 2020 Lucas Soares de Miranda
 * Copyright (C) 2020 Luiz Claudio Soares de Miranda
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * License available on https://github.com/lucasoares/tibia-calcs/blob/master/LICENSE.md
 */
package com.tibiacalcs.core.timer;

import java.util.concurrent.TimeUnit;

public class Timer {
  private long begin = 0L;
  private long end = 0L;

  public Timer() {
    this(true);
  }

  public Timer(boolean forceStart) {
    if (forceStart) {
      start();
    }
  }

  public void start() {
    this.begin = System.nanoTime();
    this.end = 0L;
  }

  public void stop() {
    this.end = System.nanoTime();
  }

  public long elapsedNanoTime() {
    return System.nanoTime() - this.begin;
  }

  public long elapsedTime(TimeUnit unit) {
    return getUnitTime(unit, System.nanoTime() - this.begin);
  }

  public long elapsedMillisTime() {
    return elapsedTime(TimeUnit.MILLISECONDS);
  }

  @Override
  public String toString() {
    return Long.toString(getMillisTime());
  }

  public float getTime() {
    stop();

    return (float) (this.end - this.begin) / 1000;
  }

  /**
   * Gets the total execution time according to the TimeUnit.
   *
   * @param timeUnit The time unit to be used for conversion.
   * @param stop If the method will execute a {@link #stop()} before getting the time.
   * @return The total execution time.
   */
  public long getTime(TimeUnit timeUnit, boolean stop) {
    if (stop) {
      stop();
    }

    long total = this.end - this.begin;

    return getUnitTime(timeUnit, total);
  }

  private long getUnitTime(TimeUnit unit, long total) {
    switch (unit) {
      case MILLISECONDS:
        return TimeUnit.NANOSECONDS.toMillis(total);
      case MICROSECONDS:
        return TimeUnit.MICROSECONDS.toMillis(total);
      case SECONDS:
        return TimeUnit.NANOSECONDS.toSeconds(total);
      case MINUTES:
        return TimeUnit.NANOSECONDS.toMinutes(total);
      case HOURS:
        return TimeUnit.NANOSECONDS.toHours(total);
      case DAYS:
        return TimeUnit.NANOSECONDS.toDays(total);
      default:
        return total;
    }
  }

  /**
   * This method will execute the {@link #stop()} method before computing time.
   *
   * @see #getTime(TimeUnit, boolean)
   * @param timeUnit The time unit to be used for conversion.
   * @return The total execution time.
   */
  public long getTime(TimeUnit timeUnit) {
    return getTime(timeUnit, true);
  }

  public long getMillisTime() {
    stop();

    return (this.end - this.begin);
  }
}
