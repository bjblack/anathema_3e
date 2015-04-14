package net.sf.anathema.hero.traits;

import net.sf.anathema.hero.traits.model.TraitType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class TraitTypeList implements Iterable<TraitType> {


  private final TraitTypeFinder finder = new TraitTypeFinder();
  private final List<TraitType> types = new ArrayList<>();

  public void add(String typeId) {
    TraitType type = finder.getTrait(typeId);
    if (type == TraitTypeFinder.NONE_FOUND) {
      return;
    }
    types.add(type);
  }

  public void add(TraitType type) {
    types.add(type);
  }

  public boolean isEmpty() {
    return types.isEmpty();
  }

  public boolean contains(TraitType traitType) {
    return types.contains(traitType);
  }

  @Override
  public Iterator<TraitType> iterator() {
    return types.iterator();
  }

  @Override
  public void forEach(Consumer<? super TraitType> action) {
    types.forEach(action);
  }

  @Override
  public Spliterator<TraitType> spliterator() {
    return types.spliterator();
  }

  public void add(TraitType... allTypes) {
    Collections.addAll(types, allTypes);
  }
}