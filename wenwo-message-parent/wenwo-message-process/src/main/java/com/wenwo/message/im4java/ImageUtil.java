package com.wenwo.message.im4java;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wenwo.message.constants.TemplateConstants;
import com.wenwo.message.model.PicTemplate;
import com.wenwo.message.model.PicTemplate.CutInfo;
import com.wenwo.message.model.PicTemplate.ImageInfo;
import com.wenwo.message.model.PicTemplate.LineInfo;
import com.wenwo.message.model.PicTemplate.TextInfo;
import com.wenwo.message.utils.LineHelper;
import com.wenwo.message.utils.VelocityUtils;
import com.wenwo.message.utils.ContextUtils;

public final class ImageUtil {
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	private final static String FONTPATH = new File(ImageUtil.class.getClassLoader().getResource("simsun.ttc")
			.getPath()).getAbsolutePath();

	public static String combineImage(PicTemplate picTemplate, String targetImgPath, VelocityContext context,
			String backGoundPic, VelocityEngine velocityEngine, JMagickImageBuilder jMagickImageBuilder) {
		try {
			// create the operation, add images and operators/options NorthWest
			IMOperation op = new IMOperation();
			logger.debug("字体文件路径{}", FONTPATH);
			op.font(FONTPATH).gravity("southeast");
			op.addImage(backGoundPic);

			List<TextInfo> textInfos = picTemplate.getTextInfos();

			List<ImageInfo> imageInfos = picTemplate.getImageInfos();
			LineInfo lineInfo = picTemplate.getLineInfo();

			// 文本信息
			if (textInfos != null && textInfos.size() > 0) {
				for (TextInfo textInfo : textInfos) {
					int fontSize = textInfo.getSize();
					String color = textInfo.getColor();
					String text = VelocityUtils.generatorTemplate(textInfo.getText(), context, velocityEngine);
					if(StringUtils.isEmpty(text)){
						continue;
					}
					int x = textInfo.getX();
					int y = textInfo.getY();
					
					//去掉回车换行
					text = removeCRLF(text);
					
					if(textInfo.isNeedLineBreak()){
						//文本换行
						List<String> lines = LineHelper.newLines(textInfo.getLineWidth(), fontSize, text, textInfo.getShowLineNum());
						int lineHeight = textInfo.getLineHeight();
						int relativeYAixis = 0;
						for(String line : lines){
							int yAixis = y + relativeYAixis;
							op.pointsize(fontSize).fill(color).draw("text " + x + "," + yAixis + " '" + line + "'");
							relativeYAixis += lineHeight;
						}
					}else{
						op.pointsize(fontSize).fill(color).draw("text " + x + "," + y + " '" + text + "'");
					}
				}
			}
			// 图片信息
			if (imageInfos != null && imageInfos.size() > 0) {
				for (ImageInfo imageInfo : imageInfos) {
					int x = imageInfo.getX();
					int y = imageInfo.getY();
					int width = imageInfo.getWidth();
					int height = imageInfo.getHeight();
					String url = VelocityUtils.generatorTemplate(imageInfo.getUrl(), context, velocityEngine);
					
					//如果是网络图片，则使用java先取到本地，然后再声称图片，若已经获取则直接使用
					String loadedRemoteUrl = LoadRemoteImgUtil.loadRemoteImg(url);
					url = StringUtils.isEmpty(loadedRemoteUrl) ? url : loadedRemoteUrl;
					if(StringUtils.isEmpty(url)){
						url = jMagickImageBuilder.getRequestPic(ContextUtils.getProjectType(context), imageInfo.getDefaultUrl());
					}
					
					logger.info("pic Url:{}", url);
					if(StringUtils.isEmpty(url)){
						continue;
					}
					op.draw("image Over " + x + "," + y + " " + width + "," + height + " " + url);
				}
			}
			// 线信息
			if (lineInfo != null) {
				String color = lineInfo.getColor();
				int x = lineInfo.getX();
				int y = lineInfo.getY();
				
				int endX = x + lineInfo.getWidth();
//				int width = lineInfo.getWidth();
//				int height = lineInfo.getHeight();
				op.fill(color).draw("line " + x + "," + y + " " + endX + "," + y);
			}
			op.addImage(targetImgPath);
//			op.encoding("utf-8");
			// execute the operation
			ConvertCmd cmd = new ConvertCmd(true);
			
			//打印log方便定位生成图片问题
			String targetUid = ContextUtils.getTargetUserId(context);
			logger.info("targetUid:{}, targetAtName:{} ,生成图片命令:{}", targetUid, context.get(TemplateConstants.CONTEXT_TARGET_USER_AT_NAME), op.toString());
			cmd.run(op);
			return fileExists(targetImgPath);
		} catch (Exception e) {
			logger.error("生成图片失败", e);
		}
		return null;
	}

	private static final Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
	private static String removeCRLF(String text) {
		if(StringUtils.isEmpty(text)){
			return null;
		}
		Matcher matcher = CRLF.matcher(text);
		if(matcher.find()){
			text = matcher.replaceAll(" ");
		}
		return text;
	}

	public static String fileExists(String path) {
		if (new File(path).exists()) {
			return path;
		}
		return null;
	}

	/**
	 * 剪切图片
	 * 
	 * @param srcPath
	 * @param newPath
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @throws Exception
	 */
	public static String cutImage(String srcPath, String newPath, CutInfo cutInfo) {
		try {
			IMOperation op = new IMOperation();
			// int x = 0;
			// int y = 0;
			int width = cutInfo.getWidth();
			int height = cutInfo.getHeight();
			op.addImage(srcPath);
			/** width：裁剪的宽度 * height：裁剪的高度 * x：裁剪的横坐标 * y：裁剪纵坐标 */
			op.crop(width, height, 0, 0);
			op.addImage(newPath);
			ConvertCmd convert = new ConvertCmd(true);
			convert.run(op);
			return fileExists(newPath);
		} catch (Exception e) {
			logger.error("剪切图片失败", e);
		}
		return null;
	}

}
