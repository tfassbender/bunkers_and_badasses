package net.jfabricationgames.bunkers_and_badasses.game_board;

import java.io.Serializable;
import java.util.List;

public class Region implements Serializable {

	private static final long serialVersionUID = -8275233954694000759L;
	
	private String name;
	private List<Field> fields;
	
	@Override
	public String toString() {
		return name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	public void addField(Field field) {
		this.fields.add(field);
	}
}