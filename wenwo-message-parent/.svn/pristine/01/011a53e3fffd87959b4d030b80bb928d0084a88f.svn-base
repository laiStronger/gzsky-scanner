/**
 * 
 */
package com.wenwo.message.model;

import java.io.Serializable;
import java.util.List;

import com.wenwo.message.model.base.MessageConfigBase;

/**
 * @author shuangtai
 * 
 */
public class PicTemplate extends MessageConfigBase implements Serializable {

	private static final long serialVersionUID = -942162999206650473L;

	private String id;

	@Deprecated
	private String backGroundPicId;//无效

	private List<TextInfo> textInfos;// 文本块信息

	private List<ImageInfo> imageInfos;// 图片块信息

	private LineInfo lineInfo;// 分割线信息

	private CutInfo cutInfo;// 图片剪切信息
	
	private boolean isOnlyBackGroupPic;//是否只有背景图就可以，如果是，则textInfo和imageInfo等其他信息可以全为空

	public CutInfo getCutInfo() {
		return cutInfo;
	}

	public void setCutInfo(CutInfo cutInfo) {
		this.cutInfo = cutInfo;
	}

	public String getBackGroundPicId() {
		return backGroundPicId;
	}

	public void setBackGroundPicId(String backGroundPicId) {
		this.backGroundPicId = backGroundPicId;
	}

	public List<TextInfo> getTextInfos() {
		return textInfos;
	}

	public void setTextInfos(List<TextInfo> textInfos) {
		this.textInfos = textInfos;
	}

	public List<ImageInfo> getImageInfos() {
		return imageInfos;
	}

	public void setImageInfos(List<ImageInfo> imageInfos) {
		this.imageInfos = imageInfos;
	}

	public LineInfo getLineInfo() {
		return lineInfo;
	}

	public void setLineInfo(LineInfo lineInfo) {
		this.lineInfo = lineInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean getIsOnlyBackGroupPic() {
		return isOnlyBackGroupPic;
	}

	public void setIsOnlyBackGroupPic(boolean isOnlyBackGroupPic) {
		this.isOnlyBackGroupPic = isOnlyBackGroupPic;
	}

	/**
	 * 图片模版文本块信息
	 * 
	 * @author shuangtai
	 * 
	 */
	public static class TextInfo implements Serializable {
		private static final long serialVersionUID = 5086553476073125469L;
		private int x;// 横坐标
		private int y;// 纵坐标
		private String font;
		private int size;
		private String color;
		private String text;// 内容，配置的变量
		
		//TODO:暂且在数据库中配置，4.0之后加入消息后台配置
		//该文本块，是否需要换行
		private boolean needLineBreak;
		
		//该文本块最多显示的行数，超过的丢弃,为0时，全部展示
		private int showLineNum;
		
		//每行的宽度
		private int lineWidth;
		
		//行行之间的间隔，上一行顶部，到下一行顶部的间隔
		private int lineHeight;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public String getFont() {
			return font;
		}

		public void setFont(String font) {
			this.font = font;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public boolean isNeedLineBreak() {
			return needLineBreak;
		}

		public void setNeedLineBreak(boolean needLineBreak) {
			this.needLineBreak = needLineBreak;
		}

		public int getLineWidth() {
			return lineWidth;
		}

		public void setLineWidth(int lineWidth) {
			this.lineWidth = lineWidth;
		}

		public int getLineHeight() {
			return lineHeight;
		}

		public void setLineHeight(int lineHeight) {
			this.lineHeight = lineHeight;
		}

		public int getShowLineNum() {
			return showLineNum;
		}

		public void setShowLineNum(int showLineNum) {
			this.showLineNum = showLineNum;
		}
	}

	/**
	 * 图片模版图片块信息
	 * 
	 * @author shuangtai
	 * 
	 */
	public static class ImageInfo implements Serializable, Cloneable {
		private static final long serialVersionUID = -3757644837902438057L;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getDefaultUrl() {
			return defaultUrl;
		}

		public void setDefaultUrl(String defaultUrl) {
			this.defaultUrl = defaultUrl;
		}

		private int x;// 横坐标
		private int y;// 纵坐标
		private int height;
		private int width;
		private String url;// 变量指定的图片地址
		private String defaultUrl;// 默认图片地址
	}

	/**
	 * 图片模版分割线信息
	 * 
	 * @author shuangtai
	 * 
	 */
	public static class LineInfo implements Serializable, Cloneable {
		private static final long serialVersionUID = 2714538560114449326L;
		private int x;// 横坐标
		private int y;// 纵坐标
		private int height;// 线高
		private int width;// 线宽
		private String color;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
	}

	/**
	 * 图片剪切信息
	 * 
	 * @author Administrator
	 * 
	 */
	public static class CutInfo implements Serializable {

		private static final long serialVersionUID = -1377483935278809697L;

		private int height;

		private int width;

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

	}

}
