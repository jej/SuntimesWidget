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

package com.forrestguice.suntimeswidget.layouts;

import android.content.Context;
import android.widget.RemoteViews;

import com.forrestguice.suntimeswidget.R;
import com.forrestguice.suntimeswidget.calculator.SuntimesRiseSetData;
import com.forrestguice.suntimeswidget.themes.SuntimesTheme;
import com.forrestguice.suntimeswidget.SuntimesUtils.TimeDisplayText;

/**
 * A 1x1 layout that displays only the sunrise time.
 */
public class SuntimesLayout_1x1_1 extends SuntimesLayout
{
    public SuntimesLayout_1x1_1()
    {
        super();
    }

    public SuntimesLayout_1x1_1( int layoutID )
    {
        this.layoutID = layoutID;
    }

    @Override
    public void initLayoutID()
    {
        this.layoutID = R.layout.layout_widget_1x1_1;
    }


    @Override
    public void updateViews(Context context, int appWidgetId, RemoteViews views, SuntimesRiseSetData data)
    {
        super.updateViews(context, appWidgetId, views, data);

        // update sunrise time
        TimeDisplayText sunriseString = utils.calendarTimeShortDisplayString(context, data.sunriseCalendarToday());
        views.setTextViewText(R.id.text_time_sunrise, sunriseString.getValue());
        views.setTextViewText(R.id.text_time_sunrise_suffix, sunriseString.getSuffix());
    }

    @Override
    public void themeViews(Context context, RemoteViews views, SuntimesTheme theme)
    {
        super.themeViews(context, views, theme);

        // theme sunrise text
        int sunriseColor = theme.getSunriseTextColor();
        int suffixColor = theme.getTimeSuffixColor();
        views.setTextColor(R.id.text_time_sunrise_suffix, suffixColor);
        views.setTextColor(R.id.text_time_sunrise, sunriseColor);
    }
}
