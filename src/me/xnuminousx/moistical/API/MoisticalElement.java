package me.xnuminousx.moistical.API;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.Element.SubElement;
import com.projectkorra.projectkorra.ProjectKorra;

public class MoisticalElement {
	public static final Element MOISTICAL = new Element("Moistical", null, ProjectKorra.plugin);
	public static final SubElement FESTIVE = new SubElement("Festive", MoisticalElement.MOISTICAL, null, ProjectKorra.plugin);
}
