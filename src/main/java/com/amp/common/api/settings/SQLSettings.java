package com.amp.common.api.settings;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.amp.common.api.impl.ToolkitConstants;
import com.amp.common.api.impl.ToolkitReflection;
import com.amp.common.api.impl.ToolkitXML;


public class SQLSettings
{
	private ToolkitReflection iReflection = null;
	
	private String sqlFile;
	
	private boolean isLoadSettingsFromFS = false;
	
    private String className = StringUtils.EMPTY, 
    		       classNameL = StringUtils.EMPTY;
    
   
    public boolean isLoadSettingsFromFS() {
		return isLoadSettingsFromFS;
	}

	public void setLoadSettingsFromFS(boolean isLoadSettingsFromFS) {
		this.isLoadSettingsFromFS = isLoadSettingsFromFS;
	}

	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getClassNameL() {
		return classNameL;
	}
	
	public void setClassNameL(String classNameL) {
		this.classNameL = classNameL;
	}
	
	public ToolkitReflection getIReflection() {
		return iReflection;
	}
	
	public void setIReflection(ToolkitReflection reflection) {
		iReflection = reflection;
	}
	
	public String getSqlFile() {
		return sqlFile;
	}
	
	public void setSqlFile(String sqlFile) {
		this.sqlFile = sqlFile;
	}
	
	public SQLSettings()
    {
	     try
	     {
	    	 this.iReflection = new ToolkitReflection();	
	         
	         this.initSettings();	 	
	     }
	     catch( Exception e )
	     {
	    	 
	     }
    }
	
	public boolean initSettings()
    {
        try
        {
            this.sqlFile = ToolkitConstants.DEFAULT_STR;
            
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
        finally {}
    }
  
	
	public boolean getReflectionData()
	{
		 boolean cRes = true;
		
		 try
		 {
			 className  = this.iReflection.getClassName();
			 if ( className == null ) { cRes = false; }
			
			 classNameL = getClass().getSimpleName();
			 
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 return false;
		 }
	} 
	
	public boolean setSettings(ToolkitXML iXML)
	{
		 boolean cRes = true;
		 Node  cNode  = null;
		 Field cField = null;
		 
		 try
		 {
			 //String methodName = this.iReflection.getMethodName();
			 
			 if( iXML == null )
			 {
				 cRes = false;
				 return cRes;
			 }
			 
			 if ( !this.getReflectionData())
			 {
				 cRes = false;
				 return cRes;
			 }
			
			 Node settNode = iXML.getXMLNode(this.classNameL);
			 if ( settNode == null)
			 {
			  cRes = false;
			  return cRes;
			 }
			 
			 NodeList cNodes = settNode.getChildNodes();
			 Field[] cFields = getClass().getDeclaredFields();
			 
			 for( int index = 0; index < cNodes.getLength(); ++index)
			 {
				 for( int jondex = 0; jondex < cFields.length; ++jondex )
				 {
					 cNode  = (Node)cNodes.item(index);
					 cField = (Field)cFields[jondex];
					 
					 if ( (cField.getName()).equals(cNode.getNodeName()) )
					 {
						  String textContent = cNode.getTextContent(); 
						  Type type = (Type) cField.getGenericType();
						  	
						  if ( type.equals(String.class ))
						  {
						   cField.set(this, textContent);
						  }
						  else if ( type.equals(boolean.class ))
						  {
						   boolean cBoolSet = Boolean.parseBoolean(textContent);
						   cField.setBoolean(this, cBoolSet);	
						  }
						  else if ( type.equals(int.class ))
						  {
						   int cIntSet = Integer.parseInt(textContent);
						   cField.setInt(this, cIntSet);	
						  }
					 }
				 }
			 }
			 
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 return false;
		 }
	}
	
	
	
}
/*===============End SQLSettings===========================*/