package contextLibrary;

import java.util.ArrayList;

/**
 * @author lcastane[at]icesi.edu.co Date: (03/2011)
 * @version 1.0
 * */

@SuppressWarnings("serial")
public class ContextEntity implements IContextModel {

	/**
	 * Constrains See contextTypeTree to validate the codes
	 */
	public final static int TYPE_METHOD = 321;
	public final static int TYPE_INFO = 322;
	public final static int TYPE_SOFTWARE_SERVICE = 323;
	public final static int TYPE_HARDWARE_SERVICE = 311;

	/**
	 * Attributes
	 */
	private String name;
	private int type;
	private ArrayList<ContextProperty> properties;

	/**
	 * Constructor for the class
	 * 
	 * @param name
	 *            for the context entity
	 * @param type
	 */
	public ContextEntity(String name, int type) {
		this.name = name;
		this.type = type;
		properties = new ArrayList<ContextProperty>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean addProperty(ContextProperty pProperty) {
		return properties.add(pProperty);
	}

	public ContextProperty getProperty(int pos) {

		return properties.get(pos);

	}

	public int getPropertiesSize() {
		return properties.size();
	}

	public ArrayList<ContextProperty> getProperties() {
		return properties;
	}

	@Override
	public boolean equals(Object obj) {
		ContextEntity entity = (ContextEntity) obj;
		if (!entity.getName().equals(name) || entity.getType() != type
				|| entity.getPropertiesSize() != this.getPropertiesSize())
			return false;
		else {
			for (int i = 0; i < properties.size(); i++) {
				boolean notFound = false;
				for (int j = 0; j < entity.getProperties().size() && !notFound; j++) {
					if (properties.get(i).equals(entity.getProperties().get(j)))
						notFound = true;
				}
				if (!notFound)
					return false;
			}
		}
		return true;
	}

}
