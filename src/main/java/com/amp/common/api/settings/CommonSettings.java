package com.amp.common.api.settings;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.amp.common.api.impl.ToolkitReflection;
import com.amp.common.api.impl.ToolkitXML;

/*===============Start CommonSettings============================*/
public class CommonSettings
{
    private String runFolder  = "";
    private String configFolder  = "";
    
    private boolean isLoadSettingsFromFS  = false;

    private ToolkitReflection iReflection = null;
    private String className  = "";
    private String classNameL = "";
    
    /**
	 * @return the configFolder
	 */
	public String getConfigFolder() {
		return configFolder;
	}

	/**
	 * @param configFolder the configFolder to set
	 */
	public void setConfigFolder(String configFolder) {
		this.configFolder = configFolder;
	}

	/**
	 * @return the isLoadSettingsFromFS
	 */
	public boolean isLoadSettingsFromFS() {
		return isLoadSettingsFromFS;
	}

	/**
	 * @param isLoadSettingsFromFS the isLoadSettingsFromFS to set
	 */
	public void setLoadSettingsFromFS(boolean isLoadSettingsFromFS) {
		this.isLoadSettingsFromFS = isLoadSettingsFromFS;
	}

	/*-----------------------------------------------------*/
    public CommonSettings()
    {
         try
         {
	          this.iReflection = new ToolkitReflection();	
	          
	          this.initSettings();
         }
         catch( Exception e ){}
    }
    
    /*-----------------------------------------------------*/
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
	
    /*-----------------------------------------------------*/
	
	public String getRunFolder()
	{
		return runFolder;
	}
	public void setRunFolder(String runFolder)
	{
		this.runFolder = runFolder;
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

	

	/*-----------------------------------------------------*/
	public void initSettings()
	{
	 try
	 {
		 this.runFolder = new File(".").getAbsolutePath();
		 
	 }
	 catch( Exception e ){}
	}
	/*-----------------------------------------------------*/
	
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