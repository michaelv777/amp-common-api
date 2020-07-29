package com.amp.common.api.test.api;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.amp.common.api.impl.ToolkitKeyword;
import com.amp.common.api.textm.CardKeyword;

public class ToolkitKeywordTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	@Test
	public void getKeywordsList() 
	{
		@SuppressWarnings("unused")
		String cMethodName = "";
        
        try
        {
        	
        	StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
         
	        String cText = "AmazonBasics Apple Certified Lightning to USB Cable - 6 Feet (1.8 Meters) - White";
	        	
	        ToolkitKeyword cToolkitKeyword = new ToolkitKeyword();
	        
	        List<CardKeyword> cKeywords = cToolkitKeyword.getKeywordsList(cText);
	        
	        for( CardKeyword cCardKeyword : cKeywords )
	        {
	        	System.out.println(cCardKeyword.getStem() + ":" + 
	        					   cCardKeyword.getFrequency()  + 
	        					   cCardKeyword.getTerms());
	        }
        }
        catch (Exception e)
        {
           fail(e.getMessage());
        }
	}
}
