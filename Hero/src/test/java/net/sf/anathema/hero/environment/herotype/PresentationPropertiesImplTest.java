package net.sf.anathema.hero.environment.herotype;

import net.sf.anathema.hero.dummy.DummyHeroSplat;
import net.sf.anathema.hero.dummy.DummyMundaneHeroType;
import net.sf.anathema.hero.environment.template.SplatTypeImpl;
import net.sf.anathema.library.identifier.SimpleIdentifier;

import org.junit.Assert;
import org.junit.Test;

public class PresentationPropertiesImplTest
{
	@Test
	public void subtypedAutoGeneratedNewResource () throws Exception
	{
		PresentationPropertiesImpl template = setTemplateType (new SplatTypeImpl (new DummyMundaneHeroType (), new SimpleIdentifier ("HeroicSubtype")));
		assertExpectedResource (template, "CharacterGenerator.Templates.Dummy.HeroicSubtype");
	}
	
	private PresentationPropertiesImpl setTemplateType (SplatTypeImpl templateType)
	{
		DummyHeroSplat dummyCharacterTemplate = new DummyHeroSplat ();
		dummyCharacterTemplate.setCharacterType (templateType.getHeroType ());
		dummyCharacterTemplate.setSubType (templateType.getSubType ().getId ());
		return new PresentationPropertiesImpl (dummyCharacterTemplate);
	}
	
	private void assertExpectedResource (PresentationPropertiesImpl template, String expected)
	{
		Assert.assertEquals (expected, template.getNewActionResource ());
	}
}
