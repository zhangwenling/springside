package org.springside.modules.security.jcaptcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.BaffleTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;

/**
 * 仿照JCaptcha2.0编写GMail验证码样式的图片引擎.
 * 
 * @author calvin
 */
public class GMailEngine extends ListImageCaptchaEngine {
	
	private int minWordLength = 4;
	private int maxWordLength = 4;
	private int imageWidth = 200;
	private int imageHeight = 70;
	private int fontSize = 50;

	@Override
	protected void buildInitialFactories() {
		//build filters
		ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[] {});
		ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[] {});
		ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[] {});
		
		//word generator
		WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));
		
		//word2image components
		TextPaster randomPaster = new DecoratedRandomTextPaster(minWordLength, maxWordLength,
				new RandomListColorGenerator(new Color[] {
				new Color(23, 170, 27), new Color(220, 34, 11), new Color(23, 67, 172) }),
				new TextDecorator[] { new BaffleTextDecorator(1, Color.white) });

	
		FontGenerator shearedFont = new RandomFontGenerator(50, 50, new Font[] { new Font("nyala", Font.BOLD, 50),
				new Font("Bell MT", Font.PLAIN, 50), new Font("Credit valley", Font.BOLD, 50) });
		
		BackgroundGenerator back = new UniColorBackgroundGenerator(200, 70, Color.white);

		WordToImage word2image = new DeformedComposedWordToImage(shearedFont, back, randomPaster, backDef, textDef, postDef);

		addFactory(new com.octo.captcha.image.gimpy.GimpyFactory(dictionnaryWords, word2image));
	}

}
