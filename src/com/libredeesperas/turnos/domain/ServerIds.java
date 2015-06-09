package com.uxor.turnos.domain;

import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class ServerIds extends Vector<String> implements KvmSerializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1814249259826447147L;

	@Override
    public Object getProperty(int arg0) {
            return this.get(arg0);
    }

    @Override
    public int getPropertyCount() {
            return 1;
    }

    @Override
    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
            arg2.name = "string";
            arg2.type = PropertyInfo.STRING_CLASS;
    }

    @Override
    public void setProperty(int arg0, Object arg1) {
            this.add(arg1.toString());
    }

}
