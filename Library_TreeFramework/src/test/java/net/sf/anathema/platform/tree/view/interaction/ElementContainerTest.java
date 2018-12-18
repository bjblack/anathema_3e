package net.sf.anathema.platform.tree.view.interaction;

import net.sf.anathema.library.number.Coordinate;
import net.sf.anathema.platform.tree.view.draw.GraphicsElement;
import net.sf.anathema.platform.tree.view.draw.InteractiveGraphicsElement;
import net.sf.anathema.platform.tree.view.draw.PolygonMother;

import org.junit.Test;

import com.google.common.collect.Iterables;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ElementContainerTest
{
	private ElementContainer container = new ElementContainer ();
	
	@Test
	public void actsOnPolygonAtPoint () throws Exception
	{
		InteractiveGraphicsElement polygon = PolygonMother.squareAtOriginWithLength2 ();
		container.add (polygon);
		Closure closure = mock (Closure.class);
		container.onElementAtPoint (new Coordinate (1, 1)).perform (closure);
		verify (closure).execute (polygon);
	}
	
	@Test
	public void performsDefaultOperationOtherwise () throws Exception
	{
		InteractiveGraphicsElement polygon = PolygonMother.squareAtOriginWithLength2 ();
		container.add (polygon);
		Closure closure = mock (Closure.class);
		Runnable defaultAction = mock (Runnable.class);
		container.onElementAtPoint (new Coordinate (3, 3)).perform (closure).orFallBackTo (defaultAction);
		verify (defaultAction).run ();
	}
	
	@Test
	public void iteratesOverInteractiveElements () throws Exception
	{
		InteractiveGraphicsElement element = mock (InteractiveGraphicsElement.class);
		container.add (element);
		assertThat (Iterables.contains (container, element), is (true));
	}
	
	@Test
	public void iteratesOverInertElements () throws Exception
	{
		GraphicsElement element = mock (GraphicsElement.class);
		container.add (element);
		assertThat (Iterables.contains (container, element), is (true));
	}
	
	@Test
	public void forgetsElementsOnClear () throws Exception
	{
		GraphicsElement element = mock (GraphicsElement.class);
		container.add (element);
		container.clear ();
		assertThat (Iterables.isEmpty (container), is (true));
	}
	
	@Test
	public void forgetsInteractiveElementsOnClear () throws Exception
	{
		InteractiveGraphicsElement element = PolygonMother.squareAtOriginWithLength2 ();
		container.add (element);
		container.clear ();
		container.onElementAtPoint (new Coordinate (1, 1)).perform (new Closure ()
		{
			@Override
			public void execute (InteractiveGraphicsElement polygon)
			{
				fail ();
			}
		}
		);
	}
}
