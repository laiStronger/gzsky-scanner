/*
*
*
*/

jy.ns("jy.widget");

jy.widget.config = {
	base : {
		body : document.body,
		offset : null
	},
	modal : {
		id : 'jy-widget-modal',
		fadeSpeed : 300
	},
	drag : {
		limitX : false,
		limitY : false,
		limitR : 1500,
		limitB : 900,
		onStart : null,
		onMove : null,
		onStop : null
	}
};

jy.base.extend(jy.widget,{
	init : function(){
		if(!jy.widget.config.base.body){
			jy.widget.config.base.body = document.body;
		}
		jy.widget.config.base.offset = {
			y : Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight),
			x : Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth)
		};
	},
	modal : {
		me : jy.widget.config.modal,
		html : $("<div style='width:100%;height:100%;position:fixed; opacity: 0.25; z-index: 999; top: 0px; left: 0px; background-color: rgb(0, 0, 0); display: none;'></div>"),
		iefix : $("<iframe style='position:absolute;top:0;left:0;z-index:998;filter:alpha(opacity=0);display:none;' id='jy-widget-iefix-select' src='javascript:false;'></iframe>"),
		init : function(){
			if($("#"+this.me.id).length)
				return;
			jy.widget.init();
			this.html.attr("id",this.me.id);
			if(jy.base.isIE){
				var ui_offset = jy.widget.config.base.offset;
				this.html.css({"filter":"alpha(opacity=25)","position":"absolute",
					"height" :  ui_offset.y,
					"width" : ui_offset.x});
				this.iefix.css({
					"height" :  ui_offset.y,
					"width" : ui_offset.x});
				this.iefix.appendTo(jy.widget.config.base.body);
			}
			this.html.appendTo(jy.widget.config.base.body);
		},
		show : function(options){
			jy.base.extend(this.me,options);
			this.init();
			if(jy.base.isIE){
				this.iefix.show();
			}
			if(this.me.fadeSpeed > 0){
				this.html.fadeIn(this.me.fadeSpeed);
			}else{
				this.html.show();
			}
		},
		close : function(){
			if(jy.base.isIE){
				this.iefix.hide();
			}
			if(this.me.fadeSpeed > 0){
				this.html.fadeOut(this.me.fadeSpeed);
			}else{
				this.html.hide();
			}
		},
		shortcut : function(callbackScope,callbackFun){
			var _that = this;
			var _close = _that.close;
			$(document).keydown(function(event){
				var e = window.event || event;  
				var k = e.keyCode || e.which;
				if(k == 27){
					_close.apply(_that,[]);
					if(callbackScope && jy.base.isFun.apply(callbackFun,[])){
						callbackFun.apply(callbackScope,[]);
					}
				}
			});
		}
	},
	drag : function(options){
		var $this = this;
		var me = jy.widget.config.drag;
		jy.base.extend(me,options);
		this.style.position = "absolute";
		this.style.cursor = "move";
		jy.widget.init();
		$this._start = function(e){
			if(jy.base.isFun.apply(me.onStart,[])){
				me.onStart.call($this,{"x":$this.e.clientX,"y":e.clientY});
			};
			$this.ox = e.clientX;
			$this.oy = e.clientY;
			$this._x = $this.offsetLeft - e.clientX;
			$this._y = $this.offsetTop - e.clientY;
			jy.base.bind.call(document,"mousemove",$this._move);
			jy.base.bind.call(document,"mouseup",$this._stop);
		};
		$this._move = function(e){
			window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
			var _left = "",_top = "";
			if(!me.limitX){
				_left = e.clientX + $this._x;
				var _limitR = me.limitR;
				if(jy.base.funs.isNumber(_limitR)){
					_left = Math.min(_left, _limitR);
				}else{
					_left = Math.min(_left, jy.widget.config.base.offset.x - $this.offsetLeft);
				}
				$this.style.left = _left + "px";
			};
			if(!me.limitY){
				_top = e.clientY + $this._y;
				var _limitB = me.limitB;
				if(jy.base.funs.isNumber(_limitB)){
					_top = Math.min(_top, _limitB);
				}else{
					_top = Math.min(_top, jy.widget.config.base.offset.y - $this.offsetTop);
				}
				$this.style.top = _top + "px";
			};
			if(jy.base.isFun.apply(me.onMove,[])){
				me.onMove.call($this,{"x":_left,"y":_top,"ox":$this.ox,"oy":$this.oy});
			};
		};
		$this._stop = function(e){
			if(jy.base.isFun.apply(me.onStop,[])){
				me.onStop.call($this,null);
			};
			jy.base.unbind.call(document,"mousemove",$this._move);
		};
		jy.base.bind.call($this,"mousedown",$this._start);
	}
});