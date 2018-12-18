package net.sf.anathema.hero.application.models;

import net.sf.anathema.hero.application.creation.models.ModelFactoryMap;
import net.sf.anathema.hero.application.dummy.DummyFactoryCollector;
import net.sf.anathema.hero.dummy.DummyModelFactory;
import net.sf.anathema.hero.individual.model.ModelTreeEntry;
import net.sf.anathema.library.identifier.SimpleIdentifier;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

public class ModelFactoryMapTest
{
	private final DummyFactoryCollector collector = new DummyFactoryCollector ();
	private ModelFactoryMap map;
	
	@Before
	public void setUp () throws Exception
	{
		createFactory ("A");
		createFactory ("B");
		map = new ModelFactoryMap (collector);
	}
	
	private void createFactory (String a)
	{
		DummyModelFactory factory = new DummyModelFactory (new SimpleIdentifier (a));
		collector.addFactory (factory);
	}
	
	@Test
	public void hasRegisteredModel () throws Exception
	{
		map.assertContainsRequiredModel ("A");
	}
	
	@Test (expected = IllegalStateException.class)
	public void throwsExceptionWhenUnknownModelIsAssertedAsRequired () throws Exception
	{
		map.assertContainsRequiredModel ("Unknown");
	}
	
	@SuppressWarnings ("unchecked")
	@Test
	public void iteratesOverAllEntries () throws Exception
	{
		assertThat (map, hasItems (factoryWithId ("A"), factoryWithId ("B")));
	}
	
	private TypeSafeMatcher<ModelTreeEntry> factoryWithId (final String expectedId)
	{
		return new TypeSafeMatcher<ModelTreeEntry> ()
		{
			@Override
			protected boolean matchesSafely (ModelTreeEntry item)
			{
				return item.getModelId ().getId ().equals (expectedId);
			}
			
			@Override
			public void describeTo (Description description)
			{
				description.appendText ("a model with id").appendValue (expectedId);
			}
		}
		;
	}
}
