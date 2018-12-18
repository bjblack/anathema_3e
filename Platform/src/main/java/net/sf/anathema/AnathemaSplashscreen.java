package net.sf.anathema;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Paint;
import java.awt.SplashScreen;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class AnathemaSplashscreen implements ISplashscreen
{
	private final static Rectangle2D.Double textAreaRectangle = new Rectangle2D.Double (93, 318, 454, 19);
	private final FontRenderContext renderContext = new FontRenderContext (null, true, false);
	private Graphics2D graphics;
	private Paint textAreaGradient;
	private Font font;
	
	public AnathemaSplashscreen ()
	{
		if (!hasSplashscreen ())
		{
			return;
		}
		Color startColor = new Color (12, 28, 59);
		Color endColor = new Color (10, 21, 46);
		Rectangle2D bounds = textAreaRectangle.getBounds2D ();
		this.textAreaGradient = new GradientPaint (
		 (int) bounds.getMinX (),
		 (int) bounds.getMinY (),
		startColor,
		 (int) bounds.getMaxX (),
		 (int) bounds.getMaxY (),
		endColor);
		this.graphics = SplashScreen.getSplashScreen ().createGraphics ();
		this.font = graphics.getFont ().deriveFont (Font.BOLD);
	}
	
	@Override
	public void displayStatusMessage (String message)
	{
		if (isSplashscreenVisible ())
		{
			return;
		}
		resetTextArea ();
		TextLayout layout = new TextLayout (message, font, renderContext);
		layout.draw (graphics, 105, 333);
		SplashScreen.getSplashScreen ().update ();
	}
	
	private void resetTextArea ()
	{
		Paint oldPaint = graphics.getPaint ();
		graphics.setPaint (textAreaGradient);
		graphics.fill (textAreaRectangle);
		graphics.setPaint (oldPaint);
	}
	
	@Override
	public void displayVersion (String string)
	{
		if (isSplashscreenVisible ())
		{
			return;
		}
		TextLayout layout = new TextLayout (string, font.deriveFont (font.getSize2D () + 2), renderContext);
		layout.draw (graphics, 445, 91);
		SplashScreen.getSplashScreen ().update ();
	}
	
	private boolean isSplashscreenVisible ()
	{
		return !hasSplashscreen () || !SplashScreen.getSplashScreen ().isVisible ();
	}
	
	private boolean hasSplashscreen ()
	{
		return !GraphicsEnvironment.isHeadless () && SplashScreen.getSplashScreen () != null;
	}
}
