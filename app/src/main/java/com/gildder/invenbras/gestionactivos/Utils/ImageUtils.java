package com.gildder.invenbras.gestionactivos.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;

public final class ImageUtils {

	/**
	 * Retorna un arreglo de byte de un bitmap.
	 * @param bitmap <code>Bitmap</code>, Es una imagen.
	 * @return Arreglo de Byte.
	 */
	public static byte[] getByteArray(Bitmap bitmap) {
		byte[] array = null;
		try {
			//ByteArrayOutputStream: Es hijo de OutputStream
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.PNG, 0, bos);

			array = bos.toByteArray();
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return array;
	}
	
	/**
	 * 
	 * @param bitmap
	 * @param wregion
	 * @param hregion
	 * @return Un Bitmap
	 */
	public static Bitmap  scaleImage(Bitmap bitmap, int wregion, int hregion) {
		int colors[] = new int[bitmap.getWidth()*bitmap.getHeight()];
		bitmap.getPixels(colors, 0, bitmap.getWidth() ,0 , 0,bitmap.getWidth(), bitmap.getHeight());
		Bitmap bitmap2 = Bitmap.createBitmap(1024, 1024, Config.ARGB_8888);
		bitmap2.setPixels(colors, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight()/2);
	
		return bitmap2;
	}

	// toByteArray and toObject are taken from: http://tinyurl.com/69h8l7x
	public static byte[] toByteArray(Object obj) throws IOException {
		byte[] bytes = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
		} finally {
			if (oos != null) {
				oos.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
		return bytes;
	}

	public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
		Object obj = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			obj = ois.readObject();
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (ois != null) {
				ois.close();
			}
		}
		return obj;
	}

	public Bitmap byteToImageView(byte[] bytes, ImageView images){
		Bitmap bmp=BitmapFactory.decodeByteArray(bytes,0,bytes.length);

		return bmp;
	}

}
