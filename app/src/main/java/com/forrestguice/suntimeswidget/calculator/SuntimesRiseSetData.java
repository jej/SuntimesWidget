/**
    Copyright (C) 2014 Forrest Guice
    This file is part of SuntimesWidget.

    SuntimesWidget is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SuntimesWidget is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with SuntimesWidget.  If not, see <http://www.gnu.org/licenses/>.
*/ 

package com.forrestguice.suntimeswidget.calculator;

import android.content.Context;

import com.forrestguice.suntimeswidget.R;
import com.forrestguice.suntimeswidget.settings.WidgetSettings;

import java.util.Calendar;

public class SuntimesRiseSetData extends SuntimesData
{
    private Context context;

    public SuntimesRiseSetData(Context context, int appWidgetId)
    {
        this.context = context;
        initFromSettings(context, appWidgetId);
    }
    public SuntimesRiseSetData(SuntimesRiseSetData other)
    {
        this.context = other.context;
        initFromOther(other, other.layoutID);
    }
    public SuntimesRiseSetData(SuntimesRiseSetData other, int layoutID)
    {
        this.context = other.context;
        initFromOther(other, layoutID);
    }

    /**
     * Property: layoutID
     */
    private int layoutID = R.layout.layout_widget_1x1_0i;
    public int layoutID()
    {
        return layoutID;
    }
    public void setLayoutID( int id )
    {
        layoutID = id;
    }

    /**
     * Property: time mode
     */
    private WidgetSettings.TimeMode timeMode;
    public WidgetSettings.TimeMode timeMode()
    {
        return timeMode;
    }
    public void setTimeMode( WidgetSettings.TimeMode mode )
    {
        timeMode = mode;
    }

    /**
     * Property: compare mode
     */
    private WidgetSettings.CompareMode compareMode;
    public WidgetSettings.CompareMode compareMode()
    {
        return compareMode;
    }
    public void setCompareMode( WidgetSettings.CompareMode mode )
    {
        compareMode = mode;
    }

    /**
     * result: sunrise today
     */
    private Calendar sunriseCalendarToday;
    public boolean hasSunriseTimeToday()
    {
        return (sunriseCalendarToday != null);
    }
    public Calendar sunriseCalendarToday()
    {
        return sunriseCalendarToday;
    }

    /**
     * result: sunset today
     */
    private Calendar sunsetCalendarToday;
    public boolean hasSunsetTimeToday()
    {
        return (sunsetCalendarToday != null);
    }
    public Calendar sunsetCalendarToday()
    {
        return sunsetCalendarToday;
    }

    /**
     * result: sunrise other
     */
    private Calendar sunriseCalendarOther;
    public boolean hasSunriseTimeOther()
    {
        return (sunriseCalendarOther != null);
    }
    public Calendar sunriseCalendarOther()
    {
        return sunriseCalendarOther;
    }

    /**
     * result: sunset other
     */
    private Calendar sunsetCalendarOther;
    public boolean hasSunsetTimeOther()
    {
        return (sunsetCalendarOther != null);
    }
    public Calendar sunsetCalendarOther()
    {
        return sunsetCalendarOther;
    }

    /**
     * Property: day delta prefix
     */
    private String dayDeltaPrefix;
    public String dayDeltaPrefix()
    {
        return dayDeltaPrefix;
    }

    /**
     * Property: day length ("today")
     */
    private long dayLengthToday;
    public long dayLengthToday()
    {
        return dayLengthToday;
    }

    /**
     * Property: day length ("other")
     */
    private long dayLengthOther;
    public long dayLengthOther()
    {
        return dayLengthOther;
    }

    /**
     * @param other another instance of SuntimesRiseSetData
     * @param layoutID an R.layout.someLayoutID to be associated w/ this data
     */
    protected void initFromOther( SuntimesRiseSetData other, int layoutID )
    {
        initFromOther(other);

        this.layoutID = layoutID;
        this.compareMode = other.compareMode();
        this.timeMode = other.timeMode();

        this.sunriseCalendarToday = other.sunriseCalendarToday();
        this.sunsetCalendarToday = other.sunsetCalendarToday();
        this.sunriseCalendarOther = other.sunriseCalendarOther();
        this.sunsetCalendarOther = other.sunsetCalendarOther();
        this.dayLengthToday = other.dayLengthToday();
        this.dayLengthOther = other.dayLengthOther();
        this.dayDeltaPrefix = other.dayDeltaPrefix();
    }

    /**
     * @param context a context used to access shared prefs
     * @param appWidgetId the widgetID to load settings from (0 for app)
     */
    @Override
    protected void initFromSettings(Context context, int appWidgetId)
    {
        super.initFromSettings(context, appWidgetId);
        this.timeMode = WidgetSettings.loadTimeModePref(context, appWidgetId);
        this.compareMode = WidgetSettings.loadCompareModePref(context, appWidgetId);
    }

    /**
     * Calculate
     */
    @Override
    public void calculate()
    {
        //Log.v("SuntimesWidgetData", "time mode: " + timeMode);
        //Log.v("SuntimesWidgetData", "location_mode: " + locationMode.name());
        //Log.v("SuntimesWidgetData", "latitude: " + location.getLatitude());
        //Log.v("SuntimesWidgetData", "longitude: " + location.getLongitude());
        //Log.v("SuntimesWidgetData", "timezone_mode: " + timezoneMode.name());
        //Log.v("SuntimesWidgetData", "timezone: " + timezone);
        //Log.v("SuntimesWidgetData", "compare mode: " + compareMode.name());

        SuntimesCalculatorFactory calculatorFactory = new SuntimesCalculatorFactory(context, calculatorMode);
        this.calculator = calculatorFactory.createCalculator(location, timezone);

        todaysCalendar = Calendar.getInstance(timezone);
        otherCalendar = Calendar.getInstance(timezone);

        if (todayIsNotToday())
        {
            todaysCalendar.set(todayIs.get(Calendar.YEAR), todayIs.get(Calendar.MONTH), todayIs.get(Calendar.DAY_OF_MONTH));
            otherCalendar.set(todayIs.get(Calendar.YEAR), todayIs.get(Calendar.MONTH), todayIs.get(Calendar.DAY_OF_MONTH));
        }

        switch (compareMode)
        {
            case YESTERDAY:
                dayDeltaPrefix = context.getString(R.string.delta_day_yesterday);
                otherCalendar.add(Calendar.DAY_OF_MONTH, -1);
                break;

            case TOMORROW:
            default:
                dayDeltaPrefix = context.getString(R.string.delta_day_tomorrow);
                otherCalendar.add(Calendar.DAY_OF_MONTH, 1);
                break;
        }

        date = todaysCalendar.getTime();
        dateOther = otherCalendar.getTime();

        switch (timeMode)
        {
            case NOON:
                sunriseCalendarToday = sunsetCalendarToday = calculator.getSolarNoonCalendarForDate(todaysCalendar);
                sunriseCalendarOther = sunsetCalendarOther = calculator.getSolarNoonCalendarForDate(otherCalendar);
                break;

            case CIVIL:
                sunriseCalendarToday = calculator.getCivilSunriseCalendarForDate(todaysCalendar);
                sunsetCalendarToday = calculator.getCivilSunsetCalendarForDate(todaysCalendar);
                sunriseCalendarOther = calculator.getCivilSunriseCalendarForDate(otherCalendar);
                sunsetCalendarOther = calculator.getCivilSunsetCalendarForDate(otherCalendar);
                break;

            case NAUTICAL:
                sunriseCalendarToday = calculator.getNauticalSunriseCalendarForDate(todaysCalendar);
                sunsetCalendarToday = calculator.getNauticalSunsetCalendarForDate(todaysCalendar);
                sunriseCalendarOther = calculator.getNauticalSunriseCalendarForDate(otherCalendar);
                sunsetCalendarOther = calculator.getNauticalSunsetCalendarForDate(otherCalendar);
                break;

            case ASTRONOMICAL:
                sunriseCalendarToday = calculator.getAstronomicalSunriseCalendarForDate(todaysCalendar);
                sunsetCalendarToday = calculator.getAstronomicalSunsetCalendarForDate(todaysCalendar);
                sunriseCalendarOther = calculator.getAstronomicalSunriseCalendarForDate(otherCalendar);
                sunsetCalendarOther = calculator.getAstronomicalSunsetCalendarForDate(otherCalendar);
                break;

            case OFFICIAL:
            default:
                sunriseCalendarToday = calculator.getOfficialSunriseCalendarForDate(todaysCalendar);
                sunsetCalendarToday = calculator.getOfficialSunsetCalendarForDate(todaysCalendar);
                sunriseCalendarOther = calculator.getOfficialSunriseCalendarForDate(otherCalendar);
                sunsetCalendarOther = calculator.getOfficialSunsetCalendarForDate(otherCalendar);
                break;
        }

        dayLengthToday = (sunsetCalendarToday == null || sunriseCalendarToday == null)
                ? -1 : sunsetCalendarToday.getTimeInMillis() - sunriseCalendarToday.getTimeInMillis();

        dayLengthOther = ((sunriseCalendarOther == null || sunsetCalendarOther == null))
                ? -1 : sunsetCalendarOther.getTimeInMillis() - sunriseCalendarOther.getTimeInMillis();

        super.calculate();
    }
}


