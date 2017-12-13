package test.dmisb.apps65.data.network.adapter;

import android.text.TextUtils;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.text.ParsePosition;
import java.util.Date;

import test.dmisb.apps65.utils.FormatUtils;

@SuppressWarnings("unused")
public class DateAdapter {
    @FromJson
    Date dateFromJson(String value) {
        if (!TextUtils.isEmpty(value)) {
            if (value.charAt(2) == '-') {
                return FormatUtils.getResBackFormat().parse(value, new ParsePosition(0));
            } else {
                return FormatUtils.getResFormat().parse(value, new ParsePosition(0));
            }
        }
        return null;
    }

    @ToJson
    String dateToJson(Date date) {
        return FormatUtils.resFormatDate(date);
    }
}
