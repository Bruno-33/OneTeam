package com.example.administrator.oneteam.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.support.v7.widget.AppCompatImageView;
/**
 * Created by D105-01 on 2017/12/23.
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView{

    private Paint paint;

    public CircleImageView(Context context) {
        super(context, null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    /**
     * 绘制图片
     */
    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        if (drawable != null){
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap b = getCircleBitmap(bitmap, 14);
            final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
            final Rect rectDest = new Rect(0,0,getWidth(),getHeight());
            paint.reset();
            canvas.drawBitmap(b, rectSrc, rectDest, paint);
        }
        else{
            super.onDraw(canvas);
        }

    }

    /**
     * 获取圆形图片
     */
    private Bitmap getCircleBitmap(Bitmap bitmap, int pixels){
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0,0);
        paint.setColor(color);
        int width = bitmap.getWidth();

        canvas.drawCircle(width/2, width/2, width/2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;

    }
}
