/**
 * pic template
 */
(function($) {
	"use strict";
	$.fn.pic_template = function (options){
		var elements = [];
        options = options || {};
        var TEXT_BLOCK_LINE = '';
		var PIC_BLOCK_LINE = '';
		
        var showPicTemplate = function(){
        	 for (var i=0, len=elements.length; i<len; i++) {
        		 var $this = elements[i];
        		 TEXT_BLOCK_LINE = $this.find("#text_block_table #text_block_line");
        		 PIC_BLOCK_LINE = $this.find("#pic_block_table #pic_block_line");
        		 initPicEvent($this);
        	 }
        };
        
        var initPicEvent = function(element){
        	element.on("click", "#newTextBlock", function(e){
    			var new_line = initNewLine(TEXT_BLOCK_LINE, element);
    			new_line.insertBefore(TEXT_BLOCK_LINE);
    		});
    		
        	element.on("click", "#newPicBlock", function(e){
    			var new_line = initNewLine(PIC_BLOCK_LINE, element);
    			new_line.insertBefore(PIC_BLOCK_LINE);
    		});
        	
        	element.on("click", "div.accordion a.variable", function(e){
        		var var_name = $(this).attr("name");
        		var editing_text = element.find("input.variable-focus");
        		var new_content = editing_text.val() + "{" + var_name + "}";
        		editing_text.val(new_content);
        	});
        };
		
		
		/**
		 * 清除焦点
		 */
		var clearFocusInput = function(element){
			element.find("input.variable-focus").removeClass("variable-focus");
		};
		
		var initNewLine = function (template_line, element){
			var new_line = template_line.clone();
			new_line.attr("id", "");
			new_line.find("td:eq(0) input").addClass("variable-focus");
			new_line.addClass("valid_tr");
			
			/**
			 * 确定按钮
			 */
			new_line.find("button.save").on("click", function(e){
				var tr = $(this).parent().parent().parent();
				var isValid = true;
				$.each(tr.find("input:visible"), function(){
					if(!$(this).val()){
						alert("无效输入");
						$(this).focus();
						isValid = false;
						return false;
					}
				});
				
				if(isValid){
					tr.find("input:visible").attr("disabled", "disabled");
					tr.find("td:first input").removeClass("variable-focus");
					$(this).parent().find("button:hidden").show();
					$(this).hide();
			}});
			
			/**
			 * 删除按钮
			 */
			new_line.find("button.delete-btn").on("click", function(e){
				$(this).parent().parent().parent().remove();
			});
			
			/**
			 * 修改按钮
			 */
			new_line.find("button.change-btn").on("click", function(e){
				clearFocusInput(element);
				var tr = $(this).parent().parent().parent();
				tr.find("input:visible").removeAttr("disabled");
				tr.find("td:first input").addClass("focus");
				$(this).parent().find("button:hidden").show();
				$(this).hide();
				$(this).parent().find("button.delete-btn").hide();
			});
			new_line.show();
			return new_line;
		};
		
		this.each(function(){
			var $this = $(this);
			elements.push($this);
		});
		
		showPicTemplate();
		return this;
	};
})(jQuery);