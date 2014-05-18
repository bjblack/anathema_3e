package net.sf.anathema.character.main.xml.trait.caste;

import net.sf.anathema.character.main.traits.groups.TraitTypeList;
import net.sf.anathema.character.main.xml.trait.GenericRestrictedTraitTemplate;
import net.sf.anathema.character.main.xml.trait.GenericTraitTemplate;
import net.sf.anathema.character.main.xml.trait.GenericTraitTemplateParser;
import net.sf.anathema.character.main.xml.trait.allocation.AllocationMinimumRestriction;
import net.sf.anathema.character.main.xml.trait.allocation.AllocationMinimumTraitTemplateParser;
import net.sf.anathema.character.main.xml.trait.alternate.AlternateMinimumTraitTemplateParser;
import net.sf.anathema.lib.exception.PersistenceException;
import net.sf.anathema.lib.xml.ElementUtilities;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class CasteMinimumTraitTemplateParser {
  private static final String ATTRIB_CASTE = "caste";
  private static final String TAG_SPECIAL_TRAIT = "specialTrait";
  private static final String TAG_ALLOCATION_MINIMUM_TRAITS = "allocationMinimumTraits";
  private static final String TAG_ALTERNATE_MINMUM_TRAITS = "alternateMinimumTraits";
  private static final String TAG_FREEBIE = "isFreebie";
  private final TraitTypeList type;
  private boolean isFreebie;
  private String caste;

  public CasteMinimumTraitTemplateParser(TraitTypeList type) {
    this.type = type;
  }

  public GenericRestrictedTraitTemplate[] parseCasteMinimumTraits(Element element, List<AllocationMinimumRestriction> list) {
    GenericRestrictedTraitTemplate[] templates;
    List<GenericRestrictedTraitTemplate> limits = new ArrayList<>();
    try {
      caste = element.attributeValue(ATTRIB_CASTE);
      isFreebie = ElementUtilities.getBooleanAttribute(element, TAG_FREEBIE, false);
      parseSpecialTraitTemplates(limits, element);
      parseAllocationMinimumTraitTemplates(limits, element, list);
      parseAlternateMinimumTraitTemplates(limits, element);

      templates = new GenericRestrictedTraitTemplate[limits.size()];
      limits.toArray(templates);
    } catch (PersistenceException e) {
      return new GenericRestrictedTraitTemplate[0];
    }
    return templates;
  }

  private void parseSpecialTraitTemplates(List<GenericRestrictedTraitTemplate> pool, Element element) throws PersistenceException {
    for (Element specialTraitElement : ElementUtilities.elements(element, TAG_SPECIAL_TRAIT)) {
      GenericTraitTemplate specialTraitTemplate = GenericTraitTemplateParser.parseTraitTemplateSoft(specialTraitElement);
      String traitTypeId = ElementUtilities.getRequiredAttrib(specialTraitElement, "id");
      pool.add(new GenericRestrictedTraitTemplate(specialTraitTemplate,
              new CasteMinimumRestriction(caste, specialTraitTemplate.getMinimumValue(null), isFreebie), type.getById(traitTypeId)));
      specialTraitTemplate.setMinimumValue(0);
    }
  }

  private void parseAlternateMinimumTraitTemplates(List<GenericRestrictedTraitTemplate> pool, Element element) throws PersistenceException {
    AlternateMinimumTraitTemplateParser parser = new AlternateMinimumTraitTemplateParser(type);
    for (Element specialTraitElement : ElementUtilities.elements(element, TAG_ALTERNATE_MINMUM_TRAITS)) {
      for (GenericRestrictedTraitTemplate template : parser.parseAlternateMinimumTraitsSoft(specialTraitElement)) {
        pool.add(new GenericRestrictedTraitTemplate(template.getTemplate(),
                new CasteMinimumRestriction(caste, template.getRestrictions().get(0), isFreebie), template.getTraitType()));
      }
    }
  }

  private void parseAllocationMinimumTraitTemplates(List<GenericRestrictedTraitTemplate> pool, Element element,
                                                    List<AllocationMinimumRestriction> list) throws PersistenceException {
    AllocationMinimumTraitTemplateParser parser = new AllocationMinimumTraitTemplateParser(type);
    for (Element specialTraitElement : ElementUtilities.elements(element, TAG_ALLOCATION_MINIMUM_TRAITS)) {
      for (GenericRestrictedTraitTemplate template : parser.parseAllocationMinimumTraits(specialTraitElement, list)) {
        pool.add(new GenericRestrictedTraitTemplate(template.getTemplate(),
                new CasteMinimumRestriction(caste, template.getRestrictions().get(0), isFreebie), template.getTraitType()));
      }
    }
  }
}