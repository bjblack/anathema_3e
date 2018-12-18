package net.sf.anathema.hero.charms.compiler.json;

import net.sf.anathema.magic.data.Charm;
import net.sf.anathema.magic.data.reference.CharmName;
import net.sf.anathema.magic.data.reference.TreeName;
import net.sf.anathema.magic.template.CharmListTemplate;
import net.sf.anathema.magic.template.CharmTemplate;
import net.sf.anathema.hero.charms.compiler.CharmCache;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SingleCharmCharmCacheBuilderTest
{
	public static final String CHARM_NAME = "AnyName";
	private CharmCacheBuilderImpl builder = new CharmCacheBuilderImpl ();
	private CharmListTemplate template = new CharmListTemplate ();
	private CharmTemplate charmTemplate = new CharmTemplate ();
	
	@Before
	public void connectTemplates () throws Exception
	{
		template.charms.put (CHARM_NAME, charmTemplate);
	}
	
	@Test
	public void createsCharmWithTreeName () throws Exception
	{
		template.tree = "TheTree";
		template.category = "TheCategory";
		builder.addTemplate (template);
		assertThat (compileCharm ().getTreeReference ().name, is (new TreeName ("TheTree")));
	}
	
	private Charm compileCharm ()
	{
		CharmCache cache = builder.createCache ();
		return cache.getCharmById (new CharmName (CHARM_NAME));
	}
}
