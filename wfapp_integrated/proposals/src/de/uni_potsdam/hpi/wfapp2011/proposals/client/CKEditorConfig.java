package de.uni_potsdam.hpi.wfapp2011.proposals.client;

import com.axeiya.gwtckeditor.client.CKConfig;
import com.axeiya.gwtckeditor.client.CKConfig.TOOLBAR_OPTIONS;
import com.axeiya.gwtckeditor.client.Toolbar;
import com.axeiya.gwtckeditor.client.ToolbarLine;

public class CKEditorConfig {

	public CKEditorConfig() {
	}
	
	/**
	 * Initializes ConfigObject with general layout data
	 * and toolbar configuration.
	 * @param height
	 * @return CKConfig
	 */
	public CKConfig getCKEditorCustomization(int height){
		
        CKConfig ckf = new CKConfig();         
        Toolbar toolbar = new Toolbar();
        ToolbarLine line1 = new ToolbarLine();
        ToolbarLine line2 = new ToolbarLine();
        
        ckf.setResizeMinWidth(430);
        ckf.setResizeMinHeight(150);
        ckf.setWidth("430px");
        ckf.setHeight(height+"px");

        TOOLBAR_OPTIONS[] t1 = 
        	   {TOOLBAR_OPTIONS.Font,
        		TOOLBAR_OPTIONS.FontSize,
        		TOOLBAR_OPTIONS._,
        		TOOLBAR_OPTIONS.Bold,
        		TOOLBAR_OPTIONS.Italic,
        		TOOLBAR_OPTIONS.Underline,
        		TOOLBAR_OPTIONS._,
        		TOOLBAR_OPTIONS.Subscript,
        		TOOLBAR_OPTIONS.Superscript,
        		TOOLBAR_OPTIONS._,
          		TOOLBAR_OPTIONS.TextColor,
          		TOOLBAR_OPTIONS.HorizontalRule,
          		TOOLBAR_OPTIONS.SpecialChar,
        		};
        
        TOOLBAR_OPTIONS[] t2 = 
        	   {TOOLBAR_OPTIONS.JustifyLeft,
        		TOOLBAR_OPTIONS.JustifyCenter,
        		TOOLBAR_OPTIONS.JustifyRight,
				TOOLBAR_OPTIONS.JustifyBlock,
		  		TOOLBAR_OPTIONS._,
		        TOOLBAR_OPTIONS.NumberedList,
		  		TOOLBAR_OPTIONS.BulletedList,
		  		TOOLBAR_OPTIONS._,
		  		TOOLBAR_OPTIONS.Outdent,
		  		TOOLBAR_OPTIONS.Indent,
		  		TOOLBAR_OPTIONS._,
		  		TOOLBAR_OPTIONS.Link,
		  		TOOLBAR_OPTIONS.Unlink,
		  		TOOLBAR_OPTIONS.Maximize,
		  		TOOLBAR_OPTIONS.RemoveFormat,
		  		TOOLBAR_OPTIONS.Source};
  
        line1.addAll(t1); 
        line2.addAll(t2);

        toolbar.add(line1);
        toolbar.addSeparator();
        toolbar.add(line2);
        
        ckf.setToolbar(toolbar);
        return ckf;
	}
}