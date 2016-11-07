package com.younchen.utils;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

public class BitmapUtil {

	/**
	 * 将view 转成bitmap形式
	 * 
	 * @param v
	 * @return
	 */
	public static Bitmap createViewBitmap(View v) {
		// v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
		// MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		// v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
		// v.buildDrawingCache();
		// Bitmap bitmap = v.getDrawingCache();
		// // return bitmap;
		Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
				Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		v.draw(canvas);
		// Log.e("BX", "bitmap"+bitmap);
		return bitmap;
	}

	/**
	 * 设置透明度
	 * 
	 * @param sourceImg
	 * @param number
	 * @return
	 */
	public static Bitmap getTransparentBitmap(Bitmap sourceImg, int number) {
		int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];

		sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg

		.getWidth(), sourceImg.getHeight());// 获得图片的ARGB值

		number = number * 255 / 100;

		for (int i = 0; i < argb.length; i++) {

			argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);

		}

		sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg

		.getHeight(), Config.ARGB_8888);

		return sourceImg;
	}

	/**
	 * 将bitmap 转化成字节流
	 * 
	 * @param bm
	 * @return
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 生成倒影图片
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createReflectedImages(Bitmap bitmap) {
		final int space = 10;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectedBitmap = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);
		Bitmap resultBitmap = Bitmap.createBitmap(width, (height + height / 2),
				Config.ARGB_8888);
		Canvas canvas = new Canvas(resultBitmap);
		canvas.drawBitmap(bitmap, 0, 0, null);

		canvas.drawBitmap(reflectedBitmap, 0, height + space, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				resultBitmap.getHeight() + space, 0x4000ff00, 0x0000ff00,
				TileMode.CLAMP);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, height, width, resultBitmap.getHeight() + space,
				paint);

		return resultBitmap;
	}

	/**
	 * 将目标图片裁剪生成一个圆形图片，此种方式抗锯齿效果好 目标图片建议为正方形
	 * 
	 * @param src
	 * @return
	 */
	public static Bitmap getRoundBitmap(Bitmap src) {
		Bitmap bm = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
				Config.ARGB_8888);
		Canvas c = new Canvas(bm);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setColor(0xFFFFCC44);
		c.drawOval(new RectF(0, 0, src.getWidth(), src.getHeight()), p);
		PorterDuffXfermode mode = new PorterDuffXfermode(Mode.SRC_IN);
		p.setXfermode(mode);
		c.drawBitmap(src, 0, 0, p);
		return bm;
	}

	/**
	 * 高效模糊算法
	 * 
	 * @param sentBitmap
	 * @param radius
	 * @return
	 */
	public static Bitmap fastblur(Bitmap sentBitmap, int radius) {

		Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

		if (radius < 1) {
			return (null);
		}

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		int[] pix = new int[w * h];
		bitmap.getPixels(pix, 0, w, 0, 0, w, h);

		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;

		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];

		int divsum = (div + 1) >> 1;
		divsum *= divsum;
		int dv[] = new int[256 * divsum];
		for (i = 0; i < 256 * divsum; i++) {
			dv[i] = (i / divsum);
		}

		yw = yi = 0;

		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius + 1;
		int routsum, goutsum, boutsum;
		int rinsum, ginsum, binsum;

		for (y = 0; y < h; y++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + Math.min(wm, Math.max(i, 0))];
				sir = stack[i + radius];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rbs = r1 - Math.abs(i);
				rsum += sir[0] * rbs;
				gsum += sir[1] * rbs;
				bsum += sir[2] * rbs;
				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}
			}
			stackpointer = radius;

			for (x = 0; x < w; x++) {

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
				}
				p = pix[yw + vmin[x]];

				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[(stackpointer) % div];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi++;
			}
			yw += w;
		}
		for (x = 0; x < w; x++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;

				sir = stack[i + radius];

				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];

				rbs = r1 - Math.abs(i);

				rsum += r[yi] * rbs;
				gsum += g[yi] * rbs;
				bsum += b[yi] * rbs;

				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}

				if (i < hm) {
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for (y = 0; y < h; y++) {
				// Preserve alpha channel: ( 0xff000000 & pix[yi] )
				pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
						| (dv[gsum] << 8) | dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (x == 0) {
					vmin[y] = Math.min(y + r1, hm) * w;
				}
				p = x + vmin[y];

				sir[0] = r[p];
				sir[1] = g[p];
				sir[2] = b[p];

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[stackpointer];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi += w;
			}
		}
		bitmap.setPixels(pix, 0, w, 0, 0, w, h);

		return (bitmap);
	}

	/**
	 * 　为指定图片增加阴影 wudi
	 * 
	 * @param map
	 *            　图片
	 * @param radius
	 *            　阴影的半径
	 * @return
	 */
	public static Bitmap drawShadow(Bitmap map, int radius) {
		if (map == null)
			return null;

		BlurMaskFilter blurFilter = new BlurMaskFilter(radius,
				BlurMaskFilter.Blur.OUTER);
		Paint shadowPaint = new Paint();
		shadowPaint.setMaskFilter(blurFilter);

		int[] offsetXY = new int[2];
		Bitmap shadowImage = map.extractAlpha(shadowPaint, offsetXY);
		shadowImage = shadowImage.copy(Config.ARGB_8888, true);
		Canvas c = new Canvas(shadowImage);
		c.drawBitmap(map, -offsetXY[0], -offsetXY[1], null);
		return shadowImage;
	}

	/**
	 * bitmap转换成二进制 add by renxiaomin
	 * 
	 * @date 2014年9月4日
	 * @param bm
	 * @return
	 */
	public static byte[] BitmapToBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 二进制转换成bitmap add by renxiaomin
	 * 
	 * @date 2014年9月4日
	 * @param b
	 * @return
	 */
	public static Bitmap BytesToBimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * 根据宽度从本地图片路径获取该图片的缩略图 add by renxiaomin
	 * 
	 * @param localImagePath
	 *            本地图片的路径
	 * @param width
	 *            缩略图的宽
	 * @param addedScaling
	 *            额外可以加的缩放比例
	 * @return bitmap 指定宽高的缩略图
	 */
	public static Bitmap getBitmapByWidth(String localImagePath, int width,
			int height) {
		if (TextUtils.isEmpty(localImagePath)) {
			return null;
		}

		Bitmap temBitmap = null;

		try {
			Options outOptions = new Options();

			// 设置该属性为true，不加载图片到内存，只返回图片的宽高到options中。
			outOptions.inJustDecodeBounds = true;
			// 加载获取图片的宽高
			Bitmap bit = BitmapFactory.decodeFile(localImagePath, outOptions);

			// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
			int be = 1;// be=1表示不缩放
			if (outOptions.outWidth > outOptions.outHeight
					&& outOptions.outWidth > width) {// 如果宽度大的话根据宽度固定大小缩放
				be = (int) (outOptions.outWidth / width);
			} else if (outOptions.outWidth < outOptions.outHeight
					&& outOptions.outHeight > height) {// 如果高度高的话根据宽度固定大小缩放
				be = (int) (outOptions.outHeight / height);
			}
			if (be <= 0)
				be = 1;
			outOptions.inSampleSize = be;// 设置缩放比例
			// 重新设置该属性为false，加载图片返回
			outOptions.inJustDecodeBounds = false;
			bit.recycle();
			temBitmap = BitmapFactory.decodeFile(localImagePath, outOptions);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return temBitmap;
	}

//	/**
//	 * author kang 缩放并保存到另外目录
//	 * 
//	 * @param localImagePath
//	 * @param width
//	 * @param Context
//	 * @return
//	 */
//	public static String getBitmapZoomedLocalPath(String localImagePath,
//			int width, int height, Context context) {
//		String filePath = null;
//		try {
//
//			BitmapFactory.Options outOptions = new BitmapFactory.Options();
//
//			// 设置该属性为true，不加载图片到内存，只返回图片的宽高到options中。
//			outOptions.inJustDecodeBounds = true;
//			// 加载获取图片的宽高
//			Bitmap bit = BitmapFactory.decodeFile(localImagePath, outOptions);
//
//			// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
//			int be = 1;// be=1表示不缩放
//			if (outOptions.outWidth > outOptions.outHeight
//					&& outOptions.outWidth > width) {// 如果宽度大的话根据宽度固定大小缩放
//				be = (int) (outOptions.outWidth / width);
//			} else if (outOptions.outWidth < outOptions.outHeight
//					&& outOptions.outHeight > height) {// 如果高度高的话根据宽度固定大小缩放
//				be = (int) (outOptions.outHeight / height);
//			}
//			if (be <= 0)
//				be = 1;
//			outOptions.inSampleSize = be;// 设置缩放比例
//			// 重新设置该属性为false，加载图片返回
//			outOptions.inJustDecodeBounds = false;
//			bit.recycle();
//			Bitmap mBitmap = BitmapFactory.decodeFile(localImagePath,
//					outOptions);
//			// ************
//			filePath = FileUtil.makeDir(context);
//			String[] names = localImagePath.split("/");
//			filePath = filePath + "/" + names[names.length - 1];
//			FileOutputStream fileOutPutStream = new FileOutputStream(filePath);
//			if (mBitmap != null)
//				mBitmap.compress(CompressFormat.JPEG, 100, fileOutPutStream);
//			fileOutPutStream.flush();
//			fileOutPutStream.close();
//			mBitmap.recycle();
//		} catch (Exception e) {
//			e.printStackTrace();
//			filePath = null;
//		}
//
//		return filePath;
//	}

	public static Bitmap getBitmapFromLocalPath(String localImagePath,
			int width, int height) {
		Options options1 = new Options();
		options1.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(localImagePath, options1);
		options1.inSampleSize = calculateInSampleSize(options1, 110, 160); // 110,160：转换后的宽和高，具体值会有些出入
		options1.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(localImagePath, options1); // filePath:文件路径
	}

	private static int calculateInSampleSize(Options options,
			int reqWidth, int reqHeight) {

		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
		}

		return inSampleSize;
	}

	public static Bitmap convertDrawable2BitmapSimple(Drawable drawable) {
		BitmapDrawable bd = (BitmapDrawable) drawable;
		return bd.getBitmap();
	}

	public static boolean AcordingMomeryCountResizeCount() {

		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		int freeMemory = (int) (Runtime.getRuntime().freeMemory() / 1024);
		Log.d("1111", "Max memory is " + maxMemory + "KB" + "Free memory is "
				+ freeMemory + "KB");
		if (freeMemory < 1024) {
			return true;
		} else {
			return false;
		}
	}

	public static final int MEMORY_WARN = 1;
	public static final int MEMORY_NORMAL = 2;
	public static final int MEMORY_GOOD = 3;

	public static int GetMomeryCountResizeCount() {

		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		int freeMemory = (int) (Runtime.getRuntime().freeMemory() / 1024);
		Log.d("1111", "Max memory is " + maxMemory + "KB" + "Free memory is "
				+ freeMemory + "KB");
		if (freeMemory < 1024) {
			return MEMORY_WARN;
		} else if (freeMemory < 2048 && freeMemory > 1024) {
			return MEMORY_NORMAL;
		} else {
			return MEMORY_GOOD;
		}

	}

	/**
	 * 保存到相册
	 * 
	 * @param context
	 * @param path
	 */
	public static void saveImageToAlbum(Context context, Bitmap img)
			throws Exception {
		MediaStore.Images.Media.insertImage(context.getContentResolver(), img,
				"img", "");
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
				.parse("file://" + Environment.getExternalStorageDirectory())));

	}

	// 提取图像Alpha位图 bx
	public static Bitmap getAlphaBitmap(Bitmap mBitmap, int mColor) {
		// BitmapDrawable mBitmapDrawable = (BitmapDrawable)
		// mContext.getResources().getDrawable(R.drawable.enemy_infantry_ninja);
		// Bitmap mBitmap = mBitmapDrawable.getBitmap();

		// BitmapDrawable的getIntrinsicWidth（）方法，Bitmap的getWidth（）方法
		// 注意这两个方法的区别
		// Bitmap mAlphaBitmap =
		// Bitmap.createBitmap(mBitmapDrawable.getIntrinsicWidth(),
		// mBitmapDrawable.getIntrinsicHeight(), Config.ARGB_8888);
		if (mBitmap == null) {
			return null;
		}
		Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(),
				mBitmap.getHeight(), Config.ARGB_8888);

		Canvas mCanvas = new Canvas(mAlphaBitmap);
		Paint mPaint = new Paint();

		mPaint.setColor(mColor);
		// 从原位图中提取只包含alpha的位图
		Bitmap alphaBitmap = mBitmap.extractAlpha();
		// 在画布上（mAlphaBitmap）绘制alpha位图
		mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);

		return mAlphaBitmap;
	}

}
