package net.sf.anathema.hero.application.perspective.model;

import net.sf.anathema.hero.application.persistence.HeroMainFileDto;
import net.sf.anathema.platform.repository.printname.ReferenceBuilder;
import net.sf.anathema.platform.repository.printname.SimpleRepositoryId;

import com.google.gson.Gson;

public class CharacterReferenceBuilder implements ReferenceBuilder<CharacterReference>
{
	@Override
	public CharacterReference create (String itemSaveData)
	{
		HeroMainFileDto dto = new Gson ().fromJson (itemSaveData, HeroMainFileDto.class);
		return new CharacterReference (new SimpleRepositoryId (dto.repositoryId), dto.printName);
	}
}
