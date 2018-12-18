package net.sf.anathema.equipment.database.gson;

import net.sf.anathema.equipment.stats.IEquipmentStats;
import net.sf.anathema.equipment.stats.impl.ArmourStats;
import net.sf.anathema.equipment.stats.impl.ArtifactStats;
import net.sf.anathema.equipment.stats.impl.WeaponStats;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class StatsAdapter implements JsonDeserializer<IEquipmentStats>, JsonSerializer<IEquipmentStats>
{
	private final BiMap<String, Class> classesToTypes = HashBiMap.create ();
	
	public StatsAdapter ()
	{
		classesToTypes.put ("Weapon", WeaponStats.class);
		classesToTypes.put ("Artifact", ArtifactStats.class);
		classesToTypes.put ("Armour", ArmourStats.class);
	}
	
	@Override
	public IEquipmentStats deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject object = (JsonObject) json;
		JsonElement type = object.get ("type");
		String typeAsString = type.getAsString ();
		Class classToBuild = classesToTypes.get (typeAsString);
		return context.deserialize (json, classToBuild);
	}
	
	@Override
	public JsonElement serialize (IEquipmentStats src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonElement element = context.serialize (src);
		JsonObject object = (JsonObject) element;
		BiMap<Class, String> typesToClasses = classesToTypes.inverse ();
		String type = typesToClasses.get (src.getClass ());
		setType (object, type);
		return element;
	}
	
	private void setType (JsonObject object, String type)
	{
		object.addProperty ("type", type);
	}
}
