package net.sf.anathema.framework.environment.dependencies;

import net.sf.anathema.platform.dependencies.AnnotationFinder;
import net.sf.anathema.platform.dependencies.InterfaceFinder;
import net.sf.anathema.platform.dependencies.ReflectionObjectFactory;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Iterables;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReflectionObjectFactoryTest
{
	private AnnotationFinder annotationFinder = mock (AnnotationFinder.class);
	private InterfaceFinder interfaceFinder = mock (InterfaceFinder.class);
	private ReflectionObjectFactory factory = new ReflectionObjectFactory (annotationFinder, interfaceFinder);
	private Set<Class<?>> annotatedClasses = new LinkedHashSet<> ();
	
	@Before
	public void configureAnnotationFinder () throws Exception
	{
		when (annotationFinder.getTypesAnnotatedWith (DummyAnnotation.class)).thenReturn (annotatedClasses);
	}
	
	@Test
	public void findsNoClassesWhenAnnotationUnknown ()
	{
		Collection<Object> result = factory.instantiateAll (DummyAnnotation.class);
		assertThat (result, hasSize (0));
	}
	
	@Test
	public void instantiatesClassWhenFinderDiscoversThem ()
	{
		annotatedClasses.add (AnnotatedDummy.class);
		Collection<Object> result = factory.instantiateAll (DummyAnnotation.class);
		assertThat (result, contains (is (instanceOf (AnnotatedDummy.class))));
	}
	
	@Test
	public void usesConstructorArguments ()
	{
		annotatedClasses.add (AnnotatedDummy.class);
		Collection<AnnotatedDummy> result = factory.instantiateAll (DummyAnnotation.class, "Parameter");
		Iterables.getOnlyElement (result).assertStringConstructorWasUsed ();
	}
	
	@Test
	public void instantiatesAllClassesAnnotated ()
	{
		annotatedClasses.add (AnnotatedDummy.class);
		annotatedClasses.add (AnotherAnnotatedDummy.class);
		Collection<AnnotatedDummy> result = factory.instantiateAll (DummyAnnotation.class);
		assertThat (result, hasSize (2));
	}
	
	@Test
	public void instantiatesAllClassesImplementingInterface ()
	{
		Set<Class<? extends DummyInterface>> implementingClasses = new LinkedHashSet<> ();
		implementingClasses.add (AnnotatedDummy.class);
		implementingClasses.add (AnotherAnnotatedDummy.class);
		when (interfaceFinder.findAll (DummyInterface.class)).thenReturn (implementingClasses);
		Collection<DummyInterface> result = factory.instantiateAllImplementers (DummyInterface.class);
		assertThat (result, hasSize (2));
	}
	
	@Test
	public void ignoresBlackListedImplementors ()
	{
		Set<Class<? extends DummyInterface>> implementingClasses = new LinkedHashSet<> ();
		implementingClasses.add (IgnoredDummy.class);
		when (interfaceFinder.findAll (DummyInterface.class)).thenReturn (implementingClasses);
		Collection<DummyInterface> result = factory.instantiateAllImplementers (DummyInterface.class);
		assertThat (result, hasSize (0));
	}
	
	@Test
	public void ignoresAbstractImplementors ()
	{
		Set<Class<? extends DummyInterface>> implementingClasses = new LinkedHashSet<> ();
		implementingClasses.add (AbstractDummy.class);
		when (interfaceFinder.findAll (DummyInterface.class)).thenReturn (implementingClasses);
		Collection<DummyInterface> result = factory.instantiateAllImplementers (DummyInterface.class);
		assertThat (result, hasSize (0));
	}
	
	@Test
	public void ordersInstancesByWeight ()
	{
		annotatedClasses.add (AnnotatedDummy.class);
		annotatedClasses.add (AnotherAnnotatedDummy.class);
		Collection<AnnotatedDummy> result = factory.instantiateOrdered (DummyAnnotation.class);
		assertThat (Iterables.get (result, 0), is ( (Matcher) instanceOf (AnotherAnnotatedDummy.class)));
		assertThat (Iterables.get (result, 1), is ( (Matcher) instanceOf (AnnotatedDummy.class)));
	}
}
