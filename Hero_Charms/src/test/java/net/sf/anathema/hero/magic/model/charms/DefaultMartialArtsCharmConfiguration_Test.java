package net.sf.anathema.hero.magic.model.charms;

import net.sf.anathema.character.main.advance.ExperiencePointConfiguration;
import net.sf.anathema.character.main.dummy.DummyCharm;
import net.sf.anathema.character.main.magic.model.charm.Charm;
import net.sf.anathema.character.main.magic.model.charm.CharmAttributeList;
import net.sf.anathema.character.main.magic.model.charms.ILearningCharmGroup;
import net.sf.anathema.character.main.testing.dummy.magic.DummyCharmsModel;
import net.sf.anathema.hero.charms.model.DefaultMartialArtsCharmConfiguration;
import net.sf.anathema.hero.experience.ExperienceModel;
import net.sf.anathema.hero.magic.MagicCollection;
import net.sf.anathema.lib.control.ChangeListener;
import net.sf.anathema.lib.util.Identifier;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class DefaultMartialArtsCharmConfiguration_Test {

  @Test
  public void testAlternativesDontBlockCompletion() throws Exception {
    MagicCollection collection = Mockito.mock(MagicCollection.class);
    ILearningCharmGroup group = Mockito.mock(ILearningCharmGroup.class);
    expectCoreCharmsCall(group);
    expectCoreCharmsCall(group);
    DummyCharmsModel dummyConfig = new DummyCharmsModel();
    dummyConfig.setGroups(group);
    ExperienceModel experienceModel = new ExperienceModel() {
      public boolean experienced;

      @Override
      public ExperiencePointConfiguration getExperiencePoints() {
        throw new UnsupportedOperationException();
      }

      @Override
      public boolean isExperienced() {
        return experienced;
      }

      @Override
      public void setExperienced(boolean experienced) {
        this.experienced = experienced;
      }

      @Override
      public void addStateChangeListener(ChangeListener listener) {
        // nothing to do
      }
    };
    DefaultMartialArtsCharmConfiguration configuration = new DefaultMartialArtsCharmConfiguration(dummyConfig, collection, experienceModel);
    boolean celestialMartialArtsGroupCompleted = configuration.isAnyCelestialStyleCompleted();
    Assert.assertTrue(celestialMartialArtsGroupCompleted);
  }

  private void expectCoreCharmsCall(ILearningCharmGroup group) {
    Mockito.when(group.getCoreCharms()).thenReturn(new Charm[]{new DummyCharm() {
      @Override
      public boolean isBlockedByAlternative(MagicCollection magicCollection) {
        return true;
      }

      @Override
      public boolean hasAttribute(Identifier attribute) {
        return !attribute.equals(CharmAttributeList.NO_STYLE_ATTRIBUTE);
      }
    }});
  }
}