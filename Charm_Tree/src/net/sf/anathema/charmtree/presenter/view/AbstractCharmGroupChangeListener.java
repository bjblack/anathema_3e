package net.sf.anathema.charmtree.presenter.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.Set;

import net.disy.commons.core.util.Ensure;
import net.sf.anathema.character.generic.framework.magic.CharmGraphNodeBuilder;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.charms.ICharmGroup;
import net.sf.anathema.character.generic.rules.IExaltedEdition;
import net.sf.anathema.character.generic.template.ITemplateRegistry;
import net.sf.anathema.lib.collection.ListOrderedSet;
import net.sf.anathema.lib.util.IIdentificate;
import net.sf.anathema.platform.svgtree.document.CascadeDocumentFactory;
import net.sf.anathema.platform.svgtree.document.ITreePresentationProperties;
import net.sf.anathema.platform.svgtree.graph.nodes.IRegularNode;

import org.apache.batik.dom.svg12.SVG12DOMImplementation;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.DOMWriter;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.svg.SVGDocument;

public abstract class AbstractCharmGroupChangeListener implements ICharmGroupChangeListener {

  private final CascadeDocumentFactory provider = new CascadeDocumentFactory();
  private final ICharmTreeViewProperties viewProperties;
  private final ITemplateRegistry templateRegistry;
  private final ICharmTreeView charmTreeView;
  private final ICharmGroupArbitrator arbitrator;
  private ICharmGroup currentGroup;

  public AbstractCharmGroupChangeListener(
      ICharmTreeView charmTreeView,
      ICharmTreeViewProperties viewProperties,
      ITemplateRegistry templateRegistry,
      ICharmGroupArbitrator arbitrator) {
    this.charmTreeView = charmTreeView;
    Ensure.ensureNotNull("View Properties must not be null", viewProperties); //$NON-NLS-1$
    this.viewProperties = viewProperties;
    this.templateRegistry = templateRegistry;
    this.arbitrator = arbitrator;
  }

  public final void valueChanged(Object cascade, Object type) {
    charmTreeView.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    try {
      loadCharmTree((ICharmGroup) cascade, (IIdentificate) type);
      return;
    }
    catch (DocumentException e) {
      e.printStackTrace();
    }
    finally {
      charmTreeView.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }

  private Set<ICharm> getDisplayCharms(ICharmGroup charmGroup) {
    ICharm[] allCharms = arbitrator.getCharms(charmGroup);
    Set<ICharm> charmsToDisplay = new ListOrderedSet<ICharm>();
    for (ICharm charm : allCharms) {
      charmsToDisplay.add(charm);
      for (ICharm prerequisite : charm.getRenderingPrerequisiteCharms()) {
        if (charmGroup.getId().equals(prerequisite.getGroupId())) {
          charmsToDisplay.add(prerequisite);
        }
      }
    }
    return charmsToDisplay;
  }

  protected abstract IExaltedEdition getEdition();

  private void loadCharmTree(ICharmGroup charmGroup, IIdentificate type) throws DocumentException {
    currentGroup = charmGroup;
    modifyCharmVisuals(type);
    if (charmGroup == null) {
      charmTreeView.loadCascade(null);
    }
    else {
      Set<ICharm> displayCharms = getDisplayCharms(charmGroup);
      ITreePresentationProperties presentationProperties = templateRegistry.getDefaultTemplate(
          charmGroup.getCharacterType(),
          getEdition()).getPresentationProperties().getCharmPresentationProperties();
      Dimension dimension = presentationProperties.getNodeDimension();
      viewProperties.setDimension(dimension);
      charmTreeView.setProperties(viewProperties);
      ICharm[] charms = displayCharms.toArray(new ICharm[displayCharms.size()]);
      IRegularNode[] nodes = CharmGraphNodeBuilder.createNodesFromCharms(Arrays.asList(charms));
      Document dom4jDocument = provider.createCascadeDocument(nodes, presentationProperties);
      DOMImplementation implementation = SVG12DOMImplementation.getDOMImplementation();
      SVGDocument svgDocument = (SVGDocument) new DOMWriter().write(dom4jDocument, implementation);
      charmTreeView.loadCascade(svgDocument);
    }
  }

  protected final ICharmGroup getCurrentGroup() {
    return currentGroup;
  }

  protected abstract void modifyCharmVisuals(IIdentificate type);

  public abstract void updateColors();
}