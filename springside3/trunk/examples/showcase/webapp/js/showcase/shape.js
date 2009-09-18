dojo.provide("showcase.shape");

//父对象,拥有颜色属性与颜色获取函数
dojo.declare("Shape", null, {
	_color : "",

	constructor : function(color) {
		this._color = color;
	},
	
	getColor : function() {
		return this._color;
	}
});

//子对象, 拥有半径属性与计算范围函数, 同时继承父对象属性与函数.
dojo.declare("Circle", Shape, {
	_radius : 0,
	
	constructor : function(color, radius) {
		this._radius = radius;
	},
	
	getArea : function() {
		return Math.PI *this._radius*this._radius;
	},
	
	generateContent : function(){
		return "Shape is a circle with "+this.getColor() +" color," + this.getArea()+" area.";
	}
});
