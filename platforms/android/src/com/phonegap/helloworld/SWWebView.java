package com.phonegap.helloworld;

import org.apache.cordova.CordovaWebView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SWWebView extends CordovaWebView
{

	public SWWebView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public SWWebView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public SWWebView(Context context)
	{
		super(context);
	}

	private long		lastDownEventTime		= -1;
	private long		lastUpEventTime			= -1;

	/**
	 * If user clicks twice in less than minTimeBetweenTwoClicks ms then ignore click
	 * This value can be increased if you don't want the user to be able to quickly click (500
	 * should prevent all double click, on all devices)
	 */
	public static int	minTimeBetweenTwoClicks	= 500;	//Set to 250 to increase responsiveness, but user might still be able to trigger double click to zoom on some devices

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		long eventTime = ev.getEventTime();
		switch (ev.getAction())
		{
		case MotionEvent.ACTION_DOWN: // Most Samsung devices will trigger zoom on mouse down 
		{
			if (eventTime - lastDownEventTime > minTimeBetweenTwoClicks)
			{
				if (eventTime - lastDownEventTime < 1000)
				{
					// Cancel all touch actions, does not work on most Samsung devices
					ev.setAction(MotionEvent.ACTION_CANCEL);
					super.onTouchEvent(ev);
				}
				ev.setAction(MotionEvent.ACTION_DOWN);
				lastDownEventTime = eventTime;
				return super.onTouchEvent(ev);
			}
			else
			{
				return false;
			}
		}
		case MotionEvent.ACTION_UP: // Other devices will trigger zoom on mouse up
		{
			if (eventTime - lastUpEventTime > minTimeBetweenTwoClicks)
			{
				if (eventTime - lastUpEventTime < 1000)
				{
					// Cancel all touch actions, does not work on most Samsung devices
					ev.setAction(MotionEvent.ACTION_CANCEL);
					super.onTouchEvent(ev);
					ev.setAction(MotionEvent.ACTION_DOWN);
					super.onTouchEvent(ev);
				}
				ev.setAction(MotionEvent.ACTION_UP);
				lastUpEventTime = eventTime;
				return super.onTouchEvent(ev);
			}
			else
			{
				return false;
			}
		}
		}
		return super.onTouchEvent(ev);
	}

}
