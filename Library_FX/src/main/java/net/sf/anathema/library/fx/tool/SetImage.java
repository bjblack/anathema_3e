package net.sf.anathema.library.fx.tool;

import javafx.scene.image.ImageView;

public class SetImage implements ImageClosure
{
	private ImageView imageView;
	
	public SetImage (ImageView imageView)
	{
		this.imageView = imageView;
	}
	
	@Override
	public void run (ImageContainer image)
	{
		image.displayIn (imageView);
	}
}
