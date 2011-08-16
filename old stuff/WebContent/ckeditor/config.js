CKEDITOR.editorConfig = function( config )
{
	CKEDITOR.config.toolbar = [
	
	['Font','FontSize', 'Format'],	
	['Bold','Italic','Underline', 'StrikeThrough'],
	['Subscript', 'Superscript'], 	
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
	
	'/',	
	['NumberedList','BulletedList','-','Outdent','Indent'],
	['TextColor','BGColor'],	
	['Image','Table', '-',  'HorizontalRule', 'SpecialChar', '-', 'Link', 'Unlink'],
	['Templates', 'Maximize', 'SpellCheck', 'RemoveFormat'],
	['Source'] 
	
	];
};
