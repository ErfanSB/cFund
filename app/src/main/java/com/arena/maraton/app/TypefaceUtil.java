package com.arena.maraton.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

class TypefaceUtil {

	@SuppressLint("LongLogTag")
	static void overrideFont(Context context) {
		try {
			final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(),
					"fonts/shabnam.ttf");

			final Field defaultFontTypefaceField = Typeface.class.getDeclaredField("SERIF");
			defaultFontTypefaceField.setAccessible(true);
			defaultFontTypefaceField.set(null, customFontTypeface);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}