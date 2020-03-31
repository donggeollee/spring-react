package com.web.react.user;

public enum Models { 
	
	UNKNOWN(-1, null), 
	USER(1, User.class), 
	;
	
	private int objectType;
	
	private Class objectClass;
	
	private Models(int objectType, Class clazz) {
		this.objectType = objectType;
		this.objectClass = clazz;
	}
	
	public Class getObjectClass() {
		return objectClass;
	}

	public int getObjectType()
	{
		return objectType;
	} 
	
	public static Models valueOf(int objectType){
		Models selected = Models.UNKNOWN ;
		for( Models m : Models.values() )
		{
			if( m.getObjectType() == objectType ){
				selected = m;
				break;
			}
		}
		return selected;
	}
}


