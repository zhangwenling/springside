package org.springside.modules.security.jcaptcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.jhlabs.image.WaterFilter;
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

	@Override
	protected void buildInitialFactories() {
		//build filters
		WaterFilter water = new WaterFilter(); //2.0 使用PinchFilter
		water.setAmplitude(3d);
		water.setAntialias(true);
		water.setPhase(20d);
		water.setWavelength(70d);
		ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[] { water });
		ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[] {});
		ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[] {});
		
		//word generator
		WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));
		//wordtoimage components
		TextPaster randomPaster = new DecoratedRandomTextPaster(7, 7, new RandomListColorGenerator(new Color[] {
				new Color(23, 170, 27), new Color(220, 34, 11), new Color(23, 67, 172) }),
				new TextDecorator[] { new BaffleTextDecorator(new Integer(1), Color.white) });

		BackgroundGenerator back = new UniColorBackgroundGenerator(200, 70, Color.white);

		FontGenerator shearedFont = new RandomFontGenerator(50, 50, new Font[] { new Font("nyala", Font.BOLD, 50),
				new Font("Bell MT", Font.PLAIN, 50), new Font("Credit valley", Font.BOLD, 50) });
		
		//word2image 1
		WordToImage word2image;
		word2image = new DeformedComposedWordToImage(shearedFont, back, randomPaster, backDef, textDef, postDef);

		addFactory(new com.octo.captcha.image.gimpy.GimpyFactory(dictionnaryWords, word2image));

		addFactory(new com.octo.captcha.image.gimpy.GimpyFactory(dictionnaryWords, word2image));
	}

}
