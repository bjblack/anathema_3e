package net.sf.anathema.character.impl.module;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.anathema.character.generic.caste.ICasteCollection;
import net.sf.anathema.character.generic.type.CharacterType;
import net.sf.anathema.framework.item.IRepositoryConfiguration;
import net.sf.anathema.framework.repository.access.printname.PrintNameFileAccess;
import net.sf.anathema.framework.view.PrintNameFile;
import net.sf.anathema.lib.registry.IRegistry;
import net.sf.anathema.lib.util.IIdentificate;

import org.apache.commons.io.FileUtils;

public class CharacterPrintNameFileScanner {

  private static final Pattern typePattern = Pattern.compile("<CharacterType.*>(.*?)</CharacterType>"); //$NON-NLS-1$
  private static final Pattern castePattern = Pattern.compile("<Caste type=\"(.*?)\"/>"); //$NON-NLS-1$
  private final Map<PrintNameFile, CharacterType> typesByFile = new HashMap<PrintNameFile, CharacterType>();
  private final Map<PrintNameFile, IIdentificate> castesByFile = new HashMap<PrintNameFile, IIdentificate>();
  private final IRegistry<CharacterType, ICasteCollection> registry;
  private final String mainFileName;

  public CharacterPrintNameFileScanner(
      IRegistry<CharacterType, ICasteCollection> registry,
      IRepositoryConfiguration repositoryConfiguration) {
    this.registry = registry;
    this.mainFileName = repositoryConfiguration.getMainFileName();
  }

  private void scan(PrintNameFile file) throws IOException {
    File scanFile;
    if (mainFileName == null) {
      scanFile = file.getFile();
    }
    else {
      scanFile = new File(file.getFile(), mainFileName);
    }
    String content = FileUtils.readFileToString(scanFile, PrintNameFileAccess.ENCODING);
    Matcher typeMatcher = typePattern.matcher(content);
    CharacterType characterType;
    typeMatcher.find();
    characterType = CharacterType.getById(typeMatcher.group(1));
    typesByFile.put(file, characterType);
    Matcher casteMatcher = castePattern.matcher(content);
    if (!casteMatcher.find()) {
      castesByFile.put(file, null);
      return;
    }
    IIdentificate casteType = registry.get(characterType).getById(casteMatcher.group(1));
    castesByFile.put(file, casteType);
  }

  public CharacterType getCharacterType(PrintNameFile file) {
    CharacterType characterType = typesByFile.get(file);
    if (characterType != null) {
      return characterType;
    }
    try {
      scan(file);
      return typesByFile.get(file);
    }
    catch (IOException e) {
      return null;
    }
  }

  public IIdentificate getCasteType(PrintNameFile file) {
    IIdentificate caste = castesByFile.get(file);
    if (caste != null) {
      return caste;
    }
    try {
      scan(file);
      return castesByFile.get(file);
    }
    catch (IOException e) {
      return null;
    }
  }
}