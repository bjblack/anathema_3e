package net.sf.anathema.equipment.presentation;

import java.util.Comparator;

public class EquipmentTemplateNameComparator implements Comparator<String>
{
	@Override
	public int compare (String o1, String o2)
	{
		return o1.compareToIgnoreCase (o2);
	}
}
