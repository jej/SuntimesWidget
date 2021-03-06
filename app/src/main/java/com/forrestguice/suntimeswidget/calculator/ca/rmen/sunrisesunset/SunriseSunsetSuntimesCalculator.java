/**
    Copyright (C) 2017 Forrest Guice
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

package com.forrestguice.suntimeswidget.calculator.ca.rmen.sunrisesunset;

import com.forrestguice.suntimeswidget.calculator.SuntimesCalculator;
import com.forrestguice.suntimeswidget.calculator.SuntimesCalculatorDescriptor;
import com.forrestguice.suntimeswidget.settings.WidgetSettings;
import java.util.Calendar;
import java.util.TimeZone;

import ca.rmen.sunrisesunset.SunriseSunset;

public class SunriseSunsetSuntimesCalculator implements SuntimesCalculator
{
    public static final String NAME = "ca.rmen.sunrisesunset";
    public static final String REF = "com.forrestguice.suntimeswidget.calculator.ca.rmen.sunrisesunset.SunriseSunsetSuntimesCalculator";

    WidgetSettings.Location location;

    public SunriseSunsetSuntimesCalculator() { /* EMPTY */ }

    @Override
    public void init(WidgetSettings.Location locationSetting, String timezone)
    {
        this.location = locationSetting;
        //this.timezone =  // not used; ca.rmen.sunrisesunset obtains the timezone from Calendar obj
    }

    @Override
    public void init(WidgetSettings.Location location, TimeZone timezone)
    {
        this.location = location;
        //this.timezone =  // not used; ca.rmen.sunrisesunset obtains the timezone from Calendar obj
    }

    @Override
    public String name()
    {
        return NAME;
    }

    @Override
    public Calendar getCivilSunriseCalendarForDate( Calendar date )
    {
        Calendar[] civilTwilight = SunriseSunset.getCivilTwilight(date, location.getLatitudeAsDouble(), location.getLongitudeAsDouble());
        if (civilTwilight == null)
            return null;
        else return civilTwilight[0];
    }

    @Override
    public Calendar getNauticalSunriseCalendarForDate( Calendar date )
    {
        Calendar[] nauticalTwilight = SunriseSunset.getNauticalTwilight(date, location.getLatitudeAsDouble(), location.getLongitudeAsDouble());
        if (nauticalTwilight == null)
            return null;
        else return nauticalTwilight[0];
    }

    @Override
    public Calendar getAstronomicalSunriseCalendarForDate( Calendar date )
    {
        Calendar[] astroTwilight = SunriseSunset.getAstronomicalTwilight(date, location.getLatitudeAsDouble(), location.getLongitudeAsDouble());
        if (astroTwilight == null)
            return null;
        else return astroTwilight[0];
    }

    @Override
    public Calendar getOfficialSunriseCalendarForDate( Calendar date )
    {
        Calendar[] riseset = SunriseSunset.getSunriseSunset(date, location.getLatitudeAsDouble(), location.getLongitudeAsDouble());
        if (riseset == null)
            return null;
        else return riseset[0];
    }

    @Override
    public Calendar getSolarNoonCalendarForDate(Calendar date)
    {
        //noinspection UnnecessaryLocalVariable
        Calendar noon = SunriseSunset.getSolarNoon(date, location.getLatitudeAsDouble(), location.getLongitudeAsDouble());
        return noon;
    }

    @Override
    public Calendar getCivilSunsetCalendarForDate( Calendar date )
    {
        Calendar[] civilTwilight = SunriseSunset.getCivilTwilight(date, location.getLatitudeAsDouble(), location.getLongitudeAsDouble());
        if (civilTwilight == null)
            return null;
        else return civilTwilight[1];
    }

    @Override
    public Calendar getNauticalSunsetCalendarForDate( Calendar date )
    {
        Calendar[] nauticalTwilight = SunriseSunset.getNauticalTwilight(date, location.getLatitudeAsDouble(), location.getLongitudeAsDouble());
        if (nauticalTwilight == null)
            return null;
        else return nauticalTwilight[1];
    }

    @Override
    public Calendar getAstronomicalSunsetCalendarForDate( Calendar date )
    {
        Calendar[] astroTwilight = SunriseSunset.getAstronomicalTwilight(date, location.getLatitudeAsDouble(), location.getLongitudeAsDouble());
        if (astroTwilight == null)
            return null;
        else return astroTwilight[1];
    }

    @Override
    public Calendar[] getMorningBlueHourForDate(Calendar date)
    {
        return null;
    }

    @Override
    public Calendar[] getEveningBlueHourForDate(Calendar date)
    {
        return null;
    }

    @Override
    public Calendar[] getMorningGoldenHourForDate(Calendar date)
    {
        return null;
    }

    @Override
    public Calendar[] getEveningGoldenHourForDate(Calendar date)
    {
        return null;
    }

    @Override
    public boolean isDay(Calendar dateTime)
    {
        return SunriseSunset.isDay(dateTime, location.getLatitudeAsDouble(), location.getLongitudeAsDouble());
    }

    @Override
    public Calendar getOfficialSunsetCalendarForDate( Calendar date )
    {
        Calendar[] riseset = SunriseSunset.getSunriseSunset(date, location.getLatitudeAsDouble(), location.getLongitudeAsDouble());
        if (riseset == null)
            return null;
        else return riseset[1];
    }

    public static SuntimesCalculatorDescriptor getDescriptor()
    {
        return new SuntimesCalculatorDescriptor(SunriseSunsetSuntimesCalculator.NAME, SunriseSunsetSuntimesCalculator.NAME, SunriseSunsetSuntimesCalculator.REF);
    }
}

